# -*- coding: utf-8 -*-
"""
Created on Wed Jan  9 20:40:38 2019

@author: XiangBo
"""

import math
import numpy as np
from tornado.web import RequestHandler
import json
import common as com


def load_data(line_arr):
    data = []
    for value in line_arr:
        data.append(float(value))
    data = np.asarray(data)
    return data


def MMGF(XX, PMGF, N, M, MM, N1):
    SB = np.zeros(shape=(N1))
    for K in range(1, 4):
        for KK in range(2, MM + 1):
            for J in range(1, KK + 1):
                SB[J] = 0.0
                I = 0
                for L in range(J - 1, N, KK):
                    SB[J] = SB[J] + XX[K - 1, L]
                    I = I + 1
                SB[J] = SB[J] / I

            for L in range(1, N1 + 1):
                J = L - int(L / KK) * KK
                if J == 0:
                    J = KK
                KKK = (K - 1) * (MM - 1) + KK - 1
                PMGF[L, KKK] = SB[J]


def RA1(N, X, F, Y):
    '''    
    SUBROUTINE RA1 FOR REGRESSION OF ONE VARIABLE
    N--SAMPLE NUMBER
    X--TIME SERIES
    F--MEAN GENERATING FUNCTION SERIES
    Y--FITTING SERIES OF X
    '''
    C = np.sum(F[:])
    D = np.sum(X[:])
    FC = np.sum(F[:] * F[:])
    XC = np.sum(X[:] * X[:])
    # 这里用  FXC=np.sum(F[:]*X[:])存在F[:]多一个F[0]的情况，暂时抛弃这种用法
    FXC = 0
    for i in range(1, N + 1):
        FXC = FXC + F[i] * X[i - 1]

    DF = FC - C * C / N
    DX = XC - D * D / N
    DFX = FXC - D * C / N
    B = DFX / DF
    B0 = (D - B * C) / N
    R = DFX / np.sqrt(abs(DF * DX))
    for i in range(1, N + 1):
        Y[i - 1] = B0 + B * F[i]


def CSC(N, X, Y, K):
    LE = np.zeros(shape=(3, 3))
    U = 0.0
    V = 0.0
    for i in range(0, N - 1):
        U = U + abs(Y[i + 1] - Y[i]) / (N - 1)
        V = V + abs(X[i + 1] - X[i]) / (N - 1)

    for i in range(1, N):
        XP = X[i] - X[i - 1]
        XP1 = Y[i] - Y[i - 1]
        if (XP1 >= U and XP >= V):
            LE[0, 0] = LE[0, 0] + 1
        if (XP1 >= U and XP <= 0):
            LE[0, 2] = LE[0, 2] + 1
        if (XP1 >= U and XP < V and XP > 0):
            LE[0, 1] = LE[0, 1] + 1
        if (XP1 < U and XP1 > 0 and XP >= V):
            LE[1, 0] = LE[1, 0] + 1
        if (XP1 < U and XP1 > 0 and XP < V and XP > 0):
            LE[1, 1] = LE[1, 1] + 1
        if (XP1 < U and XP1 > 0 and XP <= 0):
            LE[1, 2] = LE[1, 2] + 1
        if (XP1 <= 0 and XP >= V):
            LE[2, 0] = LE[2, 0] + 1
        if (XP1 <= 0 and XP > 0 and XP < V):
            LE[2, 1] = LE[2, 1] + 1
        if (XP1 <= 0 and XP <= 0):
            LE[2, 2] = LE[2, 2] + 1

    R1 = 0.0
    R2 = 0.0
    R3 = 0.0
    P = np.zeros(shape=(3,))
    Q = np.zeros(shape=(3,))

    for i in range(0, 3):
        for j in range(0, 3):
            if (LE[i, j] != 0.0):
                R1 = R1 + LE[i, j] * math.log(LE[i, j])
        P[i] = np.sum(LE[i, :])
        Q[i] = np.sum(LE[:, i])
        if (Q[i] != 0.0):
            R2 = R2 + Q[i] * math.log(Q[i])
        if (P[i] != 0.0):
            R3 = R3 + P[i] * math.log(P[i])
    S1 = 2 * (R1 + (N - 1) * math.log((N - 1)) - (R2 + R3))

    XM1 = 0.0
    for II in range(0, N):
        XM1 = XM1 + X[II] / N
    QK = 0.0
    QX = 0.0
    for i in range(0, N):
        QK = QK + (X[i] - Y[i]) * (X[i] - Y[i]) / N
        QX = QX + (X[i] - XM1) * (X[i] - XM1) / N
    S2 = (N - K) * (1 - QK / QX)
    CSC3 = S1 + S2
    return CSC3


