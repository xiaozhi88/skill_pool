package com.xiaozhi.office.common;


/**
 * 错误信息枚举:
 * 1.记录业务的错误代码
 * 2.记录返回给前端的状态代码
 * 3.记录业务错误信息
 *
 * @author LeoRmAo
 * @date 20200113 17:00:09
 * @since v1.0
 */
public enum ErrorMessage {

    /**
     * 错误枚举
     */
    INTERNAL_ERROR("50001", SystemConstant.HTTP_COMMON_ERROR, "系统内部异常，请联系管理员解决"),
    SEND_PARAM_ERROR("50002", SystemConstant.HTTP_COMMON_ERROR, "请求参数有误"),
    NO_DATA_ERROR("50003", SystemConstant.HTTP_COMMON_ERROR, "暂无数据"),
    CALL_NCL_ERROR("50004", SystemConstant.HTTP_COMMON_ERROR, "调用NCL失败"),
    GET_USERINFO_ERROR("50005", SystemConstant.HTTP_COMMON_ERROR, "获取用户信息失败"),
    USER_LOGIN_ERROR("50006", SystemConstant.HTTP_COMMON_ERROR, "登陆失败"),
    USER_LOGOUT_ERROR("50007", SystemConstant.HTTP_COMMON_ERROR, "退出发生错误"),
    CALL_PYTHON_ERROR("50008", SystemConstant.HTTP_COMMON_ERROR, "调用python失败"),
    AREA_STATION_ID_TOO_MORE_ERROR("50009", SystemConstant.HTTP_COMMON_ERROR, "区域站点个数超过上限"),
    CALL_ALGORITHM_ERROR("50010", SystemConstant.HTTP_COMMON_ERROR, "调用算法失败"),
    AUTUMN_RAIN_TIME_ERROR("50011", SystemConstant.HTTP_COMMON_ERROR, "预报时段不在华西秋雨时段内"),
    LDFAI_TIME_ERROR("50012", SystemConstant.HTTP_COMMON_ERROR, "预报时段不在旱涝急转时段内"),
    NOT_KNOW_TIME_SCALE("50013", SystemConstant.HTTP_COMMON_ERROR, "未知的时间尺度"),
    DOWNLOAD_FILE_ERROR("50014", SystemConstant.HTTP_COMMON_ERROR, "下载文件失败"),
    SATURATING_RAIN_TIME_ERROR("50015", SystemConstant.HTTP_COMMON_ERROR, "预报时段不在透雨时段内"),
    FLOOD_RAINY_SEASON_TIME_ERROR("50016", SystemConstant.HTTP_COMMON_ERROR, "预报时段不在汛雨时段内"),
    SUMMER_DROUGHT_TIME_ERROR("50017", SystemConstant.HTTP_COMMON_ERROR, "预报时段不在伏旱时段内"),
    ERROR_USERNAME_OR_PASSWORD("50018", SystemConstant.HTTP_COMMON_ERROR, "用户名或密码错误"),
    ACCOUNT_DISABLED("50019", SystemConstant.HTTP_COMMON_ERROR, "当前账户已被禁用"),
    EMPTY_USERNAME_OR_PASSWORD("50020", SystemConstant.HTTP_COMMON_ERROR, "用户名或密码不能为空"),
    NO_CIMISS_DATA_ERROR("50021", SystemConstant.HTTP_COMMON_ERROR, "暂无实况数据"),
    THE_TWO_PASSWORDS_ARE_INCONSISTENT("50022", SystemConstant.HTTP_COMMON_ERROR, "两次输入密码不一致"),
    ORIGINAL_PASSWORD_ERROR("50023", SystemConstant.HTTP_COMMON_ERROR, "原密码错误"),
    UPLOAD_FILE_ERROR("50024", SystemConstant.HTTP_COMMON_ERROR, "上传文件失败"),
    FIND_FILE_ERROR("50025", SystemConstant.HTTP_COMMON_ERROR, "文件查询失败"),
    EXIST_FILE_ERROR("50026", SystemConstant.HTTP_COMMON_ERROR, "判断文件是否存在失败"),
    ;

    /**
     * serviceCode 业务错误码
     */
    private String serviceCode;

    /**
     * webCode 前端接收错误码
     */
    private String webCode;

    /**
     * message 错误信息
     */
    private String message;

    ErrorMessage(String serviceCode, String webCode, String message) {
        this.serviceCode = serviceCode;
        this.webCode = webCode;
        this.message = message;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getWebCode() {
        return webCode;
    }

    public void setWebCode(String webCode) {
        this.webCode = webCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取 error message by code.
     *
     * @param serviceCode 描述此参数
     * @return 返回 error message by code
     * @author SunMing
     * @date 20200413
     * @since v1.0
     */
    public static com.meitq.cifox.common.constant.ErrorMessage getErrorMessageByCode(String serviceCode) {
        for (com.meitq.cifox.common.constant.ErrorMessage errorMessage : com.meitq.cifox.common.constant.ErrorMessage.values()) {
            if (serviceCode.equals(errorMessage.getServiceCode())) {
                return errorMessage;
            }
        }
        return null;
    }
}
