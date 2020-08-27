package com.xiaozhi.office.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page {
    @ApiModelProperty(value = "每页数据条数")
    private Integer pageSize;

    @ApiModelProperty(value = "页数")
    private Integer pageNumber;

    @ApiModelProperty(value = "总条数")
    private Integer totalCount;
}
