package com.xiaozhi.office.controller;

import com.xiaozhi.office.dto.ClimateMonitorContentDto;
import com.xiaozhi.office.dto.ProductDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * word及pdf产品制作
 *
 * @author zhangzy
 * @date 2020/8/15-16:05
 * @since v1.0
 */
@Api(tags = "word产品制作")
@RestController
@RequestMapping(value = "/word")
@Log4j2
@AllArgsConstructor
public class WordPdfController {

    private final  WordService wordService;

    private final WordProductProperty wordProductProperty;

    /**
     * PRODUCT_TEMPLATE_LIST 产品模板集合
     */
    private static final Map<String, List<Map<String, String>>> PRODUCT_TEMPLATE_LIST;

    static {
        PRODUCT_TEMPLATE_LIST = new HashMap<String, List<Map<String, String>>>();
        PRODUCT_TEMPLATE_LIST.put("ProductTypeList", ProductTypeEnum.getAll());
        PRODUCT_TEMPLATE_LIST.put("ProductStatusList", ProductStatusEnum.getAll());
    }

    @ApiOperation(value = "新增高温日数产品")
    @PostMapping(value = "/addHighTmpProduct")
    public BaseResponse<Boolean> addHighTmpProduct(@RequestBody HighTmpToWordVo highTmpToWordVo) {
        return wordService.addHighTmpDocx(highTmpToWordVo);
    }

    @ApiOperation(value = "新增气象监测信息产品")
    @PostMapping(value = "/addClimateMonitorProduct")
    public BaseResponse<Boolean> addClimateMonitorProduct(@RequestBody MonitorMsgVo monitorMsgVo){
        return wordService.addClimateMonitorDocx(monitorMsgVo);
    }

    @ApiOperation(value = "获取气象监测信息产品内容")
    @PostMapping(value = "/getClimateMonitorContent")
    public BaseResponse<ClimateMonitorContentDto> getClimateMonitorContent(@RequestBody MonitorMsgVo monitorMsgVo){
        LocalDateTime beginTime = monitorMsgVo.getBeginTime();
        LocalDateTime endTime = monitorMsgVo.getEndTime();

        // TODO: 2020/8/21 根据起止时间查询相关内容数值
        ClimateMonitorContentDto climateMonitorContentDto = new ClimateMonitorContentDto(
                26.5, 25.6, Math.abs(26.5 - 25.6),
                236.51, 165.51, Math.abs(236.51 - 265.51),
                89.63, 96.13, Math.abs(89.63 - 96.13)/89.63 * 100);
        climateMonitorContentDto.setAvgT(26.5);
        climateMonitorContentDto.setSameT(25.6);
        climateMonitorContentDto.setPre(236.51);
        climateMonitorContentDto.setSamePre(165.86);
        climateMonitorContentDto.setSunHours(89.63);
        climateMonitorContentDto.setSameSunHours(96.13);
        
        return BaseResponse.createBySuccess(climateMonitorContentDto);
    }

    @ApiOperation(value = "新增月气候评价产品")
    @PostMapping(value = "/addMonthClimateEvaluateProduct")
    public BaseResponse<Boolean> addMonthClimateEvaluateProduct(@RequestBody MonthClimateEvaluateVo monthClimateEvaluateVo){
        return wordService.addMonthClimateEvaluateDocx(monthClimateEvaluateVo);
    }

    @ApiOperation(value = "新增季气候评价产品")
    @PostMapping(value = "/addQuarterClimateEvaluateProduct")
    public BaseResponse<Boolean> addQuarterClimateEvaluateProduct(@RequestBody QuarterClimateEvaluateVo quarterClimateEvaluateVo){
        return wordService.addQuarterClimateEvaluateDocx(quarterClimateEvaluateVo);
    }

    @ApiOperation(value = "新增暴雨过程产品")
    @PostMapping(value = "/addHeavyRainProcessProduct")
    public BaseResponse<Boolean> addHeavyRainProcessProduct(@RequestBody HeavyRainProcessVo heavyRainProcessVo){
        return wordService.addHeavyRainProcessDocx(heavyRainProcessVo);
    }

