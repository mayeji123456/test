package com.springtest.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.springtest.data.Article;


public interface ArticleRepository extends CrudRepository<Article, Long>{
	public List<Article> findAllByOrderByGmtmodifiedDesc();
	
	public List<Article> findByAuthoridOrderByGmtmodifiedDesc(Long authorid);
	
	public List<Article> findByTagOrderByGmtmodified(String tag);
	
}
