package com.meitq.cifox.collect.service.impl;

import com.meitq.cifox.collect.dto.CrossYearDto;
import com.meitq.cifox.collect.dto.HomePageGridImgDto;
import com.meitq.cifox.collect.dto.NclColorDto;
import com.meitq.cifox.collect.feign.CallNclTianziService;
import com.meitq.cifox.collect.service.GridForecastService;
import com.meitq.cifox.collect.utils.GridForecastUtil;
import com.meitq.cifox.common.client.BaseEsClient;
import com.meitq.cifox.common.config.CommonProperty;
import com.meitq.cifox.common.config.NclProperty;
import com.meitq.cifox.common.constant.GenerateImageConstant;
import com.meitq.cifox.common.constant.SystemConstant;
import com.meitq.cifox.common.dao.LatestDataRecordDao;
import com.meitq.cifox.common.dao.VisualizationGridImgDao;
import com.meitq.cifox.common.entity.LatestDataRecord;
import com.meitq.cifox.common.entity.VisualizationGridImg;
import com.meitq.cifox.common.enums.DataPeriodEnum;
import com.meitq.cifox.common.enums.LatestDataElementEnum;
import com.meitq.cifox.common.enums.LatestDataTypeEnum;
import com.meitq.cifox.common.model.BaseResponse;
import com.meitq.cifox.common.utils.EsIndexUtils;
import com.meitq.cifox.common.utils.LocalDateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 格点预报
 *
 * @author zhangzy
 * @date 2020/9/15-15:30
 * @since v1.0
 */
@Service
@Log4j2
@AllArgsConstructor
public class GridForecastServiceImpl implements GridForecastService {

    /**
     * RANGE_NorthernHemisphere 地区范围，北半球
     */
    public static final String RANGE_NORTHERN_HEMISPHERE = "NorthernHemisphere";

    /**
     * RANGE_NORTHEAST_ASIA 地区范围，东北亚
     */
    public static final String RANGE_NORTHEAST_ASIA = "NortheastAsia";

    /**
     * RANGE_CHINA 地区范围，中国
     */
    public static final String RANGE_CHINA = "China";

    /**
     * RANGE_YANGTZE_RIVER 地区范围，扬子江
     */
    public static final String RANGE_YANGTZE_RIVER = "YangtzeRiver";

    /**
     * RANGE_SOUTHWEST_CHINA 地区范围，西南地区
     */
    public static final String RANGE_SOUTHWEST_CHINA = "SouthwestChina";

    /**
     * RANGE_CHONG_QING 地区范围，重庆
     */
    public static final String RANGE_CHONG_QING = "Chongqing";

    /**
     * SHOW_CONTOUR_LINE 展示方式，等值线
     */
    public static final String SHOW_CONTOUR_LINE = "ContourLine";

    /**
     * SHOW_STAIN 展示方式，色斑图
     */
    public static final String SHOW_STAIN = "Stain";

    /**
     * SHOW_LATTICE_VALUES 展示方式，格点数据
     */
    public static final String SHOW_LATTICE_VALUES = "LatticeValues";

    /**
     * SHOW_WIND 矢量风
     */
    public static final String SHOW_WIND = "wind";

    /**
     * PROJECTION_LAMBERT 投影方式，兰勃特投影
     */
    public static final String PROJECTION_LAMBERT = "Lambert";

    /**
     * ZCQF_ANOMALY 气温距平预报或降水量距平率预报(根据产品类型再分)
     */
    public static final String ZCQF_ANOMALY = "anomaly";

    /**
     * ZCQF_LEVELINGRATE 气温距平概率预报或降水量距平率概率预报(根据产品类型再分)
     */
    public static final String ZCQF_LEVELINGRATE = "levelingRate";

