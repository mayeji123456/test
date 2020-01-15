package com.springtest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.springtest.model.Article;
import com.springtest.model.User;

public interface ArticleRepository extends CrudRepository<Article, Integer>{
	List findAllByOrderByGmtmodified();
	
	List findByAuthorEquals(String author);
	
	Optional<Article> findById(Integer id);
}
