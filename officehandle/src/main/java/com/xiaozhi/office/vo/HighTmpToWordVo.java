package com.xiaozhi.office.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 高温日word制作vo
 *
 * @author zhangzy
 * @date 2020/8/15-17:01
 * @since v1.0
 */
@ApiModel("高温日word制作vo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HighTmpToWordVo {
    @ApiModelProperty(value = "产品标题")
    private String productTitle;

    @ApiModelProperty(value = "产品简介")
    private String productIntro;

    @ApiModelProperty(value = "产品制作时间年月字符串", name = "'2020-08'")
    private String generateTime;

//    @ApiModelProperty(value = "高温日数统计vo集合")
//    private List<HighTmpTotalVo> highTmpTotalVoList;
//
//    @ApiModelProperty(value = "高温日期vo集合")
//    private List<HighTempDateVo> highTempDateVoList;
}