    private static final String NORTHERN_HEMISPHERE_MAX_LON = "359";
    private static final String NORTHERN_HEMISPHERE_MIN_LON = "0";
    private static final String NORTHERN_HEMISPHERE_MAX_LAT = "90";
    private static final String NORTHERN_HEMISPHERE_MIN_LAT = "0";

    private static final String EAST_ASIA_MAX_LON = "160";
    private static final String EAST_ASIA_MIN_LON = "60";
    private static final String EAST_ASIA_GH500_MIN_LON = "40";
    private static final String EAST_ASIA_GH500_MAX_LAT = "80";
    private static final String EAST_ASIA_MAX_LAT = "70";
    private static final String EAST_ASIA_MIN_LAT = "0";

    private static final String CHINA_MAX_LON = "135";
    private static final String CHINA_MIN_LON = "70";
    private static final String CHINA_MAX_LAT = "55";
    private static final String CHINA_MIN_LAT = "15";

    private static final String YANGTZE_RIVER_MAX_LON = "115";
    private static final String YANGTZE_RIVER_MIN_LON = "90";
    private static final String YANGTZE_RIVER_MAX_LAT = "37.5";
    private static final String YANGTZE_RIVER_MIN_LAT = "22.5";

    private final static String SOUTHWEST_CHINA_MAX_LON = "115";
    private final static String SOUTHWEST_CHINA_MIN_LON = "95";
    private final static String SOUTHWEST_CHINA_MAX_LAT = "35";
    private final static String SOUTHWEST_CHINA_MIN_LAT = "22.5";

    private final static String CHONGQING_MAX_LON = "110.5";
    private final static String CHONGQING_MIN_LON = "105";
    private final static String CHONGQING_MAX_LAT = "32.5";
    private final static String CHONGQING_MIN_LAT = "28";

    private static final String HGT_PRODUCT_NAME = "位势高度500hpa距平";
    private static final String HGT_PRODUCT_CODE = "geo_h_500";
    private static final String HGT_COLOR = "BlueWhiteOrangeRed";
    private static final String HGT_LEVEL = "-50,-30,-20,-10,-5,0,5,10,20,30,50";

    private final NclProperty nclProperty;

    private final CommonProperty commonProperty;

    private final GridForecastUtil gridForecastUtil;

    private final CallNclTianziService callNclService;

    private final LatestDataRecordDao latestDataRecordDao;

    private final VisualizationGridImgDao visualizationGridImgDao;

    private final BaseEsClient baseEsClient;

