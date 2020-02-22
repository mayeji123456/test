package com.springtest.exception;

public enum CustomizeErrorCode implements ErrorCode{
	NOTIFICATION_NOT_FOUND("通知未找到"),
	
	ARTICLE_NOT_FOUND("文章未找到。"),
	
	USER_NOT_LOG_IN("用户未登录"),
	
	COMMENT_NOT_FOUND("评论未找到");
	
	private String message;
	
	private CustomizeErrorCode(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