def reg300(a, k, iip):
    if abs(a[k, k]) > pow(10, -12):
        for i in range(1, iip + 1):
            for j in range(1, iip + 1):
                if ((i != k) and (j != k)):
                    a[i, j] = a[i, j] - a[i, k] * a[k, j] / a[k, k]
        for j in range(1, iip + 1):
            if j != k:
                a[k, j] = a[k, j] / a[k, k]
                a[j, k] = -a[j, k] / a[k, k]
    a[k, k] = 1 / a[k, k]
    return a


def reg350(a, k, ts, ma, nb, rss, ex, coe, iip, it, ip):
    k = it
    a = reg300(a, k, iip)
    ts = ts + 1
    ma[it] = 1
    nb = nb + 1
    if a[iip, iip] <= rss[nb]:
        rss[nb] = a[iip, iip]
        for j in range(1, ip + 1):
            ex[nb, j] = ma[j]
            coe[nb, j] = a[j, iip]
    return a, ts, nb, rss, ex, coe


def reg430(a, k, ts, ma, nb, rss, ex, coe, iip, it, ip):
    k = it
    a = reg300(a, k, iip)
    ts = ts + 1
    ma[it] = 0
    nb = nb - 1
    if (a[iip, iip] <= rss[nb]):
        rss[nb] = a[iip, iip]
        for j in range(1, ip + 1):
            ex[nb, j] = ma[j]
            coe[nb, j] = a[j, iip]
    return a, ts, nb, rss, ex, coe


