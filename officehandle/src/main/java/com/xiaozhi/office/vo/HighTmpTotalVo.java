package com.xiaozhi.office.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 高温汇总vo
 *
 * @author zhangzy
 * @date 2020/8/15-16:24
 * @since v1.0
 */
@ApiModel("高温汇总vo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HighTmpTotalVo {

    @ApiModelProperty(value = "区域名称")
    private String areaName;

    @ApiModelProperty(value = "大于等于35度天数")
    private Integer gt35Days;

    @ApiModelProperty(value = "35-37度之间天数")
    private Integer gt35lt37Days;

    @ApiModelProperty(value = "大于等于37度天数")
    private Integer gt37Days;

    @ApiModelProperty(value = "37-39度之间天数")
    private Integer gt37lt39Days;

    @ApiModelProperty(value = "大于等于40度天数")
    private Integer gt40Days;
}
