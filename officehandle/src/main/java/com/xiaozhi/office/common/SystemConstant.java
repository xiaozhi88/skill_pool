package com.xiaozhi.office.common;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 常用到的常量
 *
 * @author LeoRmAo
 * @date 20190517 15:28:32
 * @since v1.0
 */
public class SystemConstant {

    /**
     * 私有构造
     *
     * @author LeoRmAo
     * @date 20190517 15:28:59
     * @since v1.0
     */
    private SystemConstant() {
    }

    //------------------------------     返回给前端的状态代码     ------------------------------
    /**
     * 通用错误状态
     */
    public static String HTTP_COMMON_ERROR = "1";

    /**
     * 通用成功状态
     */
    public static String HTTP_SUCCESS = "0";

    /**
     * CIMISS_SUCCESS_CODE cimiss调用成功返回值
     */
    public static final String CIMISS_SUCCESS_CODE = "0";

    /**
     * EMPTY_STRING 空串
     */
    public static final String EMPTY_STRING = "";

    /**
     * DOT 点
     */
    public static final String DOT = ".";

    /**
     * DAWN 顿号
     */
    public static final String DAWN = "、";

    /**
     * EQUAL_SIGN 等号
     */
    public static final String EQUAL_SIGN = "=";

    /**
     * COMMA 逗号
     */
    public static final String COMMA = ",";

    /**
     * FILE_SEPARATOR linux目录分隔符
     */
    public static final String FILE_SEPARATOR = "/";

    /**
     * UNDERLINE 下划线
     */
    public static final String UNDERLINE = "_";

    /**
     * HORIZONTAL_LINE 横线
     */
    public static final String HORIZONTAL_LINE = "-";

    /**
     * WILDCARD 通配符
     */
    public static final String WILDCARD = "*";

    /**
     * ADD 加号
     */
    public static final String ADD = "+";

    /**
     * COLON 冒号
     */
    public static final String COLON = ":";

    /**
     * SEMICOLONS 分号
     */
    public static final String SEMICOLONS = ";";

    /**
     * OPEN_PARENTHESIS 左括号
     */
    public static final String OPEN_PARENTHESIS = "(";

    /**
     * CLOSE_PARENTHESIS 右括号
     */
    public static final String CLOSE_PARENTHESIS = ")";

    /**
     * SPACE 空格
     */
    public static final String SPACE = " ";

    /**
     * TXT_SUFFIX txt文件名后缀
     */
    public static final String TXT_SUFFIX = ".txt";

    /**
     * WORD_SUFFIX word后缀
     */
    public static final String WORD_SUFFIX = ".docx";

    /**
     * XLS_SUFFIX xls文件名后缀
     */
    public static final String XLS_SUFFIX = ".xls";

    /**
     * RAR_SUFFIX rar压缩文件名后缀
     */
    public static final String RAR_SUFFIX = ".rar";

    /**
     * NC_SUFFIX nc文件名后缀
     */
    public static final String NC_SUFFIX = ".nc";

    /**
     * GRIB_SUFFIX grib文件名后缀
     */
    public static final String GRIB_SUFFIX = ".grib";

    /**
     * PNG_SUFFIX png文件名后缀
     */
    public static final String PNG_SUFFIX = ".png";

    /**
     * SVG_SUFFIX svg文件后缀名
     */
    public static final String SVG_SUFFIX = ".svg";

    /**
     * INDEX_NUMBER 气候指数个数
     */
    public static final Integer INDEX_NUMBER = 130;

    /**
     * shell调用执行ncl成功状态
     */
    public static final String NCL_SUCCESS = "0";

    /**
     * DERF_LENGTH derf模式资料预报长度
     */
    public static final int DERF_LENGTH = 50;

    /**
     * CFSV_DAILY_LENGTH CFSV逐日长度
     */
    public static final int CFSV_DAILY_LENGTH = 45;

    /**
     * EC_LENGTH EC模式资料预报长度
     */
    public static final int EC_LENGTH = 7;

    /**
     * S2S_JAPAN_LENGTH s2s日本中心，从起报下一天开始预报
     */
    public static final int S2S_JAPAN_LENGTH = 33;

    /**
     * S2S_UKMO_LENGTH s2s英国中心 从起报当天开始预报
     * 气温和降水 60天
     * UV风、位势高度 61天
     */
    public static final int S2S_UKMO_LENGTH = 60;

    /**
     * S2S_CMA_LENGTH s2s中国中心，从起报当天开始预报
     * 气温和降水 60天
     * 海平面气压、UV风、位势高度 61天
     */
    public static final int S2S_CMA_LENGTH = 60;

