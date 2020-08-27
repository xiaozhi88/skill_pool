package com.xiaozhi.office.common;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

/**
 * 描述该类实现的功能
 *
 * @param <T> 描述此泛型参数
 * @author LeoRmAo
 * @date 20200113 17:00:09
 * @since v1.0
 */
@Getter
@Setter
public class BaseResponse<T> {

    private String code;

    private String msg;

    private ResponseData<T> data;


    private BaseResponse(String code) {
        this.code = code;
    }

    private BaseResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> BaseResponse<T> createBySuccess() {
        return new BaseResponse<>(SystemConstant.HTTP_SUCCESS);
    }

    public static <T> BaseResponse<T> createBySuccess(T data) {
        BaseResponse<T> response = createBySuccess();
        response.setData(new ResponseData<>(data));
        return response;
    }

    public static <T> BaseResponse<T> createBySuccess(List<T> data) {
        BaseResponse<T> response = createBySuccess();
        response.setData(new ResponseData<>(data));
        return response;
    }

    public static <T> BaseResponse<T> createBySuccess(List<T> data, Page page) {
        BaseResponse<T> response = createBySuccess();
        response.setData(new ResponseData<>(data, page));
        return response;
    }

    public static <T> BaseResponse<T> createBySuccess(ErrorMessage errorMessage) {
        BaseResponse<T> response = createBySuccess();
        response.setMsg(errorMessage.getMessage());
        response.setData(new ResponseData<>(Collections.emptyList()));
        return response;
    }

    public static <T> BaseResponse<T> createByErrorMessage(ErrorMessage errorMessage) {
        return new BaseResponse<>(errorMessage.getWebCode(), errorMessage.getMessage());
    }
}
