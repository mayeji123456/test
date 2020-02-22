package com.springtest.DTO;

import com.springtest.exception.CustomizeException;

import lombok.Data;

@Data
public class ResultDTO<T> {
	private T data;
	private String message;
	
	public static ResultDTO errorOf(String message){
		ResultDTO resultDTO=new ResultDTO();
		resultDTO.setMessage(message);
		return resultDTO;
	}
	
	public static ResultDTO errorOf(CustomizeException e) {
		return errorOf(e.getMessage());
	}
	
	public static ResultDTO okOf(String message) {
		ResultDTO resultDTO=new ResultDTO();
		resultDTO.setMessage(message);
		return resultDTO;
	}
	
	public static <T> ResultDTO okOf(T t) {
		ResultDTO resultDTO=new ResultDTO();
		resultDTO.setMessage("success");
		resultDTO.setData(t);
		return resultDTO;
	}
}