    @ApiOperation(value = "获取下拉框信息")
    @GetMapping(value = "/getAllTemplate")
    public BaseResponse<Map<String, List<Map<String, String>>>> getAllTemplate(){
        return BaseResponse.createBySuccess(PRODUCT_TEMPLATE_LIST);
    }

    @ApiOperation(value = "获取产品列表")
    @PostMapping(value = "/getProductList")
    public BaseResponse<ProductDto> getProductList(@RequestBody ProductVo productVo){
        String fileServer = wordProductProperty.getFileServer();
        ArrayList<ProductDto> products = new ArrayList<>();
        products.add(new ProductDto(1, "2020年8月暴雨过程总结", "暴雨过程", "未提交", "重庆", "admin",
                fileServer + "/data/product/productOut/2020-08-01heavyRainProcess1597906082310.docx",
                fileServer + "/data/product/productOut/2020-08-01heavyRainProcess1597906082310.pdf",
                LocalDateTimeUtils.stringToLocalDateTime("2020-08-20 14:48:14", LocalDateTimeUtils.DATE_TIME_04)));
        products.add(new ProductDto(2, "2020年8月高温日数统计", "高温日数", "未提交", "重庆", "admin",
                fileServer + "/data/product/productOut/2020-08-20highTmp1597906475071.docx",
                fileServer + "/data/product/productOut/2020-08-20highTmp1597906475071.pdf",
                LocalDateTimeUtils.stringToLocalDateTime("2020-08-20 14:54:49", LocalDateTimeUtils.DATE_TIME_04)));
        products.add(new ProductDto(3, "2020年8月20气候监测", "气候监测", "未提交", "重庆", "admin",
                fileServer + "/data/product/productOut/2020-08-20monitorMsg1597906546492.docx",
                fileServer + "/data/product/productOut/2020-08-20monitorMsg1597906546492.pdf",
                LocalDateTimeUtils.stringToLocalDateTime("2020-08-20 14:55:55", LocalDateTimeUtils.DATE_TIME_04)));
        products.add(new ProductDto(4, "2020年8月气候评价", "气候月评价", "未提交", "重庆", "admin",
                fileServer + "/data/product/productOut/2020-08-01monthClimateEvalute1597906629437.docx",
                fileServer + "/data/product/productOut/2020-08-01monthClimateEvalute1597906629437.pdf",
                LocalDateTimeUtils.stringToLocalDateTime("2020-08-20 14:57:32", LocalDateTimeUtils.DATE_TIME_04)));
        products.add(new ProductDto(5, "2020年夏季气候评价", "气候季评价", "未提交", "重庆", "admin",
                fileServer + "/data/product/productOut/2020-06-01quarterClimateEvalute1597906695624.docx",
                fileServer + "/data/product/productOut/2020-06-01quarterClimateEvalute1597906695624.pdf",
                LocalDateTimeUtils.stringToLocalDateTime("2020-08-20 14:58:20", LocalDateTimeUtils.DATE_TIME_04)));
        return BaseResponse.createBySuccess(products);
    }

    @ApiOperation(value = "删除产品")
    @PostMapping(value = "/deleteProduct")
    public BaseResponse<Boolean> deleteProduct(@RequestBody ProductVo productVo){
        // todo 删除指定产品
        return BaseResponse.createBySuccess(Boolean.TRUE);
    }

    @ApiOperation(value = "上传产品附件")
    @PostMapping(value = "/uploadProduct")
    public BaseResponse<Boolean> uploadProduct(Integer productId, MultipartFile file){
        wordService.upload(productId, file);
        return BaseResponse.createBySuccess(Boolean.TRUE);
    }

    @ApiOperation(value = "下载产品")
    @PostMapping(value = "/downloadProduct")
    public BaseResponse<Boolean> downloadProduct(@RequestBody ProductVo productVo, HttpServletResponse response) throws Exception{
        wordService.download(productVo, response);
        return BaseResponse.createBySuccess(Boolean.TRUE);
    }

}
