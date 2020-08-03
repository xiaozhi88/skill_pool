from tornado.web import RequestHandler
import json
import common as com

class node:
    '''树的节点的类
    '''
    def __init__(self, fea=-1, value=None, results=None, right=None, left=None):
        self.fea = fea  # 用于切分数据集的属性的列索引值
        self.value = value  # 设置划分的值
        self.results = results  # 存储叶节点所属的类别
        self.right = right  # 右子树
        self.left = left  # 左子树

def split_tree(data, fea, value):
    '''根据特征fea中的值value将数据集data划分成左右子树
    input:  data(list):数据集
            fea(int):待分割特征的索引
            value(float):待分割的特征的具体值
    output: (set1,set2)(tuple):分割后的左右子树
    '''
    set_1 = []
    set_2 = []
    for x in data:
        if x[fea] >= value:
            set_1.append(x)
        else:
            set_2.append(x)
    return (set_1, set_2)

def load_data(file_name):
    '''导入数据
    input:  file_name(string):训练数据保存的文件名
    output: data_train(list):训练数据
    '''
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

def label_uniq_cnt(data):
    '''统计数据集中不同的类标签label的个数
    input:  data(list):原始数据集
    output: label_uniq_cnt(int):样本中的标签的个数
    '''
    label_uniq_cnt = {}

    for x in data:
        label = x[len(x) - 1]  # 取得每一个样本的类标签label
        if label not in label_uniq_cnt:
            label_uniq_cnt[label] = 0
        label_uniq_cnt[label] = label_uniq_cnt[label] + 1
    return label_uniq_cnt

def cal_gini_index(data):
    '''计算给定数据集的Gini指数
    input:  data(list):树中
    output: gini(float):Gini指数
    '''
    total_sample = len(data)  # 样本的总个数
    if len(data) == 0:
        return 0
    label_counts = label_uniq_cnt(data)  # 统计数据集中不同标签的个数

    # 计算数据集的Gini指数
    gini = 0
    for label in label_counts:
        gini = gini + pow(label_counts[label], 2)

    gini = 1 - float(gini) / pow(total_sample, 2)
    return gini

def build_tree(data):
    '''构建树
    input:  data(list):训练样本
    output: node:树的根结点
    '''
    # 构建决策树，函数返回该决策树的根节点
    if len(data) == 0:
        return node()

    # 1、计算当前的Gini指数
    currentGini = cal_gini_index(data)

    bestGain = 0.0
    bestCriteria = None  # 存储最佳切分属性以及最佳切分点
    bestSets = None  # 存储切分后的两个数据集

    feature_num = len(data[0]) - 1  # 样本中特征的个数
    # 2、找到最好的划分
    for fea in range(0, feature_num):
        # 2.1、取得fea特征处所有可能的取值
        feature_values = {}  # 在fea位置处可能的取值
        for sample in data:  # 对每一个样本
            feature_values[sample[fea]] = 1  # 存储特征fea处所有可能的取值

        # 2.2、针对每一个可能的取值，尝试将数据集划分，并计算Gini指数
        for value in feature_values.keys():  # 遍历该属性的所有切分点
            # 2.2.1、 根据fea特征中的值value将数据集划分成左右子树
            (set_1, set_2) = split_tree(data, fea, value)
            # 2.2.2、计算当前的Gini指数
            nowGini = float(len(set_1) * cal_gini_index(set_1) + \
                             len(set_2) * cal_gini_index(set_2)) / len(data)
            # 2.2.3、计算Gini指数的增加量
            gain = currentGini - nowGini
            # 2.2.4、判断此划分是否比当前的划分更好
            if gain > bestGain and len(set_1) > 0 and len(set_2) > 0:
                bestGain = gain
                bestCriteria = (fea, value)
                bestSets = (set_1, set_2)

    # 3、判断划分是否结束
    if bestGain > 0:
        right = build_tree(bestSets[0])
        left = build_tree(bestSets[1])
        return node(fea=bestCriteria[0], value=bestCriteria[1], \
                    right=right, left=left)
    else:
        return node(results=label_uniq_cnt(data))  # 返回当前的类别标签作为最终的类别标签

def predict(sample, tree):
    '''对每一个样本sample进行预测
    input:  sample(list):需要预测的样本
            tree(类):构建好的分类树
    output: tree.results:所属的类别
    '''
    # 1、只是树根
    if tree.results != None:
        return tree.results
    else:
    # 2、有左右子树
        val_sample = sample[tree.fea]
        branch = None
        if val_sample >= tree.value:
            branch = tree.right
        else:
            branch = tree.left
        return predict(sample, branch)

def cal_correct_rate(data_train, final_predict):
    m = len(final_predict)
    corr = 0.0
    for i in range(m):
        # 样本预测值 与 样本原值(第三列)  同正负时 值+1
        if data_train[i][-1] * final_predict[i] > 0:
            corr += 1
    return corr / m

def save_result(prediction, result_file):
    '''保存最终的预测结果
    input:  prediction(list):预测的结果
            result_file(string):存储最终预测结果的文件名
    '''
    m = len(prediction)
    f_result = open(result_file, "w")
    for i in range(m):
        tmp = []
        tmp.append(str(prediction[i]))
        f_result.writelines("\t".join(tmp) + "\n")
    f_result.close()

class DecisionTreeVo(object):
    def __init__(self, trainDataPath, testDataPath):
        self.trainDataPath = trainDataPath
        self.testDataPath = testDataPath

def dictToDecisionTreeVo(d):
    return DecisionTreeVo(d["trainDataPath"], d["testDataPath"])

class DecisionTree(RequestHandler):
    def post(self):
        try:
            # 1、导入训练数据
            jsonByte = self.request.body
            jsonStr = jsonByte.decode("utf-8")
            decisionTreeVo = json.loads(jsonStr, object_hook=dictToDecisionTreeVo)
            data_train = load_data(decisionTreeVo.trainDataPath)
            # 2、创建CART分类树
            tree = build_tree(data_train)
            # 3、获取样本数据
            test_data = load_data(decisionTreeVo.testDataPath)
            # 4、对样本进行预测
            result = list(predict(test_data[0], tree).keys())[0]
            commonResponse = com.CommonResponse(com.successCode, com.successMsg, dict([("obj", result)]))
        except BaseException as e:
            print("执行决策树算法失败：{}".format(e))
            commonResponse = com.CommonResponse(com.errorCode, "{}".format(e), dict([("obj", com.missingValues)]))
        finally:
            # 返回数据
            self.write(commonResponse.__dict__)