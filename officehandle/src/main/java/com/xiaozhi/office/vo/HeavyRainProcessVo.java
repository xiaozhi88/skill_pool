package com.xiaozhi.office.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 暴雨过程
 *
 * @author zhangzy
 * @date 2020/8/19-15:33
 * @since v1.0
 */
@ApiModel("暴雨过程vo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeavyRainProcessVo {
    @ApiModelProperty(value = "产品标题")
    private String productTitle;

    @ApiModelProperty(value = "起始时间字符串")
    private String startTimeStr;

    @ApiModelProperty(value = "结束时间字符串")
    private String endTimeStr;

    //todo
}