    /**
     * S2S_NCEP_LENGTH s2s美国中心
     * 气温从下一天开始预报 43天
     * 降水从当天开始预报 44天
     * 海平面气压、UV风、位势高度从当天开始预报 45天
     */
    public static final int S2S_NCEP_LENGTH = 44;

    /**
     * S2S_ECMWF_LENGTH s2s欧洲中心，从起报当天开始预报
     * 气温降水 46天
     * 海平面气、UV风、位势高度 47天
     */
    public static final int S2S_ECMWF_LENGTH = 46;

    /**
     * BCC_LENGTH BCC模式资料预报长度
     */
    public static final int BCC_LENGTH = 12;

    /**
     * CFSV_LENGTH CFSV模式资料预报长度
     */
    public static final int CFSV_MONTHLY_LENGTH = 10;

    /**
     * CLIMATE_INDEX_LENGTH 气候指数算法预报长度
     */
    public static final int CLIMATE_INDEX_LENGTH = 3;

    /**
     * S2S_CMA_RECALCULATE_TIME S2S中国中心回算资料时间
     */
    public static final LocalDateTime S2S_CMA_RECALCULATE_TIME = LocalDateTime.of(LocalDate.of(2015,1,1), LocalTime.MIN);

    /**
     * S2S_UKMO_RECALCULATE_TIME S2S英国中心回算资料时间
     */
    public static final LocalDateTime S2S_UKMO_RECALCULATE_TIME = LocalDateTime.of(LocalDate.of(2015,4,17), LocalTime.MIN);

    /**
     * S2S_ECMWF_RECALCULATE_TIME S2S欧洲中心回算资料时间
     */
    public static final LocalDateTime S2S_ECMWF_RECALCULATE_TIME = LocalDateTime.of(LocalDate.of(2015,3,5), LocalTime.MIN);

    /**
     * S2S_NCEP_RECALCULATE_TIME S2S美国中心回算资料时间
     */
    public static final LocalDateTime S2S_NCEP_RECALCULATE_TIME = LocalDateTime.of(LocalDate.of(2015,1,1), LocalTime.MIN);

    /**
     * S2S_CMA_RETURN_YEAR S2S中国中心回算初始年份
     */
    public static final String S2S_CMA_RETURN_YEAR = "2015";

    /**
     * S2S_CMA_BEGIN_YEAR 回算资料开始年
     */
    public static final int S2S_CMA_BEGIN_YEAR = 1994;

    /**
     * S2S_NCEP_RETURN_YEAR S2S美国中心回算初始年份
     */
    public static final String S2S_NCEP_RETURN_YEAR = "2015";

    /**
     * S2S_NCEP_BEGIN_YEAR 回算资料开始年
     */
    public static final int S2S_NCEP_BEGIN_YEAR = 1999;

    /**
     * S2S_ECMWF_RETURN_YEAR S2S欧洲中心回算初始年份
     */
    public static final String S2S_ECMWF_RETURN_YEAR = "2015";

    /**
     * S2S_ECMWF_BEGIN_YEAR 回算资料开始年
     */
    public static final int S2S_ECMWF_BEGIN_YEAR = 1995;

    /**
     * S2S_UKMO_RETURN_YEAR S2S英国中心回算初始年份
     */
    public static final String S2S_UKMO_RETURN_YEAR = "2016";

    /**
     * S2S_UKMO_BEGIN_YEAR 回算资料开始年
     */
    public static final int S2S_UKMO_BEGIN_YEAR = 1993;

    /**
     * LON 经度通用关键字
     */
    public static final String LON = "lon";

    /**
     * LAT 纬度通用关键字
     */
    public static final String LAT = "lat";

    /***
     * TIME 时间通用关键字
     */
    public static final String TIME = "time";

    /**
     * MISSING_VALUE 缺测值
     */
    public static final double MISSING_VALUE = -999;

    /**
     * AbnormalValue cimiss逐日降水和气温异常值大于
     */
    public static final double ABNORMAL_VALUE = 1000.0;

    /**
     * CIMISS_MISSING_VALUE cimiss接口返回缺测值
     */
    public static final double CIMISS_MISSING_VALUE = 999999D;

    /**
     * CMI_MISSING_VALUE 干旱MCI返回缺测值
     */
    public static final double MCI_MISSING_VALUE = -9999;

    /**
     * DERF_BEGIN_YEAR derf资料的第一年
     */
    public static final int DERF_BEGIN_YEAR = 1983;

