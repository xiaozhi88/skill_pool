class CommonResponse(object):
    def __init__(self, code, msg, data):
        self.code = code
        self.msg = msg
        self.data = data

class SiteData(object):
    def __init__(self, stationId, value):
        self.stationId = stationId
        self.value = value

# 执行成功的状态码
successCode = "0"
# 执行成功的msg
successMsg = ""
# 执行失败的状态码
errorCode = "1"
# 缺测值
missingValues = -999.0
