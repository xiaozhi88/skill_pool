'''
Created on Jun 1, 2011

@author: Peter Harrington
'''
from numpy import *
from tornado.web import RequestHandler
import json
import common as com


def loadDataSet(file_name, delim='\t'):
    return loadtxt(fname=file_name)

def pca(dataMat, topNfeat=3):
    # 求数据矩阵每一列的均值
    meanVals = mean(dataMat, axis=0)
    # 数据矩阵每一列特征减去该列的特征均值
    meanRemoved = dataMat - meanVals
    # 计算协方差矩阵，除数n-1是为了得到协方差的无偏估计
    # cov(X,0) = cov(X) 除数是n-1(n为样本个数)
    # cov(X,1) 除数是n
    covMat = cov(meanRemoved, rowvar=0)
    # 计算协方差矩阵的特征值及对应的特征向量
    # 均保存在相应的矩阵中
    eigVals, eigVects = linalg.eig(mat(covMat))
    # sort():对特征值矩阵排序(由小到大)
    # argsort():对特征值矩阵进行由小到大排序，返回对应排序后的索引
    eigValInd = argsort(eigVals)
    # 从排序后的矩阵最后一个开始自下而上选取最大的N个特征值，返回其对应的索引
    eigValInd = eigValInd[:-(topNfeat + 1):-1]
    # 将特征值最大的N个特征值对应索引的特征向量提取出来，组成压缩矩阵
    redEigVects = eigVects[:, eigValInd]
    # 将去除均值后的数据矩阵*压缩矩阵，转换到新的空间，使维度降低为N
    lowDDataMat = meanRemoved * redEigVects
    # 利用降维后的矩阵反构出原数据矩阵(用作测试，可跟未压缩的原矩阵比对)
    reconMat = (lowDDataMat * redEigVects.T) + meanVals
    # 返回压缩后的数据矩阵即该矩阵反构出原始数据矩阵
    return lowDDataMat, reconMat


def save_predict(file_name, predict):
    '''保存最终的预测值
    input:  file_name(string):需要保存的文件名
            predict(mat):对测试数据的预测值
    '''
    m = shape(predict)[0]
    result = []
    for i in range(m):
        result.append(str(predict[i]))
    f = open(file_name, "w")
    f.write("\n".join(result))
    f.close()


def least_square(feature, label):
    '''最小二乘法
    input:  feature(mat):特征
            label(mat):标签
    output: w(mat):回归系数
    '''
    # w = dot(dot(inv(dot(x.T, x)), x.T), y)
    w = (feature.T * feature).I * feature.T * label
    return w


def get_prediction(data, w):
    '''得到预测值
    input:  data(mat):测试数据
            w(mat):权重值
    output: 最终的预测
    '''
    return dot(data, transpose(w.T))


def load_data(file_path):
    '''导入测试数据
    input:  file_path(string):训练数据
    output: feature(mat):特征
    '''
    f = open(file_path)
    feature = []
    for line in f.readlines():
        feature_tmp = []
        lines = line.strip().split("\t")
        for i in range(len(lines)):
            feature_tmp.append(float(lines[i]))
        feature.append(feature_tmp)
    f.close()
    return mat(feature)


def save_predict(file_name, predict):
    '''保存最终的预测值
    input:  file_name(string):需要保存的文件名
            predict(mat):对测试数据的预测值
    '''
    m = shape(predict)[0]
    result = []
    for i in range(m):
        result.append(str(predict[i, 0].real))
    f = open(file_name, "w")
    f.write("\n".join(result))
    f.close()


class PcaVo(object):
    def __init__(self, sampleDataPath, labelDataPath, forecastDataPath):
        self.sampleDataPath = sampleDataPath
        self.labelDataPath = labelDataPath
        self.forecastDataPath = forecastDataPath

def dictToPcaVo(d):
    return PcaVo(d["sampleDataPath"], d["labelDataPath"], d["forecastDataPath"])


class Pca(RequestHandler):
    def post(self):
        try:
            jsonByte = self.request.body
            jsonStr = jsonByte.decode("utf-8")
            pcaVo = json.loads(jsonStr, object_hook=dictToPcaVo)
            feature_path = pcaVo.sampleDataPath
            label_path = pcaVo.labelDataPath
            test_path = pcaVo.forecastDataPath
            data = loadDataSet(feature_path)
            lowDDataMat, reconMat = pca(data, 3)
            y = load_data(label_path)
            w_ls = least_square(mat(lowDDataMat), y)
            testData = loadDataSet(test_path)
            testlowDDataMat, testreconMat = pca(testData, 3)
            predict = get_prediction(mat(testlowDDataMat), mat(w_ls))
            commonResponse = com.CommonResponse(com.successCode, com.successMsg, dict([("obj", predict[-1, 0].real)]))
        except BaseException as e:
            print("执行主成分分析失败：{}".format(e))
            commonResponse = com.CommonResponse(com.errorCode, "{}".format(e), dict([("obj", com.missingValues)]))
        finally:
            # 返回数据
            self.write(commonResponse.__dict__)