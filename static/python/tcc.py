#!/usr/bin/env python 
# -*- coding:utf-8 -*-
import time

import cartopy.crs as ccrs
import matplotlib.colors as colors
import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import shapefile
from matplotlib.patches import PathPatch
from matplotlib.path import Path
from scipy.interpolate import Rbf
from tornado.web import RequestHandler
import json
import common as com


class TccImageVo(object):
    def __init__(self, tcc_data_file_path, shp_path, image_path, min_lon, max_lon, min_lat, max_lat):
        self.tccDataFilePath = tcc_data_file_path
        self.shpPath = shp_path
        self.imagePath = image_path
        self.minLon = min_lon
        self.maxLon = max_lon
        self.minLat = min_lat
        self.maxLat = max_lat


def dictToTccImageVo(d):
    return TccImageVo(d["tccDataFilePath"], d["shpPath"], d["imagePath"], d["minLon"], d["maxLon"], d["minLat"], d["maxLat"])


def create_svg(tcc_image_vo):
    start = int(round(time.time() * 1000))
    # 读取数据
    file_name = tcc_image_vo.tccDataFilePath
    data = pd.read_csv(file_name, delim_whitespace=True)
    lon = data['lon']
    lat = data['lat']
    rhu_data = data['data']

    # 经纬度处理
    min_lon = float(tcc_image_vo.minLon)
    max_lon = float(tcc_image_vo.maxLon)
    olon = np.linspace(min_lon, max_lon, 800)
    min_lat = float(tcc_image_vo.minLat)
    max_lat = float(tcc_image_vo.maxLat)
    olat = np.linspace(min_lat, max_lat, 600)

    # 转成地理经纬度
    olon, olat = np.meshgrid(olon, olat)

    # 插值处理
    func = Rbf(lon, lat, rhu_data, function='linear')
    rhu_data_new = func(olon, olat)

    # 自定义色卡处理
    levels = [-1, -0.875, -0.75, -0.625, -0.5, -0.375, -0.25, -0.125, 0, 0.125, 0.25, 0.375, 0.5, 0.625, 0.75, 0.875, 1]
    my_cmap = colors.ListedColormap(['#18387A', '#215CAA', '#468FCB', '#75BCE6', '#A5DEF7', '#CC88F9', '#EC93FC',
                                     '#FFFFFF', '#FFFFFF', '#FDF3C6', '#FDDB7D', '#FEB12E', '#F88228', '#E94D24',
                                     '#CF1822', '#A31117'])
    my_cmap.set_under('#142862')
    my_cmap.set_over('#930D12')
    norm3 = colors.BoundaryNorm(levels, my_cmap.N)

    # 投影方式
    projection = ccrs.Mercator()

    # 画图部分
    fig = plt.figure(dpi=300)
    ax = fig.add_subplot(111, projection=projection)

    # 设定要显示的边界区域
    ax.set_extent([min_lon, max_lon, min_lat, max_lat], crs=projection)

    # 等值面处理
    cs = ax.contourf(olon, olat, rhu_data_new, levels=levels, cmap=my_cmap, norm=norm3, extend='both',
                     transform=projection, alpha=1)

    # 读取shp文件，获取要裁剪的区域clip
    shp_name = tcc_image_vo.shpPath
    sf = shapefile.Reader(shp_name, encoding="gbk")
    for shp_record in sf.shapeRecords():
        # if shp_record.record['SGB'] == '210000':
        vertices = []
        codes = []
        pts = shp_record.shape.points
        prt = list(shp_record.shape.parts) + [len(pts)]
        for i in range(len(prt) - 1):
            for j in range(prt[i], prt[i + 1]):
                vertices.append((pts[j][0], pts[j][1]))
            codes += [Path.MOVETO]
            codes += [Path.LINETO] * (prt[i + 1] - prt[i] - 2)
            codes += [Path.CLOSEPOLY]
        clip = Path(vertices, codes)
        clip = PathPatch(clip, transform=ax.transData)

    # 裁剪显示区域
    for contour in cs.collections:
        contour.set_clip_path(clip)

    # cartopy 设置背景透明及去除边框
    ax.outline_patch.set_visible(False)
    ax.background_patch.set_visible(False)

    # 保存图片，tight让保存的图片更加紧促
    save_path = tcc_image_vo.imagePath
    plt.savefig(save_path, bbox_inches='tight', transparent=True, pad_inches=0)

    end = int(round(time.time() * 1000))
    print(end - start)
    return end - start


class tcc_svg(RequestHandler):
    def post(self):
        global commonResponse
        try:
            jsonByte = self.request.body
            jsonStr = jsonByte.decode("utf-8")
            tccImageVo = json.loads(jsonStr, object_hook=dictToTccImageVo)
            result = create_svg(tccImageVo)
            commonResponse = com.CommonResponse(com.successCode, com.successMsg, dict([("obj", result)]))
        except BaseException as e:
            print("执行TCC画图失败：{}".format(e))
            commonResponse = com.CommonResponse(com.errorCode, "{}".format(e), dict([("obj", com.missingValues)]))
        finally:
            # 返回数据
            self.write(commonResponse.__dict__)
