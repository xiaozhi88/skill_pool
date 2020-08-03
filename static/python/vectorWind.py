'''
Demonstration of wind barb plots
'''
import os
import matplotlib.pyplot as plt
from tornado.web import RequestHandler
import json
import common as com

class VectorWindVo(object):
    def __init__(self, imageDir, imageName, uwindValues, vwindValues, lons, lats):
        self.imageDir = imageDir
        self.imageName = imageName
        self.uwindValues = uwindValues
        self.vwindValues = vwindValues
        self.lons = lons
        self.lats = lats

def dictToVectorWindVo(d):
    return VectorWindVo(d["imageDir"], d["imageName"], d["uwindValues"], d["vwindValues"], d["lons"], d["lats"])

class VectorWindDto(object):
    def __init__(self, imagePath):
        self.imagePath = imagePath

class VectorWindHandler(RequestHandler):
    def post(self):
        try:
            jsonByte = self.request.body
            jsonStr = jsonByte.decode("utf-8")
            vectorWindVo = json.loads(jsonStr, object_hook=dictToVectorWindVo)
            x = vectorWindVo.lons
            y = vectorWindVo.lats
            u = vectorWindVo.uwindValues
            v = vectorWindVo.vwindValues
            fig = plt.figure()
            ax = fig.add_subplot(1, 1, 1)
            ax.barbs(x, y, u, v, length=4, pivot='middle')
            plt.xticks([])
            plt.yticks([])
            plt.axis('off')
            imageDir = vectorWindVo.imageDir
            if os.path.exists(imageDir) == False:
                os.makedirs(imageDir)
            imageName = vectorWindVo.imageName
            plt.savefig(imageDir + imageName, format='svg', bbox_inches='tight', transparent=True, dpi=600)
            plt.clf()
            plt.close(fig)
            commonResponse = com.CommonResponse(com.successCode, com.successMsg, dict([("obj", imageDir + imageName)]))
        except BaseException as e:
            print("执行矢量风出图失败：{}".format(e))
            commonResponse = com.CommonResponse(com.errorCode, "{}".format(e), dict([("obj", com.missingValues)]))
        finally:
            # 返回数据
            self.write(commonResponse.__dict__)