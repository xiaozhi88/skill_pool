# 线性回归
from sklearn.linear_model import LinearRegression
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


def multiple_regression(data_train, sample_data, test_data):
    linreg = LinearRegression()
    linreg.fit(sample_data, data_train)
    x_test = load_data(test_data)
    y_pred = linreg.predict(x_test)
    return y_pred

class MultipleRegressionVo(object):
    def __init__(self, resultDataFilePath, sampleDataFilePath, testDataFilePath):
        self.resultDataFilePath = resultDataFilePath
        self.sampleDataFilePath = sampleDataFilePath
        self.testDataFilePath = testDataFilePath

def dictToMultipleRegressionVo(d):
    return MultipleRegressionVo(d["resultDataFilePath"], d["sampleDataFilePath"], d["testDataFilePath"])

class MultipleRegression(RequestHandler):
    def post(self):
        try:
            jsonByte = self.request.body
            jsonStr = jsonByte.decode("utf-8")
            multipleRegressionVo = json.loads(jsonStr, object_hook=dictToMultipleRegressionVo)
            # 1、导入数据
            data_train = load_data(multipleRegressionVo.resultDataFilePath)
            # 3、获取样本数据
            sample_data = load_data(multipleRegressionVo.sampleDataFilePath)
            test_data = multipleRegressionVo.testDataFilePath
            # 4、对样本进行预测
            result = multiple_regression(data_train, sample_data, test_data)
            commonResponse = com.CommonResponse(com.successCode, com.successMsg, dict([("obj", result[0][0])]))
        except BaseException as e:
            print("执行多元回归算法失败：{}".format(e))
            commonResponse = com.CommonResponse(com.errorCode, "{}".format(e), dict([("obj", com.missingValues)]))
        finally:
            # 返回数据
            self.write(commonResponse.__dict__)