    @Override
    public void saveVisualizationGridImg() {
        HomePageGridImgDto dto = new HomePageGridImgDto();
        // 获取位势高度最新资料入库信息
        LatestDataTypeEnum dataTypeEnum = LatestDataTypeEnum.DERF_DAILY;
        // 首页需求只需要位势高度
        LatestDataElementEnum elementTypeEnum = LatestDataElementEnum.HGT;
        // 首页位势高度场默认为500hpa, 由于老系统采集系统有采集500hpa的位势高度资料,这里查询最新资料时间采用850hpa, 850和500采集是同步的
        String height = "850";
        LatestDataRecord latestDataRecord = latestDataRecordDao.queryRecord(dataTypeEnum.getDataType(), elementTypeEnum.getElementType(), height);
        LocalDateTime observeTime = latestDataRecord.getObserveTime();

        //设置起报时间和预报时间
        LocalDateTime currentTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        String startTime = LocalDateTimeUtils.localDateTimeToString(currentTime.plusDays(1), LocalDateTimeUtils.DATE_TIME_01);
        String endTime = LocalDateTimeUtils.localDateTimeToString(currentTime.plusDays(30), LocalDateTimeUtils.DATE_TIME_01);;
        dto.setStartTime(startTime);
        dto.setEndTime(endTime);
        dto.setObserveTime(LocalDateTimeUtils.localDateTimeToString(observeTime, LocalDateTimeUtils.DATE_TIME_01));
        dto.setInitialForecastYear(String.valueOf(currentTime.getYear()));

        String elementType = elementTypeEnum.getElementType();
        // 查询出已有图片数据
        List<VisualizationGridImg> visualizationGridImgs = visualizationGridImgDao.queryData(elementType);
        VisualizationGridImg old = CollectionUtils.isEmpty(visualizationGridImgs) ? new VisualizationGridImg() : visualizationGridImgs.get(0);
        boolean saveFlag = false;
        List<String> deleteUrls = new ArrayList<>();

        // 获取预报图
        try {
            String imgForecastUrl = getVisualizationGridForecastImg(dto, elementType);
            if (StringUtils.isNotBlank(imgForecastUrl)) {
                if (null == old) {
                    old = new VisualizationGridImg();
                    old.setId(elementType);
                    old.setImgForecastUrl(imgForecastUrl);
                    saveFlag = true;
                } else if (StringUtils.isAnyBlank(old.getImgForecastUrl())) {
                    old.setImgForecastUrl(imgForecastUrl);
                    saveFlag = true;
                } else if (!old.getImgForecastUrl().equals(imgForecastUrl)) {
                    //删除旧的路径
                    deleteUrls.add(old.getImgForecastUrl());
                    old.setImgForecastUrl(imgForecastUrl);
                    saveFlag = true;
                }
            }
        } catch (Exception e) {
            log.error("生成derf-{}预报图失败", elementType);
        }

        LocalDateTime updateTime = LocalDateTime.now();
        if (saveFlag) {
            old.setUpdateTime(updateTime);
            // 更新首页可视化图片信息
            ArrayList<VisualizationGridImg> gridImgArrayList = new ArrayList<>();
            gridImgArrayList.add(old);
            try {
                baseEsClient.batchUpdate(EsIndexUtils.getVisualizationGridImgIndexName(), gridImgArrayList);
            } catch (IOException e) {
                log.info("更新首页可视化图片失败");
            }
            log.info("保存可视化页面{}预报图片，{}", elementType, old);
        }
        deleteUrls.forEach(url -> {
            String path = url.replaceAll(commonProperty.getImageBaseUrl(), commonProperty.getImageBaseDir());
            log.info("删除旧的格点预报图片，path={}", path);
            File file = new File(path);
            if (file.exists()) {
                boolean delete = file.delete();
            }
        });

    }

    /**
     * 获取可视化首页，格点预报图片（位势高度，气温，降水，温度场）
     *
     * @param homePageGridImgDto 生成图片条件
     * @param productType        产品类型
     * @return 返回 String       图片路径
     * @author 周恒
     * @date 20190605 14:12:55
     * @since v1.0
     */
    private String getVisualizationGridForecastImg(HomePageGridImgDto homePageGridImgDto, String productType) {
        String initialForecastTime = homePageGridImgDto.getObserveTime();
        String initialForecastYear = homePageGridImgDto.getInitialForecastYear();
        String startTime = homePageGridImgDto.getStartTime();
        String endTime = homePageGridImgDto.getEndTime();
        //true 预报图; false 预报检验图
        boolean actuallyMode = true;
        String level = getGdcByProductType(productType);
        return derfVisualizationForecastImg(initialForecastTime, startTime, endTime,
                productType, initialForecastYear, level, actuallyMode);
    }

    /**
     * 获取可视化首页默认高度
     *
     * @param productType 描述此参数
     * @return 返回 string 描述此返回参数
     * @author 周恒
     * @date 20190716 16:40:52
     * @since v1.0
     */
    private String getGdcByProductType(String productType) {
        if (LatestDataElementEnum.HGT.getElementType().equals(productType)) {
            return "500hPa";
        }
        return "";
    }

