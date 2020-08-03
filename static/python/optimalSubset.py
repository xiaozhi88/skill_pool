#python 最优子集回归算法

import numpy as np
import pandas as pd
import math
from tornado.web import RequestHandler
import json
import common as com

class OptimalSubsetVo(object):
    def __init__(self, sampleDataPath, featureNum):
        self.sampleDataPath = sampleDataPath
        self.featureNum = featureNum

def dictToOptimalSubsetVo(d):
    return OptimalSubsetVo(d["sampleDataPath"], d["featureNum"])

'''
nums都是11，n1都为n+1，n气候指数为130，高度场为2701，modes数据全国为 979
只用10年的训练数据(有时候会缺失一两年，所有nums也由后台传入)，不然太大了，效率很慢，实测15年的单个站点服务器上13秒，全部站点跑完7分多钟
'''
class OptimalSubset(RequestHandler):
    def post(self):
        try:
            jsonByte = self.request.body
            jsonStr = jsonByte.decode("utf-8")
            optimalSubsetVo = json.loads(jsonStr, object_hook=dictToOptimalSubsetVo)
            txtPath = optimalSubsetVo.sampleDataPath
            feature_num = int(optimalSubsetVo.featureNum)
            col_row_num = (feature_num // 100 + 1) * 100

            ff1 = pd.read_csv(txtPath, sep='\\s+', header=None)
            ff2 = pd.DataFrame(ff1)
            nums = ff1.shape[0]

            xxCol = col_row_num
            xfCol = col_row_num
            xfRow = col_row_num
            n = feature_num


            nsize = n//10 + 1
            n1 = n+1

            # 创建二维数组
            ex = np.zeros(shape=(30, 30), dtype=float)
            cc = np.zeros(shape=(30, 30), dtype=float)
            coe = np.zeros(shape=(30, 30), dtype=float)
            # 创建30*100的数组(指数先改为30*200)(高度场30*2800)
            xx = np.zeros(shape=(30, xxCol), dtype=float)
            # 创建100*100的数组(指数先改为200*200)(高度场2800*2800)
            xf = np.zeros(shape=(xfRow, xfCol), dtype=float)
            csca = []
            ihh = []

            for i in range(0, 30):
                csca.append(0)
                ihh.append(0)
            csca = np.array(csca, dtype=float)
            ihh = np.array(ihh)

            x1 = []
            x2 = []
            x = []
            for j in range(0, n1):
                x1.append(0)
                x2.append(0)
                x.append(0)
            x1 = np.array(x1, dtype=float)
            x2 = np.array(x2, dtype=float)
            x = np.array(x, dtype=float)

            '''
            ff1和ff2获取的二维数组数据有缺失的，比如此例中最后一行的最后一个数据，
            fillna缺测数据填充为参数值
            '''

            f1 = ff2.fillna(32766)
            f2 = ff2.fillna(-999)

            # 气候指数，需要的训练集指数刚好是10的整数位，最后一组需要求最优子集测试回归的数据，刚好就少一列，需要补回来
            # 前面几组，有1个特征向量占一行，不会出现最后一组的情况
            if feature_num % 10 == 0:
                f32766 = pd.DataFrame([[32766, 32766, 32766, 32766, 32766, 32766, 32766, 32766, 32766, 32766]])
                # ignore_index=True True 添加时，需要索引，默认值是false
                f1 = f1.append(f32766, ignore_index=True)
                f0 = pd.DataFrame([[-999, -999, -999, -999, -999, -999, -999, -999, -999, -999]])
                f2 = f2.append(f0, ignore_index=True)

            dat = []
            xy = []
            for num in range(0, nums):
                f12 = []
                f13 = []
                # shape查看数组维数，shape[0]=11，shape[1]=143 或 2702
                for feature_index in range(0, f1.shape[1]):
                    f12.append(f1[feature_index][num])
                    f13.append(f2[feature_index][num])
                for bb in f12:
                    if bb == 32766:
                        for bb1 in range(0, f12.count(32766)):
                            f12.remove(bb)
                f12 = np.array(f12)
                f13 = np.array(f13)
                dat.append(f12)
                # xy.txt读取的数据，不满数组的数据补0操作
                xy.append(f13)

            dat = np.array(dat)
            dat_mean = []
            for i in range(0, len(dat)):
                dat_mean.append(np.nanmean(dat[i][0:n]))

            a1 = []
            for i in range(0, nums):
                for j in range(0, nums):
                    s = 0
                    for k in range(0, n):
                        s += (xy[i][k]*xy[j][k])
                    s -= (n*dat_mean[i]*dat_mean[j])
                    a1.append(s)

            a = []
            for i in range(0, nums):
                a2 = []
                # 魔法数值 6 ,气候指数修改为nums
                for j in range(i*nums, i*nums+nums):
                    a2.append(a1[j])
                a.append(a2)
            a = np.array(a)

            sto = a[nums-1][nums-1]
            # **求次方
            ih = 2**(nums-2)-1
            ks = []
            for i in range(0, ih+1):
                ks.append(0)

            ks[0] = 1
            for k in range(1, nums-2):
                j = 2**k
                ks[j-1] = k+1
                for i in range(0, j-1):
                    m = ks[j-i-2]
                    ks[j+i] = -m
            ks[ih] = 5

            nb = 0
            rss = []
            ma = []
            for i in range(0, nums-1):
                rss.append(10.0**20)
                ma.append(0)
            rss = np.array(rss)
            ma = np.array(ma)

            def csc():
                le = np.array([[0 for col in range(3)] for row in range(3)], dtype=float)
                p = []
                q = []
                for i in range(0, 3):
                    p.append(0)
                    q.append(0)
                p = np.array(p, dtype=float)
                q = np.array(q, dtype=float)
                v = 0.0
                u = 0.0
                for i in range(0, n-1):
                    u += abs(x1[i+1]-x1[i])/(n-1)
                    v += abs(x[i+1]-x[i])/(n-1)
                for i in range(0, 3):
                    for j in range(0, 3):
                        le[i][j] = 0.0
                for i in range(0, n-1):
                    xp = x[i+1]-x[i]
                    xp1 = x1[i+1]-x1[i]
                    if(xp1 >= u and xp >= v):
                        le[0][0] += 1
                    if(xp1>=u and xp<=0):
                        le[2][0] += 1
                    if(xp1>=u and xp>0 and xp<v):
                        le[1][0] += 1
                    if(xp1>0 and xp1<u and xp>=v):
                        le[0][1] += 1
                    if(xp1>0 and xp1<u and xp>0 and xp<v):
                        le[1][1] += 1
                    if(xp1>0 and xp1<u and xp<=0):
                        le[2][1] += 1
                    if(xp1<=0 and xp>=v):
                        le[0][2] += 1
                    if(xp1<=0 and xp>0 and xp<v):
                        le[1][2] += 1
                    if(xp1<=0 and xp<=0):
                        le[2][2] += 1
                le = np.array(le)
                le = le.T
                r1 = 0.0
                r2 = 0.0
                r3 = 0.0
                for i in range(0, 3):
                    p[i] = 0.0
                    q[i] = 0.0
                for i in range(0, 3):
                    for j in range(0, 3):
                        if le[j][i] != 0.0:
                            r1 += (le[j][i]*(math.log(float(le[j][i]))))
                for i in range(0, 3):
                    for j in range(0, 3):
                        p[i] += le[j][i]
                        q[i] += le[i][j]
                for i in range(0,3):
                    if(q[i] != 0.0):
                        r2 += (q[i]*(math.log(q[i])))
                    if(p[i] != 0.0):
                        r3 += (p[i]*(math.log(p[i])))
                s1 = 2*(r1+(n-1)*(math.log(float(n-1)))-(r2+r3))
                xm1 = 0.0
                for i in range(0, n):
                    xm1 += (x[i]/float(n))
                qk = 0.0
                qx = 0.0
                for i in range(0,  n):
                    qk += ((x[i]-x1[i])*(x[i]-x1[i])/n)
                    qx += ((x[i]-xm1)*(x[i]-xm1)/n)
                s2 = (n-(k+1))*(1-qk/qx)
                csc3 = s1+s2
                return csc3

            def reg2(it):
                kk = it-1
                if (abs(a[kk][kk]) > (10**(-12))):
                    for j in range(0, nums):
                        for i in range(0, nums):
                            if(i != kk and j!=kk):
                                a[i][j] -= a[kk][j]*a[i][kk]/a[kk][kk]
                            else:
                                continue
                    for j in range(0, nums):
                        if(j != kk):
                            a[j][kk] = a[j][kk]/a[kk][kk]
                            a[kk][j] = -a[kk][j]/a[kk][kk]
                        else:
                            continue
                    a[kk][kk] = 1/a[kk][kk]
                ma[kk] = 0
                if(a[nums-1][nums-1]<=rss[nb-1]):
                    rss[nb-1] = a[nums-1][nums-1]
                    for j in range(0, nums-1):
                        ex[j][nb-1] = ma[j]
                        coe[j][nb-1] = a[nums-1][j]
                else:
                    return ex, coe, ma, rss, a, nb, ts
                return ex, coe, ma, rss, a, nb, ts

            def reg1(it):
                kk = it-1
                if (abs(a[kk][kk]) > (10**(-12))):
                    for j in range(0, nums):
                        for i in range(0, nums):
                            if(i != kk and j != kk):
                                a[i][j] -= a[kk][j]*a[i][kk]/a[kk][kk]
                            else:
                                continue
                    for j in range(0, nums):
                        if(j != kk):
                            a[j][kk] = a[j][kk]/a[kk][kk]
                            a[kk][j] = -a[kk][j]/a[kk][kk]
                        else:
                            continue
                    a[kk][kk] = 1/a[kk][kk]
                ma[kk] = 1
                if(a[nums-1][nums-1] <= rss[nb-1]):
                    rss[nb-1] = a[nums-1][nums-1]
                    for j in range(0, nums-1):
                        ex[j][nb-1] = ma[j]
                        coe[j][nb-1] = a[nums-1][j]
                else:
                    return ex, coe, ma, rss, a, nb, ts
                return ex, coe, ma, rss, a, nb, ts

            ts = 0
            for m in range(0, ih+1):
                it = abs(ks[m])
                if ks[m] > 0:
                    nb += 1
                    ts += 1
                    ex, coe, ma, rss, a, nb, ts = reg1(it)
                else:
                    nb -= 1
                    ts += 1
                    ex, coe, ma, rss, a, nb, ts = reg2(it)

            for m in range(0, ih):
                it = abs((-1)*ks[ih-1-m])
                if (-1)*ks[ih-1-m] > 0:
                    nb += 1
                    ts += 1
                    ex, coe, ma, rss, a, nb, ts = reg1(it)
                else:
                    nb -= 1
                    ts += 1
                    ex, coe, ma, rss, a, nb, ts = reg2(it)

            for k in range(0, nums-1):
                s = rss[k]
                c = 0.0
                for j in range(0, nums-1):
                    if ex[j][k] != 0:
                        c += (coe[j][k]*dat_mean[j])
                    else:
                        continue
                c = dat_mean[nums-1]-c

                mm = 0
                for j in range(0, nums-1):
                    if ex[j][k] != 0.0:
                        mm += 1
                        cc[mm-1][k] = coe[j][k]
                        for i in range(0, n1):
                            xx[mm-1][i] = xy[j][i]
                for i in range(0, n1):
                    xf[i][k] = c
                    for j in range(0, mm):
                        xf[i][k] = xf[i][k]+cc[j][k]*xx[j][i]
                    x[i] = xy[nums-1][i]
                    x1[i] = xf[i][k]
                for j in range(0, n):
                    x2[j] = xf[j][k]-x[j]
                w = 0.0
                for j in range(0, n):
                    w += x2[j]**2
                v1 = np.sqrt(w/n)
                csca[k] = csc()

            aa = 0.0
            for i in range(0, nums-1):
                if(csca[i] > aa):
                    aa = csca[i]
            for i in range(0, nums-1):
                if csca[i] == aa:
                    ihh[0] = i

            kk1 = ihh[0]
            for j in range(0, nums-1):
                if(j == kk1):
                    for i in range(0, n1):
                        x1[i] = xf[i][j]
                else:
                    continue

            w = 0.0
            for i in range(0, n):
                w += (x[i]-x1[1])**2
            v = np.sqrt(w/n)
            commonResponse = com.CommonResponse(com.successCode, com.successMsg, dict([("obj", '%.2f'%x1[n])]))
        except BaseException as e:
            print("执行最优子集失败：{}".format(e))
            commonResponse = com.CommonResponse(com.errorCode, "{}".format(e), dict([("obj", com.missingValues)]))
        finally:
            # 返回数据
            self.write(commonResponse.__dict__)