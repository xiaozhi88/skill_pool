package com.xiaozhi.office.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义运行时异常
 *
 * @author 周恒
 * @date 20190812 09:53:27
 * @since v1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = -8458116729669426482L;

	private String message;

	private String state = "500";

	public BaseException() {
		super();
	}

	public BaseException(String message, Throwable cause,
                         boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.message = message;
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
		this.message = message;
	}

	public BaseException(String message) {
		super(message);
		this.message = message;
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String message, String status) {
		super(message);
		this.message = message;
		this.state = status;
	}

}
