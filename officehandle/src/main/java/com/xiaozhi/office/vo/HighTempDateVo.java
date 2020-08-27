package com.xiaozhi.office.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 高温日期表
 *
 * @author zhangzy
 * @date 2020/8/15-13:30
 * @since v1.0
 */
@ApiModel("高温日期表vo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HighTempDateVo {
    @ApiModelProperty(value = "地区")
    private String area;
    @ApiModelProperty(value = "1号高温标记, “―”表示 < 35℃；35℃ ≤*< 37℃；37℃ ≤Ⅰ< 40℃；40℃ ≤ Ⅱ")
    private String d1;
    @ApiModelProperty(value = "2号高温标记")
    private String d2;
    @ApiModelProperty(value = "3号高温标记")
    private String d3;
    @ApiModelProperty(value = "4号高温标记")
    private String d4;
    private String d5;
    private String d6;
    private String d7;
    private String d8;
    private String d9;
    private String d10;
    private String d11;
    private String d12;
    private String d13;
    private String d14;
    private String d15;
    private String d16;
    private String d17;
    private String d18;
    private String d19;
    private String d20;
    private String d21;
    private String d22;
    private String d23;
    private String d24;
    private String d25;
    private String d26;
    private String d27;
    private String d28;
    private String d29;
    private String d30;
    private String d31;
}
