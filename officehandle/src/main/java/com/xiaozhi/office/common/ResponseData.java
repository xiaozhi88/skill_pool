package com.xiaozhi.office.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 描述该类实现的功能
 *
 * @param <T> 描述此泛型参数
 * @author LeoRmAo
 * @date 20200113 17:00:09
 * @since v1.0
 */
@Data
@NoArgsConstructor
public class ResponseData<T> {
    private T obj;

    private List<T> list;

    private Page page;

    public ResponseData(T obj) {
        this.obj = obj;
    }

    public ResponseData(List<T> list) {
        this.list = list;
    }

    public ResponseData(List<T> list, Page page) {
        this.list = list;
        this.page = page;
    }
}