    /**
     * CFSV_BEGIN_YEAR cfsv逐日资料的第一年
     */
    public static final int CFSV_BEGIN_YEAR = 2014;

    public static final int ALGORITHM_BEGIN_YEAR = 2011;

    /**
     * INDEX_DATE_CONVERTER_YEAR
     * 索引和时间转换需要的年，均生函数预测下一年的，一定要保证当年和下一年都为平年才行
     */
    public static final int INDEX_DATE_CONVERTER_YEAR = 1982;

    /**
     * CIMISS_BEGIN_YEAR cimiss资料基态起始年
     */
    public static final int CIMISS_BEGIN_YEAR = 1983;

    /**
     * CIMISS_HISTORY_BEGIN_YEAR cimiss历史资料起始年
     */
    public static final int CIMISS_HISTORY_BEGIN_YEAR = 1961;

    /**
     * MODES_EC_BEGIN_YEAR modes的ec资料第一年1981是system4,1993是system5
     */
    public static final int MODES_EC_BEGIN_YEAR = 1993;

    /**
     * MODES_CFSV_BEGIN_YEAR modes的cfsv资料第一年
     */
    public static final int MODES_CFSV_BEGIN_YEAR = 1982;

    /**
     * MODES_BCC_BEGIN_YEAR modes的bcc资料第一年
     */
    public static final int MODES_BCC_BEGIN_YEAR = 1991;

    /**
     * CLIMATE_INDEX_BEGIN_YEAR 气候指数资料的第一年
     */
    public static final int CLIMATE_INDEX_BEGIN_YEAR = 1951;

    /**
     * NCEP_BEGIN_YEAR NCEP资料第一年
     */
    public static final int NCEP_BEGIN_YEAR = 1983;

    /**
     * FAHRENHEIT_TO_CELSIUS 华氏度转摄氏度
     */
    public static final double FAHRENHEIT_TO_CELSIUS = 273.15;

    /**
     * ONE_DAY 描述此常量
     */
    public static final String ONE_DAY = "01";

    /**
     * POST POST请求方式
     */
    public static final String POST = "POST";

    /**
     * AND 传参间隔
     */
    public static final String AND = "&";

    /**
     * CONTENT_TYPE 内容类型
     */
    public static final String CONTENT_TYPE = "Content-Type";

    /**
     * APPLICATION_FORM FORM表单提交请求
     */
    public static final String APPLICATION_FORM = "application/x-www-form-urlencoded";

    /**
     * GRIB cfsv逐日下载grib目录
     */
    public static final String GRIB = "grib";

    /**
     * DATA_FORECAST_MIN_INDEX 模式资料预报时间的最小索引值
     */
    public static final int DATA_FORECAST_MIN_INDEX = 0;

    /**
     * DATA_RANGE_SITE_INFO 表名
     */
    public static final String DATA_RANGE_SITE_INFO = "DATA_RANGE_SITE_INFO";

    /**
     * LEAP_YEAR 定义一个闰年，用来处理2月29日
     */
    public static final int LEAP_YEAR = 2016;

    /**
     * ALL_ELEMENT_CODE：所有要素枚举类型
     */
    public static final String ALL_ELEMENT_CODE = "020000";

    /**
     * 业务使用：字符串true
     */
    public static final String TRUE = "true";

    /**
     * 业务使用：字符串false
     */
    public static final String FALSE = "false";

    /**
     * ORIGIN 原值
     */
    public static final String ORIGIN = "Origin";

    /**
     * ANOMALY 距平值
     */
    public static final String ANOMALY = "Anomaly";

    /**
     * ANOMALY_RATE 距平率
     */
    public static final String ANOMALY_RATE = "AnomalyRate";

    /**
     * 业务使用：近30天
     */
    public static final int RECENT_THIRTY_DAYS = 30;

    /**
     * 业务使用：近半年
     */
    public static final int RECENT_SIX_MONTHS = 6;

    /**
     * 数据监控使用
     */
    public static final String DATA_IS_LATE = "数据到达延误";
    /**
     * 数据监控使用
     */
    public static final String DATA_IS_NOT_COMPLETE = "文件存储不完整";
    /**
     * 数据监控使用
     */
    public static final String DATA_IS_ABNORMAL = "异常";
    /**
     * 数据监控使用
     */
    public static final String DATA_IS_NORMAL = "正常";

