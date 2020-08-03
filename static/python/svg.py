#!/usr/bin/env python 
# -*- coding:utf-8 -*-

import os
import cartopy.crs as ccrs
import matplotlib.colors as colors
import matplotlib.pyplot as plt
import numpy as np
import shapefile
from matplotlib.patches import PathPatch
from matplotlib.path import Path
from scipy.interpolate import Rbf
from tornado.web import RequestHandler
import json
import common as com

class SvgDto(object):
    def __init__(self, values, lons, lats, savePath, imageName, shpPath, levels, colorMap, underColor, overColor, areaMinLon, areaMaxLon, lonLength, areaMinLat, areaMaxLat, latLength):
        self.values = values
        self.lons = lons
        self.lats = lats
        self.savePath = savePath
        self.imageName = imageName
        self.shpPath = shpPath
        self.levels = levels
        self.colorMap = colorMap
        self.underColor = underColor
        self.overColor = overColor
        self.areaMinLon = areaMinLon
        self.areaMaxLon = areaMaxLon
        self.lonLength = lonLength
        self.areaMinLat = areaMinLat
        self.areaMaxLat = areaMaxLat
        self.latLength = latLength

def dictToSvgDto(d):
    return SvgDto(d["values"], d["lons"], d["lats"], d["savePath"], d["imageName"], d["shpPath"], d["levels"], d["colorMap"],d["underColor"],d["overColor"],d["areaMinLon"], d["areaMaxLon"], d["lonLength"], d["areaMinLat"], d["areaMaxLat"], d["latLength"])


class CreateSvgImage(RequestHandler):
    def post(self):
        try:
            jsonByte = self.request.body
            jsonStr = jsonByte.decode("utf-8")
            svgDto = json.loads(jsonStr, object_hook=dictToSvgDto)
            # 读取txt文件
            lat = svgDto.lats
            lon = svgDto.lons
            val = svgDto.values
            minLon = svgDto.areaMinLon
            maxLon = svgDto.areaMaxLon
            minLat = svgDto.areaMinLat
            maxLat = svgDto.areaMaxLat
            lonLength = svgDto.lonLength
            latLength = svgDto.latLength

            # 经纬度处理
            olon = np.linspace(minLon, maxLon, lonLength)
            olat = np.linspace(minLat, maxLat, latLength)

            # 转成地理经纬度
            olon, olat = np.meshgrid(olon, olat)

            # 插值处理
            func = Rbf(lon, lat, val, function='linear')
            rhu_data_new = func(olon, olat)

            # 自定义色卡处理
            levels = svgDto.levels
            my_cmap = colors.ListedColormap(svgDto.colorMap)
            my_cmap.set_under(svgDto.underColor)
            my_cmap.set_over(svgDto.overColor)
            norm3 = colors.BoundaryNorm(levels, my_cmap.N)

            # 投影方式
            projection = ccrs.Mercator()

            # 创建图实例 创建设置像素
            fig = plt.figure(dpi=300)
            ax = fig.add_subplot(111, projection=projection)

            # 设定要显示的边界区域
            ax.set_extent([minLon, maxLon, minLat, maxLat], crs=projection)

            # 等值面处理
            cs = ax.contourf(olon, olat, rhu_data_new, levels=levels, cmap=my_cmap, norm=norm3, extend='both',
                             transform=projection, alpha=1)

            # 读取shp文件，获取要裁剪的区域clip(环流图不用裁剪)
            shp_name = svgDto.shpPath
            if shp_name:
                sf = shapefile.Reader(shp_name, encoding="gbk")
                for shp_record in sf.shapeRecords():
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
            #判断目录是否存在，不存在则创建
            save_path = svgDto.savePath
            if os.path.exists(save_path) == False:
                os.makedirs(save_path)
            image_name = svgDto.imageName
            # 保存图片，tight让保存的图片更加紧促
            plt.savefig(save_path + image_name, bbox_inches='tight', transparent=True, pad_inches=0)
            plt.clf()
            plt.close(fig)
            commonResponse = com.CommonResponse(com.successCode, com.successMsg, dict([("obj", save_path + image_name)]))
        except BaseException as e:
            print("执行svg出图失败：{}".format(e))
            commonResponse = com.CommonResponse(com.errorCode, "{}".format(e), dict([("obj", com.missingValues)]))
        finally:
            # 返回数据
            self.write(commonResponse.__dict__)
