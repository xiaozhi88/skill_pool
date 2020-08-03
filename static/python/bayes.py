#!/usr/bin/env python 
# -*- coding:utf-8 -*-

from sklearn.naive_bayes import GaussianNB
import numpy as np
from tornado.web import RequestHandler
import json
import common as com


def get_data(path):
    train = []
    with open(path, "r", encoding="utf8") as file:
        for line in file:
            train.append(line.strip().split(","))
    test = np.asarray(train.pop(), dtype=np.float32)
    train = np.asarray(train, dtype=np.float32)
    return train, test


def get_y_train_data(path):
    y = {}
    with open(path, "r", encoding="utf8") as file:
        for line in file:
            line_list = line.strip().split(",")
            station_id = line_list.pop(0)
            y[station_id] = np.asarray(line_list, dtype=np.float32)
    return y

class NaiveBayesVo(object):
    def __init__(self, bayesDir, trainxFileName, trainyFileName):
        self.bayesDir = bayesDir
        self.trainxFileName = trainxFileName
        self.trainyFileName = trainyFileName

def dictToNaiveBayesVo(d):
    return NaiveBayesVo(d["bayesDir"], d["trainxFileName"], d["trainyFileName"])

class NaiveBayes(RequestHandler):
    def post(self):
        try:
            jsonByte = self.request.body
            jsonStr = jsonByte.decode("utf-8")
            naiveBayesVo = json.loads(jsonStr, object_hook = dictToNaiveBayesVo)
            base_path = naiveBayesVo.bayesDir
            x_file_name = naiveBayesVo.trainxFileName
            y_file_name = naiveBayesVo.trainyFileName
            # 入参训练数据读取
            (x_train, x_test) = get_data(base_path + x_file_name)
            # 出参训练数据读取
            y_train_dict = get_y_train_data(base_path + y_file_name)
            clf = GaussianNB()
            predictDataList = []
            # 创建预测值文件对象
            for (station, y_train) in y_train_dict.items():
                clf.fit(x_train, y_train)
                y_predict = clf.predict(x_test.reshape(1, -1))
                # 写预测值
                siteData = com.SiteData(str(station), str(y_predict[0]))
                predictDataList.append(siteData.__dict__)
            commonResponse = com.CommonResponse(com.successCode, com.successMsg, dict([("list", predictDataList)]))
        except BaseException as e:
            print("执行朴素贝叶斯算法失败：{}".format(e))
            commonResponse = com.CommonResponse(com.errorCode, "{}".format(e), dict([("list", [])]))
        finally:
            # 返回数据
            self.write(commonResponse.__dict__)
