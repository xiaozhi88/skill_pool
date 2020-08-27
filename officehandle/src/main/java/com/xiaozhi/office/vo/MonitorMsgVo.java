package com.xiaozhi.office.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 气候监测信息vo
 *
 * @author zhangzy
 * @date 2020/8/15-14:17
 * @since v1.0
 */
@ApiModel("气候监测信息vo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonitorMsgVo {
    @ApiModelProperty(value = "产品标题")
    private String productTitle;

    @ApiModelProperty(value = "产品简介")
    private String productIntro;

    @ApiModelProperty(value = "开始时间")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "发送时间字符串", name = "2020-08-20")
    private String sendTime;

    @ApiModelProperty(value = "正文内容")
    private String content;
}
