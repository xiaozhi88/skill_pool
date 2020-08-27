package com.meitq.cifox.api.module.productmanagement.enums;

import com.meitq.cifox.common.exception.BaseException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品状态枚举
 *
 * @author zhangzy
 * @date 2020/8/20-10:56
 * @since v1.0
 */
public enum ProductStatusEnum {
    /***
     * 未提交
     */
    UNCOMMIT("0201", "未提交"),
    /**
     * 待审核
     */
    CHECK_PENDING("0202", "待审核"),
    /**
     * 待发布
     */
    PUBLISH_PENDING("0203", "待发布"),
    /**
     * 已发布
     */
    HAVE_PUBLISHED("0204", "已发布");

    ProductStatusEnum(String type, String name) {
        this.code = type;
        this.name = name;
    }

    /**
     * 根据code获取枚举
     *
     * @param code 状态码
     * @return 返回 com.meitq.cifox.common.enums.DataValueTypeEnum 描述此返回参数
     * @author zhangzy
     * @date 2020/8/20
     * @since v1.0
     */
    public static ProductStatusEnum findByCode(String code) {
        for (ProductStatusEnum productStatusEnum : ProductStatusEnum.values()) {
            if (productStatusEnum.getCode().equals(code)) {
                return productStatusEnum;
            }
        }
        throw new BaseException("数据类型错误");
    }

    /**
     * 获取所有的状态名
     *
     * @return 返回 java.util.List<java.lang.String> 描述此返回参数
     * @author zhangzy
     * @date 2020/8/20
     * @since v1.0
     */
    public static List<Map<String, String>> getAll() {
        ArrayList<Map<String, String>> list = new ArrayList<>();
        for (ProductStatusEnum productStatusEnum : ProductStatusEnum.values()) {
            HashMap<String, String> hs = new HashMap<>();
            hs.put("code", productStatusEnum.getCode());
            hs.put("name", productStatusEnum.getName());
            list.add(hs);
        }
        return list;
    }

    @Getter
    private String code;

    /**
     * 状态名
     */
    @Getter
    private String name;
}