    /**
     * 获取可视化页面，格点预报图和与预报检验图
     * 位势高度、气温、降水、温度场
     *
     * @param initialForecastTime 起报时间
     * @param startTime           预报开始时间
     * @param endTime             预报结束时间
     * @param elementType         要素类型
     * @param initialForecastYear 起报年
     * @param level              高度场(气温和降水无高度场，位势高度和温度场需要该参数)
     * @param actuallyMode        true为预报图，false为预报检验图
     * @return 返回 string        生成图片路径
     * @author 周恒
     * @date 20190605 14:41:23
     * @since v1.0
     */
    private String derfVisualizationForecastImg(String initialForecastTime, String startTime, String endTime, String elementType, String initialForecastYear, String level, boolean actuallyMode) {
        // 是否透明
        boolean transparent = false;
        //关闭实时检验
        CrossYearDto crossYearDto = new CrossYearDto();
        return findDataBasicList(LatestDataTypeEnum.DERF_DAILY.getDataType(), initialForecastTime, startTime, endTime, elementType,
                PROJECTION_LAMBERT, RANGE_NORTHEAST_ASIA, SHOW_STAIN,
                level, DataPeriodEnum.FIFTYDAY_DAY.getKey(), initialForecastYear, ZCQF_ANOMALY, crossYearDto, actuallyMode, transparent);
    }

