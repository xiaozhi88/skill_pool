package com.xiaozhi.office.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 产品制作配置类
 *
 * @author zhangzy
 * @date 2020/8/15-17:25
 * @since v1.0
 */
@Data
@ConfigurationProperties(prefix = "wordproduct")
public class WordProductProperty {
    /**
     * ftl模板文件根路径
     */
    private String ftlBasePath;

    /**
     * 高温日数ftl模板文件根路径
     */
    private String highTmpDaysFtlFileName;

    /**
     * 气候监测ftl模板文件根路径
     */
    private String climateMonitorFtlName;

    /**
     * 生成的word文件存放根路径
     */
    private String wordOutBasePath;

    /**
     * 产品存放根路径(docx,pdf)
     */
    private String productBasePath;

    /**
     * 模板zip文件存放根路径
     */
    private String zipFileBasePath;

    /**
     * 气候监测zip文件存放子路径
     */
    private String climateMonitorZipFileChildPath;

    /**
     * 气候监测模板zip文件名称字符串
     */
    private String climateMonitorZipFileBaseName;

    /**
     * 高温日数模板zip文件存放子路径
     */
    private String highTmpZipFileChildPath;

    /**
     * 高温日数模板zip文件名称字符串
     */
    private String monthClimateEvaluateZipFileChildPath;

    /**
     * 月气候评价zip文件存放子路径
     */
    private String monthClimateEvaluateZipFileBaseName;

    /**
     * 月气候评价模板zip文件名称字符串
     */
    private String highTmpZipFileBaseName;

    /**
     * 季气候评价模板zip文件存放子路径
     */
    private String quarterClimateEvaluateZipFileChildPath;

    /**
     * 季气候评价模板zip文件名称字符串
     */
    private String quarterClimateEvaluateZipFileBaseName;

    /**
     * 暴雨过程模板zip文件存放子路径
     */
    private String heavyRainProcessZipFileChildPath;

    /**
     * 暴雨过程模板zip文件名称字符串
     */
    private String heavyRainProcessZipFileBaseName;

    /**
     * 附件上传根路径
     */
    private String accessoryFileBasePath;

    /**
     * 文件服务器信息
     */
    private String fileServer;

}
