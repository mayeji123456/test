package com.springtest.DTO;

import lombok.Data;

@Data
public class CommentCreateDTO {
	private Long parentid;
	private String text;
	private Integer type;
}
