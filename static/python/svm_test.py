# coding:UTF-8

import numpy as np
import pickle as pickle
# 脚本中有用svm命令参数，这里一定要跟模块区分开
import svm as sv
from tornado.web import RequestHandler
import json
import common as com


def load_test_data(test_file):
    '''导入测试数据
    input:  test_file(string):测试数据
    output: data(mat):测试样本的特征
    '''
    data = []
    f = open(test_file)

    for line in f.readlines():

        lines = line.strip().split(' ')

        # 处理测试样本中的特征
        tmp = []
        for i in range(0, len(lines)):
            tmp.append(float(lines[i]))
        data.append(tmp)
    f.close()
    return np.mat(data)


def load_svm_model(svm_model_file):
    '''导入SVM模型
    input:  svm_model_file(string):SVM模型保存的文件
    output: svm_model:SVM模型
    '''
    with open(svm_model_file, 'rb') as f:
        svm_model = pickle.load(f)
    return svm_model


def get_prediction(test_data, svm):
    '''对样本进行预测
    input:  test_data(mat):测试数据
            svm:SVM模型
    output: prediction(list):预测所属的类别
    '''
    m = np.shape(test_data)[0]
    prediction = []
    for i in range(m):
        # 对每一个样本得到预测值
        predict = sv.svm_predict(svm, test_data[i, :])
        # 得到最终的预测类别
        prediction.append(str(np.sign(predict)[0, 0]))
    return prediction


def save_prediction(result_file, prediction):
    '''保存预测的结果
    input:  result_file(string):结果保存的文件
            prediction(list):预测的结果
    '''
    f = open(result_file, 'w')
    f.write("\n".join(prediction))
    f.close()

class SvmTest(RequestHandler):
    def post(self):
        try:
            jsonByte = self.request.body
            jsonStr = jsonByte.decode("utf-8")
            svmVo = json.loads(jsonStr, object_hook=sv.dictToSvmVo)
            file_name = svmVo.testDataPath
            model_name = svmVo.modelPath
            # 1、导入测试数据
            test_data = load_test_data(file_name)
            # 2、导入SVM模型
            svm_model = load_svm_model(model_name)
            # 3、得到预测值
            prediction = get_prediction(test_data, svm_model)
            commonResponse = com.CommonResponse(com.successCode, com.successMsg, dict([("obj", prediction[0])]))
        except BaseException as e:
            print("执行支持向量机算法失败：{}".format(e))
            commonResponse = com.CommonResponse(com.errorCode, "{}".format(e), dict([("obj", com.missingValues)]))
        finally:
            # 返回数据
            self.write(commonResponse.__dict__)
