# coding:UTF-8

import numpy as np
import svm
from tornado.web import RequestHandler
import json
import common as com


def load_data_libsvm(data_file):
    '''导入训练数据
    input:  data_file(string):训练数据所在文件
    output: data(mat):训练样本的特征
            label(mat):训练样本的标签
    '''
    data = []
    label = []
    f = open(data_file)

    for line in f.readlines():
        lines = line.strip().split(' ')
        length = len(lines)
        # 提取得出label
        label.append(float(lines[length - 1]))

        # 提取出特征，并将其放入到矩阵中
        tmp = []

        for i in range(0, length - 1):
            tmp.append(float(lines[i]))
        data.append(tmp)

    f.close()
    return np.mat(data), np.mat(label).T

class SvmTrain(RequestHandler):
    def post(self):
        try:
            jsonByte = self.request.body
            jsonStr = jsonByte.decode("utf-8")
            svmVo = json.loads(jsonStr, object_hook = svm.dictToSvmVo)
            file_name = svmVo.trainDataPath
            model = svmVo.modelPath
            # 1、导入训练数据
            dataSet, labels = load_data_libsvm(file_name)
            # 2、训练SVM模型
            C = 0.6
            toler = 0.001
            maxIter = 500
            svm_model = svm.SVM_training(dataSet, labels, C, toler, maxIter)
            # 3、计算训练的准确性
            svm.cal_accuracy(svm_model, dataSet, labels)
            # 4、保存最终的SVM模型
            svm.save_svm_model(svm_model, model)
            commonResponse = com.CommonResponse(com.successCode, com.successMsg, dict([("obj", 1)]))
        except BaseException as e:
            print("执行支持向量机-生成模型失败：{}".format(e))
            commonResponse = com.CommonResponse(com.errorCode, "{}".format(e), dict([("obj", 1)]))
        finally:
            # 返回数据
            self.write(commonResponse.__dict__)
