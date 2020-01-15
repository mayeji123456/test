package com.springtest.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Article {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String title;
	private String text;
	private String tag;
	
	private String author;
	private Integer commentcount;
	private Integer viewcount;
	private Integer likecount;
	
	private Long gmtcreate;
	private Long gmtmodified;
	
}
