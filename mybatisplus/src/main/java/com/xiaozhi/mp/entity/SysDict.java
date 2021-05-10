package com.xiaozhi.mp.entity;

/***************************************************/
  /* 
  Usage                : ${DESCRIPTION}
  Project name         : skill_pool
  Author               : ${USER_NAME}
  Mail                 : ${USER_NAME}@chinaexpressair.com
  Date                 : 2021-05-10 09:54
  Version              : 1.0
  Modification history :
      Date          Author          Version          Description
  ---------------------------------------------------------------
   2021-05-10       ${USER_NAME}             1.0             新建
   @Copyright(C)2008-2020: 华夏航空股份有限公司 All right Reserved.
  */
/**
    * 数据字典表
    */
public class SysDict {
    private Long id;

    /**
    * 字典名称
    */
    private String name;

    /**
    * 字典类型
    */
    private String type;

    /**
    * 字典码
    */
    private String code;

    /**
    * 字典值
    */
    private String value;

    /**
    * 排序
    */
    private Integer orderNum;

    /**
    * 备注
    */
    private String remark;

    /**
    * 删除标记  -1：已删除  0：正常
    */
    private Byte delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }
}