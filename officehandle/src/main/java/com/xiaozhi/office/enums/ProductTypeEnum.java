package com.meitq.cifox.api.module.productmanagement.enums;

import com.meitq.cifox.common.exception.BaseException;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 产品类别枚举
 *
 * @author zhangzy
 * @date 2020/8/20-10:56
 * @since v1.0
 */
public enum ProductTypeEnum {
    /***
     * 高温日数
     */
    HIGH_TEMP_DAYS("0101", "高温日数"),
    /**
     * 气候监测
     */
    CLIMATE_MONITOR("0102", "气候监测"),
    /**
     * 气候月评价
     */
    MONTH_CLIMATE_EVALUATE("0103", "气候月评价"),
    /**
     * 气候季评价
     */
    QUARTER_CLIMATE_EVALUATE("0104", "气候季评价"),
    /**
     * 气候季评价
     */
    HEAVY_RAIN_PROCESS("0105", "暴雨过程总结")
    ;

    ProductTypeEnum(String type, String name) {
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
    public static ProductTypeEnum findByCode(String code) {
        for (ProductTypeEnum productTypeEnum : ProductTypeEnum.values()) {
            if (productTypeEnum.getCode().equals(code)) {
                return productTypeEnum;
            }
        }
        throw new BaseException("数据类型错误");
    }

    /**
     * 获取所有的状态
     *
     * @return 返回 java.util.List<java.lang.String> 描述此返回参数
     * @author zhangzy
     * @date 2020/8/20
     * @since v1.0
     */
    public static List<Map<String, String>> getAll() {
        ArrayList<Map<String, String>> list = new ArrayList<>();
        for (ProductTypeEnum productTypeEnum : ProductTypeEnum.values()) {
            HashMap<String, String> hs = new HashMap<>();
            hs.put("code", productTypeEnum.getCode());
            hs.put("name", productTypeEnum.getName());
            list.add(hs);
        }
        return list;
    }

    @Getter
    private String code;

    /**
     * 类别名
     */
    @Getter
    private String name;
}
