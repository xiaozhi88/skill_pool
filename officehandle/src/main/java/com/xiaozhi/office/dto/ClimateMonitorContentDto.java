package com.xiaozhi.office.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 气候监测内容
 *
 * @author zhangzy
 * @date 2020/8/21-17:17
 * @since v1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClimateMonitorContentDto {

    @ApiModelProperty(value = "平均温")
    private Double avgT;

    @ApiModelProperty(value = "同期气温")
    private Double sameT;

    @ApiModelProperty(value = "平均温和同期气温绝对差值")
    private Double difT;

    @ApiModelProperty(value = "降水量")
    private Double pre;

    @ApiModelProperty(value = "常年降水")
    private Double samePre;

    @ApiModelProperty(value = "降水和常年降水绝对差值")
    private Double difPre;

    @ApiModelProperty(value = "日照时数")
    private Double sunHours;

    @ApiModelProperty(value = "常年日照时数")
    private Double sameSunHours;

    @ApiModelProperty(value = "日照时数和常年日照时数百分值")
    private Double difSunHours;
}
