package com.xiaozhi.office.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 产品vo
 *
 * @author zhangzy
 * @date 2020/8/19-16:02
 * @since v1.0
 */
@ApiModel("产品vo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductVo {
    @ApiModelProperty(value = "产品id")
    private String id;

    @ApiModelProperty(value = "产品标题")
    private String title;

    @ApiModelProperty(value = "word文件路径")
    private String wordPath;

    @ApiModelProperty(value = "pdf文件路径")
    private String pdfPath;

    @ApiModelProperty(value = "产品类型code")
    private String typeCode;

    @ApiModelProperty(value = "产品状态code")
    private String statusCode;

    @ApiModelProperty(value = "最小创建时间")
    private LocalDateTime minCreateTime;

    @ApiModelProperty(value = "最大创建时间")
    private LocalDateTime maxCreateTime;
}
