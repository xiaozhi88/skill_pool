import numpy as np
import pandas as pd
from tornado.web import RequestHandler
import json
import common as com

def load_data(file_name):
    data_train = []
    f = open(file_name)
    for line in f.readlines():
        lines = line.strip().split()
        data_tmp = []
        for x in lines:
            data_tmp.append(float(x))
        data_train.append(data_tmp)
    f.close()
    return data_train

def Similarity(a,b):
    #欧式距离
    EuDis=np.sqrt(sum(pow(a-b,2))) #等同于EuDis=np.linalg.norm(a - b)
    rEu = 1/(1+EuDis) #相似系数做1/(1+x)这样的处理，下同
    #标准欧氏距离
    stdEuDis=np.sqrt(sum(pow((a-b)/np.sqrt( (a - (a-b)/2) ** 2 + (b - (a-b)/2) ** 2 ),2)))
    rstdEu = 1/(1+stdEuDis)
    #曼哈顿距离
    ManDis=sum(abs(a-b))
    rMan= 1/(1+ManDis)
    #切比雪夫距离
    CheDis=max(abs(a-b))
    rChe=1/(1+CheDis)
    #余弦相似度
    rCos=sum(a*b)/( np.sqrt(sum(a**2)) * np.sqrt(sum(b**2)))
    #汉明相似度
    rHanM=1/(1+sum(a!=b))
    #杰卡德相似系数
    rJcd=float(len(set(a) & set(b))  )/ len(set(a) | set(a))
    #相关系数
    avga = np.mean(a)
    avgb = np.mean(b)
    saSq = sum(pow(a-avga,2))
    sbSq = sum(pow(b-avgb,2))
    pSum = sum((a-avga)*(b-avgb))
    den = np.sqrt(saSq*sbSq)
    if den == 0:rPer = 0
    else:rPer = pSum/den
    return rEu + rstdEu + rMan + rChe + rCos + rHanM + rJcd + rPer

class RhythmVo(object):
    def __init__(self, sampleDataPath):
        self.sampleDataPath = sampleDataPath

def dictToRhythmVo(d):
    return RhythmVo(d["sampleDataPath"])

class Rhythm(RequestHandler):
    def post(self):
        try:
            jsonByte = self.request.body
            jsonStr = jsonByte.decode("utf-8")
            rhythmVo = json.loads(jsonStr, object_hook=dictToRhythmVo)
            datas = load_data(rhythmVo.sampleDataPath)
            length = len(datas) - 1
            observeData = datas[length]
            index = 0
            maxSimilarity = Similarity(np.array(observeData), np.array(datas[0]))
            if pd.isnull(maxSimilarity):
                maxSimilarity = 0
            for i in range(1,length):
                s = Similarity(np.array(observeData), np.array(datas[i]))
                if pd.isnull(s):
                    s = 0
                if s > maxSimilarity:
                    maxSimilarity = s
                    index = i
            commonResponse = com.CommonResponse(com.successCode, com.successMsg, dict([("obj", index)]))
        except BaseException as e:
            print("执行韵律算法失败：{}".format(e))
            commonResponse = com.CommonResponse(com.errorCode, "{}".format(e), dict([("obj", com.missingValues)]))
        finally:
            # 返回数据
            self.write(commonResponse.__dict__)