def sreg(XY, FX, Y, N, N1, IN, IIN):
    XYM = np.zeros(shape=(1000))
    KS = np.zeros(shape=2000)
    RSS = np.zeros(shape=2000)
    MA = np.zeros(shape=2000)

    EX = np.zeros(shape=(1000, 1000))
    COE = np.zeros(shape=(1000, 1000))
    CSCA = np.zeros(shape=1000)
    A = np.zeros(shape=(1000, 1000))
    # FX=np.zeros(shape=(1000,1000))

    CC = np.zeros(shape=(1000, 1000))
    XX = np.zeros(shape=(1000, 1000))
    XF = np.zeros(shape=(1000, 1000))
    X1 = np.zeros(shape=(1000))
    X2 = np.zeros(shape=(1000))
    X3 = np.zeros(shape=(1000))
    IHH = np.zeros(shape=(1000))

    for J in range(1, IN + 2):
        S = 0
        for I in range(1, N + 1):
            S = S + XY[I, J]
        XYM[J] = S / N
    for I in range(1, IN + 2):
        for J in range(1, IN + 2):
            S = 0
            for K in range(1, N + 1):
                S = S + XY[K, I] * XY[K, J]
            S = S - N * XYM[I] * XYM[J]
            A[I, J] = S
            A[J, I] = S
    sto = A[IN + 1, IN + 1]
    IH = pow(2, IN - 1) - 1
    KS[1] = 1
    for K in range(2, IN):
        J = pow(2, (K - 1))
        KS[J] = K
        for I in range(1, J):
            M = KS[J - I]
            KS[J + I] = -M
            # print (K,J,I,KS[J+I])
    NB = 0
    for I in range(1, IN + 1):
        RSS[I] = pow(10, 20)

    TS = 0
    for M in range(1, IH + 1):
        IT = int(abs(KS[M]))
        # print (IT)
        if (KS[M] > 0):
            # print(M,KS[M],A[1,1],TS,NB,RSS[1],EX[1,1],COE[1,1])
            A, TS, NB, RSS, EX, COE = reg350(A, K, TS, MA, NB, RSS, EX, COE, IIN, IT, IN)
        else:
            A, TS, NB, RSS, EX, COE = reg430(A, K, TS, MA, NB, RSS, EX, COE, IIN, IT, IN)

    IT = IN
    A, TS, NB, RSS, EX, COE = reg350(A, K, TS, MA, NB, RSS, EX, COE, IIN, IT, IN)

    for M in range(1, IH + 1):
        M1 = (-1) * KS[IH + 1 - M]
        IT = int(abs(M1))
        if (M1 > 0):
            A, TS, NB, RSS, EX, COE = reg350(A, K, TS, MA, NB, RSS, EX, COE, IIN, IT, IN)
        else:
            A, TS, NB, RSS, EX, COE = reg430(A, K, TS, MA, NB, RSS, EX, COE, IIN, IT, IN)

    for K in range(1, IN + 1):
        S = RSS[K]
        C = 0.0
        for j in range(1, IN + 1):
            if (EX[K, j] != 0):
                C = C + COE[K, j] * XYM[j]

        C = XYM[IN + 1] - C

        MM = 0
        for j in range(1, IN + 1):
            if (EX[K, j] != 0):
                MM = int(MM) + 1
                CC[K, MM] = COE[K, j]
                for i in range(1, N1 + 1):
                    XX[i, int(MM)] = FX[i, j]

        for i in range(1, N1 + 1):
            XF[K, i] = C
            for j in range(1, int(MM) + 1):
                XF[K, i] = XF[K, i] + CC[K, j] * XX[i, j]

            X1[i - 1] = XY[i, IIN]
            X3[i - 1] = XF[K, i]

        for j in range(1, N + 1):
            X2[j - 1] = XF[K, j] - X1[j - 1]

        W = 0.0
        for j in range(1, N + 1):
            W = W + X2[j - 1] * X2[j - 1]
        V = np.sqrt(W / N)
        CSCA[K] = CSC(N, X1, X3, K)

    AA = 0.0
    for i in range(1, IN + 1):
        if (CSCA[i] > AA):
            AA = CSCA[i]
    for i in range(1, IN + 1):
        if (CSCA[i] == AA):
            IHH[1] = i

    KK = int(IHH[1])
    for j in range(1, IN + 1):
        if (j == KK):
            for i in range(1, N1 + 1):
                Y[i - 1] = XF[j, i]