    /**
     * 格点预报出图(非重庆区域)
     *
     * @param dataType            数据模式
     * @param initialForecastTime 起报时间
     * @param startTime           开始时间
     * @param endTime             结束时间
     * @param elementType         产品类型(003002...)
     * @param projection          投影方式(勃朗特)
     * @param range               地区范围(东北亚)
     * @param show                展示方式(Stain...<色斑图>)
     * @param level              位势高度
     * @param period              预报时效(004009...)
     * @param initialForecastYear 起报年份
     * @param zcqf                左侧区分(prediction...<"anomaly">)
     * @param crossYearDto        格点预报，是否跨年DTO
     * @param actuallyMode        true为预报图，false为预报检验图
     * @param transparent         透明/不透明
     * @return 返回 map 生成的ncl图片路径
     * @author caowei
     * @date 20180523 16:05:38
     * @since v1.0
     */
    public String findDataBasicList(String dataType, String initialForecastTime, String startTime, String endTime, String elementType,
                                    String projection, String range, String show, String level, String period, String initialForecastYear,
                                    String zcqf, CrossYearDto crossYearDto, boolean actuallyMode, boolean transparent) {

        if (StringUtils.isAnyBlank(dataType, initialForecastTime, startTime, endTime, elementType, projection, range,
                show, zcqf, initialForecastYear)) {
            return "";
        }
        String imgUrl = commonProperty.getFutureMonthSubPath() + "hgt" + SystemConstant.FILE_SEPARATOR;
        //处理高度（方便后面处理）
        String ncepGdc = getNcepGdc(level);
        level = processingHeight(level);
        //获取ncl路径和img名字
        List<String> nclUrlAndImgName = getNclUrlAndImgName(startTime, endTime, zcqf, elementType, level);
        String p8 = initialForecastTime.substring(0, 10) + SystemConstant.UNDERLINE + nclUrlAndImgName.get(1);
        String imagePath = commonProperty.getReadNcImageBaseDir() + imgUrl + p8 + SystemConstant.PNG_SUFFIX;
        String imgHttpUrl = imagePath.replace(commonProperty.getReadNcImageBaseDir(), commonProperty.getReadNcImageBaseUrl());
        File imgFile = new File(imagePath);
        if (imgFile.exists()) {
            return imgHttpUrl;
        }

        Map<String, String> map = new HashMap<>();
        map.put(GenerateImageConstant.PARAMETER38, "false");


        //设置所有nc文件参数，以及nclColr
        NclColorDto nclColor = setRealAndGroundStateFileMapInfo(initialForecastTime, elementType, period, dataType, level, map);

        //设置图片时间title
        GridForecastUtil.setImgTimeTitle(period, startTime, endTime, actuallyMode, crossYearDto, map);

        //设置实时文件，基态文件，跨年实时，跨年基态，开始和结束时间
        GridForecastUtil.setStartTimeAndEndTime(dataType, startTime, endTime, zcqf, map);

        //设置经纬度范围和范围
        setLatAndLonRange(range, map, elementType, level);

        //设置展示方式
        GridForecastUtil.setShow(show, range, map);

        // 投影方式,如:lambert和stereographic (分别对应兰伯特投影和极射投影)
        map.put(GenerateImageConstant.PARAMETER16, projection);

        //获取ncl路径
        String nclUrl = nclUrlAndImgName.get(0);

        //nclUrl请求路径
        map.put(GenerateImageConstant.NCL_FILE_PATH, nclUrl);
        // 图片完整路径
        map.put(GenerateImageConstant.PARAMETER3, commonProperty.getReadNcImageBaseDir() + imgUrl + p8);
        //生成的图片名称
        map.put(GenerateImageConstant.PARAMETER8, p8);
        //设置 选择的模式
        map.put(GenerateImageConstant.PARAMETER17, dataType);
        //设置高度
        GridForecastUtil.setHeight(dataType, level, ncepGdc, map);
        //设置年份
        map.put(GenerateImageConstant.PARAMETER23, initialForecastYear);

        //设置色卡
        GridForecastUtil.setColorCardMarker(nclColor, map);

        map.put(GenerateImageConstant.PARAMETER28, initialForecastTime.replace(SystemConstant.HORIZONTAL_LINE, SystemConstant.EMPTY_STRING));
        map.put(GenerateImageConstant.PARAMETER29, period);

        //图片生成路径
        map.put(GenerateImageConstant.PARAMETER18, commonProperty.getReadNcImageBaseDir() + imgUrl);

        //是否为透明色 p42
        map.put(GenerateImageConstant.PARAMETER42, transparent ? "1" : "0");
        try {
            // 调用ncl生成图片
            BaseResponse<String> stringBaseResponse = callNclService.callImageNcl(map);
            String successCode = "0";
            if (successCode.equals(stringBaseResponse.getCode())) {
                return imgHttpUrl;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            log.error(e2.getMessage());
        }
        return "";
    }

    /**
     * 获取ncl的路径，以及图片名
     *
     * @return 返回 list 1返回nclUrl，2返回imgName
     * @author 周恒
     * @date 20180926 17:25:48
     * @since v1.0
     */
    private List<String> getNclUrlAndImgName(String startTime, String endTime, String zcqf, String elementType, String gdc) {
        List<String> result = new ArrayList<>(2);
        // 图片名
        String p8 = startTime + SystemConstant.UNDERLINE + endTime + SystemConstant.UNDERLINE + zcqf;
        //ncl脚本文件路径
        String nclUrl = "";
        //位势高度
        if (elementType.equals(LatestDataElementEnum.HGT.getElementType())) {
            nclUrl = nclProperty.getGridForecastNclFileUrl() + nclProperty.getGhWind();
            p8 = p8 + "_ghWind_" + gdc;
        }
        result.add(nclUrl);
        result.add(p8);
        return result;
    }

    /**
     * 设置实时和基态nc文件名和关键字
     * p9 实时nc文件名
     * p13 基态nc文件名
     * p11 nc实时和基态文件头信息关键字
     *
     * @return 返回 Ncl颜色
     * @author 周恒
     * @date 20180926 16:05:39
     * @since v1.0
     */
    private NclColorDto setRealAndGroundStateFileMapInfo(String initialForecastTime, String elementType, String period,
                                                         String dataType, String height, Map<String, String> map) {
        gridForecastUtil.setNcFileNameAndKey(dataType, period, elementType, initialForecastTime, height, map);

        // 获取color(只有位势高度,暂固定写死)
        NclColorDto nclColor = new NclColorDto();
        nclColor.setProductName(HGT_PRODUCT_NAME);
        nclColor.setProductCode(HGT_PRODUCT_CODE);
        nclColor.setType(1);
        nclColor.setOfficialColor(HGT_COLOR);
        nclColor.setOfficialInterval(HGT_LEVEL);
        return nclColor;
    }

    /**
     * 获取ncep高度
     *
     * @author 周恒
     * @date 20180926 15:37:19
     * @since v1.0
     */
    public static String getNcepGdc(String gdc) {
        if (StringUtils.isNotBlank(gdc)) {
            return gdc.substring(0, gdc.length() - 3);
        }
        return "";
    }

    /**
     * 处理高度
     *
     * @author 周恒
     * @date 20180926 15:37:33
     * @since v1.0
     */
    public static String processingHeight(String gdc) {
        if (StringUtils.isNotBlank(gdc)) {
            gdc = gdc.substring(0, gdc.length() - 3);
            if (gdc.length() == 2) {
                gdc = "00" + gdc;
            }
            if (gdc.length() == 3) {
                gdc = "0" + gdc;
            }
        }
        return gdc;
    }

    /**
     * 设置经纬度范围
     *
     * @author 周恒
     * @date 20180926 16:40:15
     * @since v1.0
     */
    private void setLatAndLonRange(String range, Map<String, String> map, String productType, String gdc) {
        String p4 = "";
        String p5 = "";
        String p6 = "";
        String p7 = "";
        switch (range) {
            case RANGE_NORTHERN_HEMISPHERE:
                p4 = NORTHERN_HEMISPHERE_MAX_LON;
                p5 = NORTHERN_HEMISPHERE_MIN_LON;
                p6 = NORTHERN_HEMISPHERE_MAX_LAT;
                p7 = NORTHERN_HEMISPHERE_MIN_LAT;
                break;
            case RANGE_NORTHEAST_ASIA:
                p4 = EAST_ASIA_MAX_LON;
                p7 = EAST_ASIA_MIN_LAT;
                if (StringUtils.isNotBlank(gdc)
                        && productType.equals(LatestDataElementEnum.HGT.getElementType())
                        && "0500".equals(gdc)) {
                    //位势高度500hpa时，经纬度不一样
                    p5 = EAST_ASIA_GH500_MIN_LON;
                    p6 = EAST_ASIA_GH500_MAX_LAT;
                } else {
                    p5 = EAST_ASIA_MIN_LON;
                    p6 = EAST_ASIA_MAX_LAT;
                }
                break;
            case RANGE_CHINA:
                p4 = CHINA_MAX_LON;
                p5 = CHINA_MIN_LON;
                p6 = CHINA_MAX_LAT;
                p7 = CHINA_MIN_LAT;
                break;
            case RANGE_YANGTZE_RIVER:
                p4 = YANGTZE_RIVER_MAX_LON;
                p5 = YANGTZE_RIVER_MIN_LON;
                p6 = YANGTZE_RIVER_MAX_LAT;
                p7 = YANGTZE_RIVER_MIN_LAT;
                break;
            case RANGE_SOUTHWEST_CHINA:
                p4 = SOUTHWEST_CHINA_MAX_LON;
                p5 = SOUTHWEST_CHINA_MIN_LON;
                p6 = SOUTHWEST_CHINA_MAX_LAT;
                p7 = SOUTHWEST_CHINA_MIN_LAT;
                break;
            case RANGE_CHONG_QING:
                p4 = CHONGQING_MAX_LON;
                p5 = CHONGQING_MIN_LON;
                p6 = CHONGQING_MAX_LAT;
                p7 = CHONGQING_MIN_LAT;
                break;
            default:
        }
        map.put(GenerateImageConstant.PARAMETER4, p4);
        map.put(GenerateImageConstant.PARAMETER5, p5);
        map.put(GenerateImageConstant.PARAMETER6, p6);
        map.put(GenerateImageConstant.PARAMETER7, p7);
        map.put(GenerateImageConstant.PARAMETER41, range);
    }
}
