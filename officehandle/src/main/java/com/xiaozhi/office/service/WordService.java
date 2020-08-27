package com.xiaozhi.office.service;

import com.xiaozhi.office.common.BaseResponse;
import com.xiaozhi.office.vo.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 产品制作接口
 *
 * @author zhangzy
 * @date 2020/8/15-17:04
 * @since v1.0
 */
public interface WordService {

    /**
     * 高温日数产品制作(doc)
     *
     * @param highTmpToWordVo 高温日word制作vo
     * @return 返回 com.meitq.cifox.common.model.BaseResponse<java.lang.Boolean> 描述此返回参数
     * @author zhangzy
     * @date 2020/8/15
     * @date 2020/8/15
     * @since v1.0
     */
    BaseResponse<Boolean> addHighTmpDoc(HighTmpToWordVo highTmpToWordVo);

    /**
     * 高温日数产品制作(docx)
     *
     * @param highTmpToWordVo 高温日word制作vo
     * @return 返回 com.meitq.cifox.common.model.BaseResponse<java.lang.Boolean> 描述此返回参数
     * @author zhangzy
     * @date 2020/8/15
     * @date 2020/8/15
     * @since v1.0
     */
    BaseResponse<Boolean> addHighTmpDocx(HighTmpToWordVo highTmpToWordVo);

    /**
     * 气候监控产品制作(doc)
     *
     * @param monitorMsgVo 气候监控VO
     * @return 返回 com.meitq.cifox.common.model.BaseResponse<java.lang.Boolean> 描述此返回参数
     * @author zhangzy
     * @date 2020/8/17
     * @since v1.0
     */
    BaseResponse<Boolean> addClimateMonitorDoc(MonitorMsgVo monitorMsgVo);

    /**
     * 气候监控产品制作(docx)
     *
     * @param monitorMsgVo 气候监控VO
     * @return 返回 com.meitq.cifox.common.model.BaseResponse<java.lang.Boolean> 描述此返回参数
     * @author zhangzy
     * @date 2020/8/17
     * @since v1.0
     */
    BaseResponse<Boolean> addClimateMonitorDocx(MonitorMsgVo monitorMsgVo);

    /**
     * 月气候评价产品制作(docx)
     *
     * @param monthClimateEvaluateVo 月气候评价vo
     * @return 返回 com.meitq.cifox.common.model.BaseResponse<java.lang.Boolean> 描述此返回参数
     * @author zhangzy
     * @date 2020/8/19
     * @since v1.0
     */
    BaseResponse<Boolean> addMonthClimateEvaluateDocx(MonthClimateEvaluateVo monthClimateEvaluateVo);

    /**
     * 季气候评价产品制作(docx)
     *
     * @param quarterClimateEvaluateVo 季气候评价vo
     * @return 返回 com.meitq.cifox.common.model.BaseResponse<java.lang.Boolean> 描述此返回参数
     * @author zhangzy
     * @date 2020/8/19
     * @since v1.0
     */
    BaseResponse<Boolean> addQuarterClimateEvaluateDocx(QuarterClimateEvaluateVo quarterClimateEvaluateVo);

    /**
     * 暴雨过程产品制作(docx)
     *
     * @param heavyRainProcessVo 暴雨过程vo
     * @return 返回 com.meitq.cifox.common.model.BaseResponse<java.lang.Boolean> 描述此返回参数
     * @author zhangzy
     * @date 2020/8/19
     * @since v1.0
     */
    BaseResponse<Boolean> addHeavyRainProcessDocx(HeavyRainProcessVo heavyRainProcessVo);

    /**
     * 上传附件
     *
     * @param productId 产品id
     * @param file 文件
     * @author zhangzy
     * @date 2020/8/20
     * @since v1.0
     */
    void upload(Integer productId, MultipartFile file);

    /**
     * 文件下载
     *
     * @param productVo productVo
     * @param response response
     * @author zhangzy
     * @date 2020/8/20
     * @since v1.0
     */
    void download(ProductVo productVo, HttpServletResponse response) throws Exception;
}