    /**
     * SINGLE_QUOTATION_MARK 单引号
     */
    public static final String SINGLE_QUOTATION_MARK = "'";
    /**
     * TIME_SCALE_DAY 时间尺度-日
     */
    public static final String TIME_SCALE_DAY = "1";
    /**
     * TIME_SCALE_MONTH 时间尺度-月
     */
    public static final String TIME_SCALE_MONTH = "2";
    /**
     * TIME_SCALE_SEASON 时间尺度-季
     */
    public static final String TIME_SCALE_SEASON = "3";
    /**
     * TIME_SCALE_YEAR 时间尺度-年
     */
    public static final String TIME_SCALE_YEAR = "4";
    /**
     * SEASON_SPRING 春季
     */
    public static final String SEASON_SPRING = "1";
    /**
     * SEASON_SUMMER 夏季
     */
    public static final String SEASON_SUMMER = "2";
    /**
     * SEASON_AUTUMN 秋季
     */
    public static final String SEASON_AUTUMN = "3";
    /**
     * SEASON_WINTER 冬季
     */
    public static final String SEASON_WINTER = "4";

    /**
     * USER_PASSWORD 用户默认密码
     */
    public static final String USER_PASSWORD = "admin123456";

    /**
     * ADMIN_ROLE_ID admin角色id
     */
    public static final int ADMIN_ROLE_ID = 1;

    /**
     * NO_HEIGH 前端页面使用：当要素没有高度的情况
     */
    public static final String NO_HEIGH = "无";

    /**
     * WAVE_SYMBOL 波浪符号
     */
    public static final String WAVE_SYMBOL = "~";

    /**
     * STATION_AT_LEAST_NUM 最少站点数
     */
    public static final int STATION_AT_LEAST_NUM = 90;

    /**
     * TCC TCC关键字
     */
    public static final String TCC = "tcc";

    /**
     * MESSAGE_KIND_EXTENSION_HEAVY_T 报文种类：延伸期强降温
     */
    public static final int MESSAGE_KIND_EXTENSION_HEAVY_T = 1;
    /**
     * MESSAGE_KIND_EXTENSION_HEAVY_PRE 报文种类：延伸期强降水
     */
    public static final int MESSAGE_KIND_EXTENSION_HEAVY_PRE = 2;

    /**
     * MESSAGE_KIND_EXTENSION_HIGH_T 报文种类：延伸期高温
     */
    public static final int MESSAGE_KIND_EXTENSION_HIGH_T = 3;

    /**
     * MESSAGE_KIND_MONTH 报文种类：月报
     */
    public static final int MESSAGE_KIND_MONTH = 4;

    /**
     * MESSAGE_KIND_SEASON 报文种类：季报
     */
    public static final int MESSAGE_KIND_SEASON = 5;

    /**
     * TITLE_EXTENSION_TEM 报文标题前缀：延伸期气温
     */
    public static final String TITLE_EXTENSION_TEM = "延伸期-气温_";

    /**
     * TITLE_EXTENSION_PRE 报文标题前缀：延伸期降水
     */
    public static final String TITLE_EXTENSION_PRE = "延伸期-降水_";

    /**
     * TITLE_EXTENSION_PRE 报文标题前缀：延伸期降水
     */
    public static final String TITLE_EXTENSION_HIGH_T = "延伸期-高温_";

    /**
     * TITLE_MONTH 报文标题前缀：月
     */
    public static final String TITLE_MONTH = "月_";

    /**
     * TITLE_SEASON 报文标题前缀：季
     */
    public static final String TITLE_SEASON = "季_";

    /**
     * SPRING_FLAG 春季
     */
    public static final String SPRING_FLAG = "03_05";

    /**
     * SUMMER_FLAG 夏季
     */
    public static final String SUMMER_FLAG = "06_08";

    /**
     * AUTUMN_FLAG 秋季
     */
    public static final String AUTUMN_FLAG = "09_11";

    /**
     * WINTER_FLAG 冬季
     */
    public static final String WINTER_FLAG = "12_02";

    /**
     * FIRST_TEN_DAY_PERIOD_OF_A_MONTH 上旬最后一天
     */
    public static final int FIRST_TEN_DAYS_PERIOD_OF_A_MONTH = 10;

    /**
     * MIDDLE_TEN_DAYS_PERIOD_OF_A_MONTH 中旬最后一天
     */
    public static final int MIDDLE_TEN_DAYS_PERIOD_OF_A_MONTH = 20;

    /**
     * PRE_THRESHOLD 延伸期强降水阈值
     */
    public static final String PRE_THRESHOLD = "010";

    /**
     * TEM_THRESHOLD 延伸期强降温阈值
     */
    public static final String TEM_THRESHOLD = "040";

