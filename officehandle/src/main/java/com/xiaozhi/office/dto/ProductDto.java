package com.xiaozhi.office.dto;

import entity.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 产品dto
 *
 * @author zhangzy
 * @date 2020/8/19-15:54
 * @since v1.0
 */
@Data
@ApiModel("word产品DTO")
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    @ApiModelProperty(value = "产品id")
    private Integer id;

    @ApiModelProperty(value = "产品标题")
    private String title;

    @ApiModelProperty(value = "产品类别")
    private String type;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "部门")
    private String section;

    @ApiModelProperty(value = "制作者")
    private String framer;

    @ApiModelProperty(value = "word文件路径")
    private String wordPath;

    @ApiModelProperty(value = "pdf文件路径")
    private String pdfPath;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    private ProductDto entityToDto(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setType(product.getType());
        productDto.setStatus(product.getStatus());
        productDto.setFramer(product.getFramer());
        productDto.setSection(product.getSection());
        productDto.setWordPath(product.getWordPath());
        productDto.setPdfPath(product.getPdfPath());
        productDto.setCreateTime(product.getCreateTime());
        return productDto;
    }
}
