import tornado.httpserver
import tornado.ioloop
import tornado.options
from tornado.web import RequestHandler
from tornado.options import define, options
import py_eureka_client.eureka_client as eureka_client
from indexKMeans import IndexKMeans
from bayes import NaiveBayes
from decisionTree import DecisionTree
from svm_test import SvmTest
from svm_train import SvmTrain
from mgf import Mgf
from multipleRegression import MultipleRegression
from optimalSubset import OptimalSubset
from rhythm import Rhythm
from pca import Pca
from svg import CreateSvgImage
from tcc import tcc_svg

define("eurekaServer", default="http://10.172.16.59:8761/eureka/", help="eureka服务器地址", type=str)
define("appName", default="python", help="注册应用名称", type=str)
define("port", default=8001, help="程序端口号", type=int)

class Info(RequestHandler):
    def get(self):
        self.write("hello python eureka!")

def eurekaClient():
    tornado.options.parse_command_line()
    eureka_client.init_registry_client(eureka_server=options.eurekaServer,
                                       app_name=options.appName,
                                       instance_port=options.port)
    app = tornado.web.Application(
        handlers=[
            (r"/info", Info),
            (r"/kmeans", IndexKMeans),
            (r"/naiveBayes", NaiveBayes),
            (r"/decisionTree", DecisionTree),
            (r"/createSvmModel", SvmTrain),
            (r"/svm", SvmTest),
            (r"/mgf", Mgf),
            (r"/multipleRegression", MultipleRegression),
            (r"/optimalSubset", OptimalSubset),
            (r"/rhythm", Rhythm),
            (r"/pca", Pca),
            (r"/createSvgImage", CreateSvgImage),
            (r"/tcc", tcc_svg)
        ]
    )
    http_server = tornado.httpserver.HTTPServer(app)
    http_server.listen(options.port)
    tornado.ioloop.IOLoop.instance().start()

if __name__ == "__main__":
    eurekaClient()