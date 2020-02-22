package com.springtest.DTO;

import com.springtest.data.Article;
import com.springtest.data.User;

import lombok.Data;

@Data
public class ArticleDTO {
	private Article article;
	private User author;
}
