package com.springtest.DTO;

import com.springtest.data.Comment;
import com.springtest.data.User;

import lombok.Data;

@Data
public class CommentDTO {
	private Comment comment;
	private User commentator;
}