    /**
     * HIGH_TEM_THRESHOLD 描述此常量
     */
    public static final String HIGH_TEM_THRESHOLD = "035";

    /**
     * NCEP_MONTHLY_NC_BEGIN_MONTH ncep逐月nc文件起始月份
     */
    public static final int NCEP_MONTHLY_NC_BEGIN_MONTH = 1;

    /**
     * NCEP_MONTHLY_NC_BEGIN_YEAR ncep逐月通用要素nc文件起始年份
     */
    public static final int NCEP_MONTHLY_NC_BEGIN_YEAR = 1948;

    /**
     * NCEP_MONTHLY_NC_SST_BEGIN_YEAR ncep逐月海温nc文件起始年份
     */
    public static final int NCEP_MONTHLY_NC_SST_BEGIN_YEAR = 1854;

    /**
     * SPRING_START_MONTH 气候中春天开始月份
     */
    public static final int SPRING_START_MONTH = 3;

    /**
     * SUMMER_START_MONTH 气候中夏天开始月份
     */
    public static final int SUMMER_START_MONTH = 6;

    /**
     * AUTUMN_START_MONTH 气候中秋天开始月份
     */
    public static final int AUTUMN_START_MONTH = 9;

    /**
     * WINTER_START_MONTH 气候中冬天开始月份
     */
    public static final int WINTER_START_MONTH = 12;

    /**
     * PLAN_A 海温相似订正方案一
     */
    public static final String PLAN_A = "PLAN_A";

    /**
     * PLAN_B 海温相似订正方案二
     */
    public static final String PLAN_B = "PLAN_B";

    /**
     * PLAN_C 海温相似订正方案三
     */
    public static final String PLAN_C = "PLAN_C";

    /**
     * PLAN_D 海温相似订正方案四
     */
    public static final String PLAN_D = "PLAN_D";

    /**
     * NCEP_CLIMATE_STATE_BEGIN_YEAR ncep气候态起始年
     */
    public static final int NCEP_CLIMATE_STATE_BEGIN_YEAR = 1981;

    /**
     * NCEP_CLIMATE_STATE_END_YEAR ncep气候态结束年
     */
    public static final int NCEP_CLIMATE_STATE_END_YEAR = 2010;

    /**
     * NCEP_DAILY_KEYWORD ncep逐日关键字
     */
    public static final String NCEP_DAILY_KEYWORD = "day";

    /**
     * NCEP_MONTHLY_KEYWORD ncep逐月关键字
     */
    public static final String NCEP_MONTHLY_KEYWORD = "mon";

    /**
     * ATMOSPHERIC_CIRCULATION_INDEX 环流指数
     */
    public static final String ATMOSPHERIC_CIRCULATION_INDEX = "M_Atm_Nc";

    /**
     * SEA_SURFACE_TEMPERATURE_INDEX 海温指数
     */
    public static final String SEA_SURFACE_TEMPERATURE_INDEX = "M_Oce_Er";

    /**
     * OTHER_INDEX 其他指数
     */
    public static final String OTHER_INDEX = "M_Ext";

    /**
     * NOTICE__NO_SEND 公告未发送
     */
    public static final int NOTICE__NO_SEND = 0;

    /**
     * NOTICE_SEND 公告发送
     */
    public static final int NOTICE_SEND = 1;

    /**
     * NOTICE_NO_VIEW 公告未查看
     */
    public static final int NOTICE_NO_VIEW = 0;

    /**
     * NOTICE_VIEW 公告已查看
     */
    public static final int NOTICE_VIEW = 1;

    /**
     * WORD_DOC_SUFFIX doc文件后缀
     */
    public static final String WORD_DOC_SUFFIX = ".doc";

    /**
     * WORD_DOCX_SUFFIX docx文件后缀
     */
    public static final String WORD_DOCX_SUFFIX = ".docx";

    /**
     * ZIP_SUFFIX zip压缩文件后缀
     */
    public static final String ZIP_SUFFIX = ".zip";

    /**
     * PDF_SUFFIX pdf文件后缀
     */
    public static final String PDF_SUFFIX = ".pdf";

    /**
     * CODE_GBK gbk编码
     */
    public static final String CODE_GBK = "gbk";

    /**
     * CODE_UTF_8 UTF-8编码
     */
    public static final String CODE_UTF8 = "UTF-8";

    /**
     * TOTAL_AVERAGE_STR 合计平均值
     */
    public static final String TOTAL_AVERAGE_STR = "合计平均值";

}
