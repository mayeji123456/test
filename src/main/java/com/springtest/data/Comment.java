package com.springtest.data;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



import lombok.Data;
@Entity
@Data
public class Comment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Long parentid;
	private Integer type;

	private Integer likecount;
	private Integer commentcount;
	private String text;	
	private Long commentatorid;
	
	private Long gmtcreate;
	private Long gmtmodified;

}