def ACTA(X, Y, N, NN, N1, IN):
    x1 = np.zeros(shape=1000)
    x2 = np.zeros(shape=1000)
    x3 = np.zeros(shape=1000)
    xx = np.zeros(shape=(3, 1000))
    PMGF = np.zeros(shape=(1000, 1000))
    FX = np.zeros(shape=(1000, 1000))

    F = np.zeros(shape=1000)
    NUM = np.zeros(shape=1000)
    IP = np.zeros(shape=1000)
    CSCA = np.zeros(shape=1000)

    pmgf1 = np.zeros(shape=(1000, 1000))
    XY = np.zeros(shape=(1000, 500))

    for i in range(0, N - 1):
        x1[i] = X[i + 1] - X[i]
    for i in range(0, N - 2):
        x2[i] = x1[i + 1] - x1[i]
    for i in range(0, N):
        xx[0, i] = X[i]
        xx[1, i] = x1[i]
        xx[2, i] = x2[i]
    MM = int(N / 3)
    M = MM * 3
    MMGF(xx, PMGF, N, 3, MM, N1)

    M = M - 3
    for i in range(MM, 2 * MM - 1):
        for j in range(1, N1 + 1):
            FX[j, i - MM + 1] = PMGF[j, i]

    for i in range(1, MM):
        PMGF[1, i + M] = X[0]
        X0 = X[0]
        for j in range(2, N1 + 1):
            X0 = X0 + FX[j - 1, i]
            PMGF[j, i + M] = X0
    M = M + MM - 1
    for i in range(1, M + 1):
        for j in range(1, N + 1):
            F[j] = PMGF[j, i]
        if i < MM:
            NUM[i] = 0
            IP[i] = i + 1
        elif i >= MM and i < 2 * MM - 1:
            NUM[i] = 1
            IP[i] = i - MM + 2
        elif i >= 2 * MM and i < 3 * MM - 1:
            NUM[i] = 2
            IP[i] = i - 2 * MM + 3
        else:
            NUM[i] = 4
            IP[i] = i - 3 * MM + 4

        RA1(N, X, F, x3)
        CSCA[i] = CSC(N, X, x3, 1)

    KF = 13.39
    L = 0
    for i in range(1, M + 1):
        if CSCA[i] > KF:
            L = L + 1
            # print (L,NUM[I],IP[I],CSCA[I])
            CSCA[L] = CSCA[i]
            for j in range(1, N1 + 1):
                pmgf1[j, L] = PMGF[j, i]

    for i in range(1, L + 1):
        IP[i] = i

    for i in range(1, L):
        j = i + 1
        while j < L + 1:
            if CSCA[i] >= CSCA[j]:
                j = j + 1
            else:
                W = CSCA[i]
                CSCA[i] = CSCA[j]
                CSCA[j] = W
                K = IP[i]
                IP[i] = IP[j]
                IP[j] = K

    if L < IN:
        IN = L
    for i in range(1, IN + 1):
        KL = int(IP[i])
        for j in range(1, N1 + 1):
            FX[j, i] = pmgf1[j, KL]

    for i in range(1, IN + 1):
        for j in range(1, N + 1):
            XY[j, i] = FX[j, i]
    for i in range(1, N + 1):
        XY[i, IN + 1] = X[i - 1]
    IIN = IN + 1

    sreg(XY, FX, Y, N, N1, IN, IIN)

class MgfVo(object):
    def __init__(self, sampleDataPath):
        self.sampleDataPath = sampleDataPath

def dictToMgfVo(d):
    return MgfVo(d["sampleDataPath"])

class Mgf(RequestHandler):
    def post(self):
        jsonByte = self.request.body
        jsonStr = jsonByte.decode("utf-8")
        mgfVo = json.loads(jsonStr, object_hook=dictToMgfVo)
        dataFilePath = mgfVo.sampleDataPath
        file = open(dataFilePath, 'r')
        predictDataList = []
        for line in file.readlines():
            line_arr = line.split(",")
            station_id = line_arr.pop(0)
            X = load_data(line_arr) #数据转float数组
            N, = X.shape  # shape返回数组维度，资料年份长度
            NN = 1  # 需要预测的年数
            N1 = N + NN
            IN = 10  # 循环次数
            Y = np.zeros(shape=N1)
            try:
                ACTA(X, Y, N, NN, N1, IN)
                predict_value = Y[Y.size - 1]
                siteData = com.SiteData(str(station_id), str(predict_value))
                predictDataList.append(siteData.__dict__)
            except TypeError as e:
                print(station_id, "预测失败：", e)
        if len(predictDataList) == 0:
            commonResponse = com.CommonResponse(com.errorCode, "每个站点都预测失败", dict([("list", predictDataList)]))
        else:
            commonResponse = com.CommonResponse(com.successCode, com.successMsg, dict([("list", predictDataList)]))
        self.write(commonResponse.__dict__)
