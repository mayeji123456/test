package com.springtest.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springtest.model.Article;
import com.springtest.model.User;
import com.springtest.repository.ArticleRepository;
import com.springtest.repository.UserRepository;

@Service
public class ArticleService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ArticleRepository articleRepository;
	
	public List<Article> findallarticle(){
		return articleRepository.findAllByOrderByGmtmodified();
	}
	
	public void savearticle(Article article) {
		articleRepository.save(article);
	}
	
	public void updatearticle(Article article) {
		if(article.getId()==null) {
			article.setGmtcreate(System.currentTimeMillis());
			article.setGmtmodified(article.getGmtcreate());
			savearticle(article);
		}else {
			Article oldarticle=findarticlebyid(article.getId());
			if(oldarticle==null) {
				article.setGmtcreate(System.currentTimeMillis());
				article.setGmtmodified(article.getGmtcreate());
				savearticle(article);
			}else {
				articleRepository.delete(oldarticle);
				article.setGmtcreate(oldarticle.getGmtcreate());
				article.setGmtmodified(System.currentTimeMillis());
				savearticle(article);
			}
			
		}
		
	}
	public int getmaxpage() {
		int articlecount=articleRepository.findAllByOrderByGmtmodified().size();
		int maxpage=(articlecount-1)/5+1;
		if(maxpage==0)maxpage++;
		return maxpage;
	}
	
	public List<Article> getarticlebypage(int page){
		List<Article> articles=findallarticle();
		int maxpage=getmaxpage();
		page=Math.max(1, page);
		List<Article> result=new ArrayList<>();
		
		if(page>=maxpage) {		
			for(int i=(maxpage-1)*5;i<articles.size();i++) {
				result.add(articles.get(i));
			}
		}else {
			for(int i=(page-1)*5;i<page*5;i++) {
				result.add(articles.get(i));
			}
		}
		return result;
	}
	
	public List<Article> getarticlebyuser(int page,User user){
		List<Article> articles=articleRepository.findByAuthorEquals(user.getName());
		
		int articlecount=articles.size();
		int maxpage=(articlecount-1)/5+1;
		if(maxpage==0)maxpage++;
		
		page=Math.max(1, page);
		List<Article> result=new ArrayList<>();
		
		if(page>=maxpage) {		
			for(int i=(maxpage-1)*5;i<articles.size();i++) {
				result.add(articles.get(i));
			}
		}else {
			for(int i=(page-1)*5;i<page*5;i++) {
				result.add(articles.get(i));
			}
		}
		return result;
	}
	
	public Article findarticlebyid(int id) {
		return articleRepository.findById(id).orElse(null);
	}
}
