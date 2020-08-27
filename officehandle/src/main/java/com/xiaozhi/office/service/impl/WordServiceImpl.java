package com.xiaozhi.office.service.impl;

import com.xiaozhi.office.common.BaseException;
import com.xiaozhi.office.common.BaseResponse;
import com.xiaozhi.office.common.LocalDateTimeUtils;
import com.xiaozhi.office.common.SystemConstant;
import com.xiaozhi.office.property.WordProductProperty;
import com.xiaozhi.office.service.WordService;
import com.xiaozhi.office.utils.ExportWordOfDoc;
import com.xiaozhi.office.utils.ExportWordOfDocx;
import com.xiaozhi.office.utils.ZipUtils;
import com.xiaozhi.office.vo.*;
import entity.Product;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author zhangzy
 * @date 2020/8/15-17:04
 * @since v1.0
 */
@Service
@AllArgsConstructor
@Log4j2
public class WordServiceImpl implements WordService {
    /**
     * 产品制作配置
     */
    private final WordProductProperty wordProductProperty;

    @Override
    public BaseResponse<Boolean> addHighTmpDoc(HighTmpToWordVo highTmpToWordVo) {
        String productTitle = highTmpToWordVo.getProductTitle();
        HashMap<String, Object> dataMap = getHighTmpDataMap(highTmpToWordVo);
        String ftlBasePath = wordProductProperty.getFtlBasePath();
        String ftlFileName = wordProductProperty.getHighTmpDaysFtlFileName();
        String wordOutBasePath = wordProductProperty.getWordOutBasePath();
        String wordFileName = productTitle + ".docx";
        try{
            ExportWordOfDoc.createWord(ftlBasePath, ftlFileName, wordOutBasePath, wordFileName, dataMap);
            // 生成pdf
            com.meitq.cifox.api.module.productmanagement.utils.WordToPdfByLibreOffice.wordConverterToPdf(wordOutBasePath + wordFileName);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return BaseResponse.createBySuccess(Boolean.TRUE);
    }

    @Override
    public BaseResponse<Boolean> addHighTmpDocx(HighTmpToWordVo highTmpToWordVo) {
        String productTitle = highTmpToWordVo.getProductTitle();
        String fileName = highTmpToWordVo.getGenerateTime() + "highTmp" + System.currentTimeMillis();
        String productBasePath = wordProductProperty.getProductBasePath();
        String zipFileBasePath = wordProductProperty.getZipFileBasePath();
        String zipFileChildPath = wordProductProperty.getHighTmpZipFileChildPath();
        String zipFileBaseName = wordProductProperty.getHighTmpZipFileBaseName();
        String zipFilePath = zipFileBasePath + zipFileChildPath + zipFileBaseName + SystemConstant.ZIP_SUFFIX;
        String uZipDirPath = zipFileBasePath + zipFileChildPath + zipFileBaseName + SystemConstant.FILE_SEPARATOR;
        ZipUtils.unZip(zipFilePath, uZipDirPath);
        HashMap<String, Object> dataMap = getHighTmpDataMap(highTmpToWordVo);
        Boolean flag = exportWordAndPdf(fileName, productBasePath, zipFileBasePath, zipFileChildPath, uZipDirPath, dataMap, null);
        if (!flag){
            return BaseResponse.createBySuccess(Boolean.FALSE);
        }
        saveProduct(productTitle, fileName, com.meitq.cifox.api.module.productmanagement.enums.ProductTypeEnum.HIGH_TEMP_DAYS);
        return BaseResponse.createBySuccess(Boolean.TRUE);
    }

    @Override
    public BaseResponse<Boolean> addClimateMonitorDoc(MonitorMsgVo monitorMsgVo) {
        String productTitle = monitorMsgVo.getProductTitle();
        Map<String,Object> dataMap = new HashMap<>(2);
        String ftlBasePath = wordProductProperty.getFtlBasePath();
        String ftlFileName = wordProductProperty.getClimateMonitorFtlName();
        String wordOutBasePath = wordProductProperty.getWordOutBasePath();
        String wordFileName = productTitle + SystemConstant.WORD_DOC_SUFFIX;
        dataMap.put("data", monitorMsgVo);
        try{
            ExportWordOfDoc.createWord(ftlBasePath, ftlFileName, wordOutBasePath, wordFileName, dataMap);
            // 生成pdf
            com.meitq.cifox.api.module.productmanagement.utils.WordToPdfByLibreOffice.wordConverterToPdf(wordOutBasePath + wordFileName);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return BaseResponse.createBySuccess();
    }

    @Override
    public BaseResponse<Boolean> addClimateMonitorDocx(MonitorMsgVo monitorMsgVo) {
        String productTitle = monitorMsgVo.getProductTitle();
        String fileName = monitorMsgVo.getSendTime() + "monitorMsg" + System.currentTimeMillis();
        String productBasePath = wordProductProperty.getProductBasePath();
        String zipFileBasePath = wordProductProperty.getZipFileBasePath();
        String zipFileChildPath = wordProductProperty.getClimateMonitorZipFileChildPath();
        String zipFileBaseName = wordProductProperty.getClimateMonitorZipFileBaseName();
        String zipFilePath = zipFileBasePath + zipFileChildPath + zipFileBaseName + SystemConstant.ZIP_SUFFIX;
        String uZipDirPath = zipFileBasePath + zipFileChildPath + zipFileBaseName + SystemConstant.FILE_SEPARATOR;
        ZipUtils.unZip(zipFilePath, uZipDirPath);
        HashMap<String, Object> dataMap = new HashMap<>(2);
        dataMap.put("data", monitorMsgVo);
        Boolean flag = exportWordAndPdf(fileName, productBasePath, zipFileBasePath, zipFileChildPath, uZipDirPath, dataMap, null);
        if (!flag){
            return BaseResponse.createBySuccess(Boolean.FALSE);
        }
        // 保存入库
        saveProduct(productTitle, fileName, com.meitq.cifox.api.module.productmanagement.enums.ProductTypeEnum.CLIMATE_MONITOR);
        return BaseResponse.createBySuccess(Boolean.TRUE);
    }

    @Override
    public BaseResponse<Boolean> addMonthClimateEvaluateDocx(MonthClimateEvaluateVo monthClimateEvaluateVo) {
        //todo 待确定模板及数据
        Map<String,Object> dataMap = new HashMap<>();
        List<Map<String, String>> images = new ArrayList<>();
        String productTitle = monthClimateEvaluateVo.getProductTitle();
        String fileName = monthClimateEvaluateVo.getStartTimeStr() + "monthClimateEvalute" + System.currentTimeMillis();
        String productBasePath = wordProductProperty.getProductBasePath();
        String zipFileBasePath = wordProductProperty.getZipFileBasePath();
        String zipFileChildPath = wordProductProperty.getMonthClimateEvaluateZipFileChildPath();
        String zipFileBaseName = wordProductProperty.getMonthClimateEvaluateZipFileBaseName();
        String zipFilePath = zipFileBasePath + zipFileChildPath + zipFileBaseName + SystemConstant.ZIP_SUFFIX;
        String uZipDirPath = zipFileBasePath + zipFileChildPath + zipFileBaseName + SystemConstant.FILE_SEPARATOR;
        ZipUtils.unZip(zipFilePath, uZipDirPath);
        Boolean flag = exportWordAndPdf(fileName, productBasePath, zipFileBasePath, zipFileChildPath, uZipDirPath, dataMap, images);
        if (!flag){
            return BaseResponse.createBySuccess(Boolean.FALSE);
        }
        saveProduct(productTitle, fileName, com.meitq.cifox.api.module.productmanagement.enums.ProductTypeEnum.MONTH_CLIMATE_EVALUATE);
        return BaseResponse.createBySuccess(Boolean.TRUE);
    }

    @Override
    public BaseResponse<Boolean> addQuarterClimateEvaluateDocx(QuarterClimateEvaluateVo quarterClimateEvaluateVo) {
        //todo 待确定模板及数据
        Map<String,Object> dataMap = new HashMap<>();
        List<Map<String, String>> images = new ArrayList<>();
        String productTitle = quarterClimateEvaluateVo.getProductTitle();
        String fileName = quarterClimateEvaluateVo.getStartTimeStr() + "quarterClimateEvalute" + System.currentTimeMillis();
        String productBasePath = wordProductProperty.getProductBasePath();
        String zipFileBasePath = wordProductProperty.getZipFileBasePath();
        String zipFileChildPath = wordProductProperty.getQuarterClimateEvaluateZipFileChildPath();
        String zipFileBaseName = wordProductProperty.getQuarterClimateEvaluateZipFileBaseName();
        String zipFilePath = zipFileBasePath + zipFileChildPath + zipFileBaseName + SystemConstant.ZIP_SUFFIX;
        String uZipDirPath = zipFileBasePath + zipFileChildPath + zipFileBaseName + SystemConstant.FILE_SEPARATOR;
        ZipUtils.unZip(zipFilePath, uZipDirPath);
        Boolean flag = exportWordAndPdf(fileName, productBasePath, zipFileBasePath, zipFileChildPath, uZipDirPath, dataMap, images);
        if (!flag){
            return BaseResponse.createBySuccess(Boolean.FALSE);
        }
        saveProduct(productTitle, fileName, com.meitq.cifox.api.module.productmanagement.enums.ProductTypeEnum.QUARTER_CLIMATE_EVALUATE);
        return BaseResponse.createBySuccess(Boolean.TRUE);
    }

    @Override
    public BaseResponse<Boolean> addHeavyRainProcessDocx(HeavyRainProcessVo heavyRainProcessVo) {
        //todo 待确定模板及数据
        Map<String,Object> dataMap = new HashMap<>();
        List<Map<String, String>> images = new ArrayList<>();
        String productTitle = heavyRainProcessVo.getProductTitle();
        String fileName = heavyRainProcessVo.getStartTimeStr() + "heavyRainProcess" + System.currentTimeMillis();
        String productBasePath = wordProductProperty.getProductBasePath();
        String zipFileBasePath = wordProductProperty.getZipFileBasePath();
        String zipFileChildPath = wordProductProperty.getHeavyRainProcessZipFileChildPath();
        String zipFileBaseName = wordProductProperty.getHeavyRainProcessZipFileBaseName();
        String zipFilePath = zipFileBasePath + zipFileChildPath + zipFileBaseName + SystemConstant.ZIP_SUFFIX;
        String uZipDirPath = zipFileBasePath + zipFileChildPath + zipFileBaseName + SystemConstant.FILE_SEPARATOR;
        ZipUtils.unZip(zipFilePath, uZipDirPath);
        Boolean flag = exportWordAndPdf(fileName, productBasePath, zipFileBasePath, zipFileChildPath, uZipDirPath, dataMap, images);
        if (!flag){
            return BaseResponse.createBySuccess(Boolean.FALSE);
        }
        saveProduct(productTitle, fileName, com.meitq.cifox.api.module.productmanagement.enums.ProductTypeEnum.HEAVY_RAIN_PROCESS);
        return BaseResponse.createBySuccess(Boolean.TRUE);
    }

    @Override
    public void upload(Integer productId, MultipartFile file) {
        String accessoryFileBaseName = wordProductProperty.getAccessoryFileBasePath();
        File sourceFile = new File(accessoryFileBaseName, Objects.requireNonNull(file.getOriginalFilename()));
        File parentFile = sourceFile.getParentFile();
        if (!parentFile.exists() && !parentFile.mkdirs()) {
            throw new BaseException("父目录不存在且创建失败");
        }
        try (
            InputStream is = file.getInputStream();
            OutputStream os = new FileOutputStream(sourceFile)
        ){
            IOUtils.copy(is, os);
            log.info("附件上传成功!");
        }catch (IOException e) {
            e.printStackTrace();
        }
        // TODO: 2020/8/20 将附件地址写入数据库中对应id的产品中
        String sourcePath = sourceFile.getAbsolutePath();
    }

    @Override
    public void download(ProductVo productVo, HttpServletResponse response) throws Exception {
        String fileName = productVo.getTitle();
        String file = productVo.getWordPath();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition","attachment;filename=\""+ fileName +".docx"+"\"");

        byte[] buffer = new byte[1024];
        try (FileInputStream fis = new FileInputStream(file); BufferedInputStream bis = new BufferedInputStream(fis)) {
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
            log.info("文件下载成功: " + fileName);
        }
    }

    /**
     * 保存产品数据
     *
     * @param productTitle 标题
     * @param fileName 文件名
     * @param productTypeEnum 产品类别
     * @author zhangzy
     * @date 2020/8/20
     * @since v1.0
     */
    private void saveProduct(String productTitle, String fileName, com.meitq.cifox.api.module.productmanagement.enums.ProductTypeEnum productTypeEnum) {
        // 保存入库
        // TODO: 2020/8/20  section通过当前登录用户信息获取
        String section = "重庆市";
        String framer = "admin";
        String productBasePath = wordProductProperty.getProductBasePath();
        Product product = new Product();
        product.setTitle(productTitle);
        product.setType(productTypeEnum.getCode());
        product.setStatus(com.meitq.cifox.api.module.productmanagement.enums.ProductStatusEnum.UNCOMMIT.getCode());
        product.setSection(section);
        product.setFramer(framer);
        product.setWordPath(productBasePath + fileName + SystemConstant.WORD_DOCX_SUFFIX);
        product.setPdfPath(productBasePath + fileName + SystemConstant.PDF_SUFFIX);
        product.setCreateTime(LocalDateTime.now());
        // TODO: 2020/8/20 保存
        log.info("存入产品: " + product);
    }

    /**
     * 导出word文件及pdf(docx)
     *
     * @param productTitle 生成产品名称
     * @param productBasePath 产品根路径
     * @param zipFileBasePath 压缩模板文件根路径
     * @param zipFileChildPath 压缩模板文件子路径
     * @param uZipDirPath 解压文件夹路径
     * @param dataMap 模板导出所需数据
     * @return 返回 com.meitq.cifox.common.model.BaseResponse<java.lang.Boolean> 描述此返回参数
     * @author zhangzy
     * @date 2020/8/18
     * @since v1.0
     */
    private Boolean exportWordAndPdf(String productTitle, String productBasePath, String zipFileBasePath, String zipFileChildPath, String uZipDirPath, Map<String, Object> dataMap, List<Map<String, String>> images) {
        try {
            ExportWordOfDocx.exportDocx(new File(productBasePath + productTitle + SystemConstant.WORD_DOCX_SUFFIX), new File(uZipDirPath),
                    new File(zipFileBasePath + zipFileChildPath + "document.ftl"), new File(zipFileBasePath + zipFileChildPath + "document.xml.rels.ftl"), dataMap, images);
            if (new File(productBasePath + productTitle + SystemConstant.WORD_DOCX_SUFFIX).exists()){
                // 转pdf
                com.meitq.cifox.api.module.productmanagement.utils.WordToPdfByLibreOffice.wordConverterToPdf(productBasePath + productTitle + SystemConstant.WORD_DOCX_SUFFIX);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取高温日数模板导出数据
     *
     * @param highTmpToWordVo 高温日数模板导出vo
     * @return 返回 java.util.HashMap<java.lang.String,java.lang.Object> 模板导出数据
     * @author zhangzy
     * @date 2020/8/18
     * @since v1.0
     */
    private HashMap<String, Object> getHighTmpDataMap(HighTmpToWordVo highTmpToWordVo) {
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("year", highTmpToWordVo.getGenerateTime().split("-")[0]);
        dataMap.put("month", highTmpToWordVo.getGenerateTime().split("-")[1]);
        dataMap.put("endTime", LocalDateTimeUtils.localDateTimeToString(LocalDateTime.now(),
                LocalDateTimeUtils.DATE_TIME_01));
        ArrayList<HighTempDateVo> highTempDatelVos = new ArrayList<>();
        highTempDatelVos.add(new HighTempDateVo(
                "沙坪坝",
                "-", "-", "*", "-", "I", "*", "-", "-", "*", "I", "I", "I",
                "-", "-", "-", "-", "-", "*", "*", "-", "-", "-", "-",
                "-", "-", "-", "-", "-", "*", "-", ""
        ));
        highTempDatelVos.add(new HighTempDateVo(
                "北碚",
                "-", "-", "*", "-", "I", "*", "-", "-", "*", "I", "I", "I",
                "-", "-", "-", "-", "-", "*", "", "-", "I", "-", "-",
                "I", "-", "-", "*", "-", "*", "-", ""
        ));
        ArrayList<HighTmpTotalVo> highTmpTotalVos = new ArrayList<>();
        highTmpTotalVos.add(new HighTmpTotalVo("沙坪坝", 6, 3, 2, 1, 1));
        highTmpTotalVos.add(new HighTmpTotalVo("北碚", 4, 3, 2, 1, 1));

        dataMap.put("areaDayList", highTmpTotalVos);
        dataMap.put("areaDateList", highTempDatelVos);
        return dataMap;
    }


    public static void main(String[] args) {
        HashMap<String, Object> dataMap = new HashMap<>();
        List<Map<String, String>> images = new ArrayList<>();
        dataMap.put("userName", "哈哈");
        dataMap.put("type", "呵呵");
        dataMap.put("startTime", "2020-08-15");
        dataMap.put("endTime", "2020-08-17");
        dataMap.put("reason", "放松一下");
        HashMap<String, String> image1 = new HashMap<>();
        image1.put("image", "D:\\word_pdf_test\\demo\\1.png");
        image1.put("index", "m2");
        HashMap<String, String> image2 = new HashMap<>();
        image2.put("image", "D:\\word_pdf_test\\demo\\2.jpeg");
        image2.put("index", "m1");
        images.add(image1);
        images.add(image2);
        try {
            ZipUtils.unZip("D:\\word_pdf_test\\demo\\template\\demo\\abc.zip", "D:\\word_pdf_test\\demo\\template\\demo\\abc\\");
            new WordServiceImpl(new WordProductProperty()).exportWordAndPdf("1",
                    "D:\\word_pdf_test\\demo\\product\\",
                    "D:\\word_pdf_test\\demo\\template\\",
                    "demo\\",
                    "D:\\word_pdf_test\\demo\\template\\demo\\abc",
                    dataMap,
                    images
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
