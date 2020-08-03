import numpy as np
from random import random
from tornado.web import RequestHandler
import json
import common as com

FLOAT_MAX = 1e100

def load_data(file_path):
    '''导入数据
    input:  file_path(string):文件的存储位置
    output: data(mat):数据
    '''
    f = open(file_path)
    data = []
    for line in f.readlines():
        row = []  # 记录每一行
        lines = line.strip().split()
        for x in lines:
            value = float(x)
            if value == -999:
                value = 0
            row.append(value)  # 将文本中的特征转换成浮点数
        data.append(row)
    f.close()
    return np.mat(data)

def distance(vecA, vecB):
    '''计算vecA与vecB之间的欧式距离的平方
    input:  vecA(mat)A点坐标
            vecB(mat)B点坐标
    output: dist[0, 0](float)A点与B点距离的平方
    '''
    dist = (vecA - vecB) * (vecA - vecB).T
    return dist[0, 0]

def nearest(point, cluster_centers):
    '''计算point和cluster_centers之间的最小距离
    input:  point(mat):当前的样本点
        cluster_centers(mat):当前已经初始化的聚类中心
    output: min_dist(float):点point和当前的聚类中心之间的最短距离
    '''
    min_dist = FLOAT_MAX
    m = np.shape(cluster_centers)[0]  # 当前已经初始化的聚类中心的个数
    for i in range(m):
        # 计算point与每个聚类中心之间的距离
        d = distance(point, cluster_centers[i,])
        # 选择最短距离
        if min_dist > d:
            min_dist = d
    return min_dist

def rand_cent(points, k):
    '''KMeans++的初始化聚类中心的方法
       input:  points(mat):样本
               k(int):聚类中心的个数
       output: cluster_centers(mat):初始化后的聚类中心
   '''
    m, n = np.shape(points)
    cluster_centers = np.mat(np.zeros((k, n)))
    # 1、随机选择一个样本点为第一个聚类中心
    index = np.random.randint(0, m)
    cluster_centers[0,] = np.copy(points[index, ])
    # 2、初始化一个距离的序列
    d = [0.0 for _ in range(m)]
    for i in range(1, k):
        sum_all = 0
        for j in range(m):
            # 3、对每一个样本找到最近的聚类中心点
            d[j] = nearest(points[j,], cluster_centers[0:i, ])
            # 4、将所有的最短距离相加
            sum_all += d[j]
        # 5、取得sum_all之间的随机值
        sum_all *= random()
        # 6、获得距离最远的样本点作为聚类中心点
        for j, di in enumerate(d):
            sum_all -= di
            if sum_all > 0:
                continue
            cluster_centers[i] = np.copy(points[j, ])
            break
    return cluster_centers

def kmeans(data, k, centroids):
    '''根据KMeans算法求解聚类中心
    input:  data(mat):训练数据
            k(int):类别个数
            centroids(mat):随机初始化的聚类中心
    output: centroids(mat):训练完成的聚类中心
            sub_center(mat):每一个样本所属的类别
    '''
    m, n = np.shape(data)  # m：样本的个数，n：特征的维度
    sub_center = np.mat(np.zeros((m, 2)))  # 初始化每一个样本所属的类别
    for i in range(m):
        sub_center[i, ] = np.mat([k, 0])  # 重新初始化
    change = True  # 判断是否需要重新计算聚类中心
    while change == True:
        change = False  # 重置
        for i in range(m):
            minDist = np.inf  # 设置样本与聚类中心之间的最小的距离，初始值为正无穷
            minIndex = 0  # 所属的类别
            for j in range(k):
                # 计算i和每个聚类中心之间的距离
                dist = distance(data[i, ], centroids[j, ])
                if dist < minDist:
                    minDist = dist
                    minIndex = j
            # 判断是否需要改变
            if sub_center[i, 0] != minIndex:  # 需要改变
                change = True
                sub_center[i, ] = np.mat([minIndex, i])
        # 重新计算聚类中心
        for j in range(k):
            sum_all = np.mat(np.zeros((1, n)))
            r = 0  # 每个类别中的样本的个数
            for i in range(m):
                if sub_center[i, 0] == j:  # 计算第j个类别
                    sum_all += data[i, ]
                    r += 1
            for z in range(n):
                try:
                    centroids[j, z] = sum_all[0, z] / r
                except ZeroDivisionError:
                    print(" r is zero")
    return sub_center

def save_result(file_name, source):
    '''保存source中的结果到file_name文件中
    input:  file_name(string):文件名
            source(mat):需要保存的数据
    output:
    '''
    m, n = np.shape(source)
    f = open(file_name, "w")
    for i in range(m):
        tmp = []
        for j in range(n):
            tmp.append(str(source[i, j]))
        f.write("\t".join(tmp) + "\n")
    f.close()

def get_same_year(source):
    m, n = np.shape(source)
    type_end_year = int(source[m - 1, 0])
    same_type_list = []
    for i in range(m - 1):
        year_type = int(source[i, 0])
        if type_end_year == year_type:
            same_type_list.append(int(source[i, 1]))
    return same_type_list

def get_index_means(data, k):
    # 2、随机初始化k个聚类中心
    cluster_centers = rand_cent(data, k)
    # 3、聚类计算
    subCenter = kmeans(data, k, cluster_centers)
    # 4、获取传入最后一年同类的其余年份数组
    return get_same_year(subCenter)

class KmeansVo(object):
    def __init__(self, dataPath):
        self.dataPath = dataPath

def dictToKmeansVo(d):
    return KmeansVo(d["dataPath"])

class IndexKMeans(RequestHandler):
    def post(self):
        try:
            commonResponse = com.CommonResponse(com.errorCode, "无同聚类数据", dict([("list", [])]))
            jsonByte = self.request.body
            jsonStr = jsonByte.decode("utf-8")
            kmeansVo = json.loads(jsonStr, object_hook = dictToKmeansVo)
            k = 8  # 聚类中心的个数
            data = load_data(kmeansVo.dataPath)
            while k >= 2:
                years = get_index_means(data, k)
                if len(years) != 0:
                    print("聚类成功，k={}".format(k))
                    commonResponse = com.CommonResponse(com.successCode, com.successMsg, dict([("list", years)]))
                    break
                k = k - 1
        except BaseException as e:
            print("执行kmeans失败：{}".format(e))
            commonResponse = com.CommonResponse(com.errorCode, "{}".format(e), dict([("list", [])]))
        finally:
            # 返回数据
            self.write(commonResponse.__dict__)
