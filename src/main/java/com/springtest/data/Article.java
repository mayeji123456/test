package com.springtest.data;


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
	private Long id;
	
	private String title;
	private String text;
	private String tag;
	
	private Long authorid;
	

	private Integer commentcount;

	private Integer viewcount;

	private Integer likecount;
	
	private Long gmtcreate;
	private Long gmtmodified;
	
}
