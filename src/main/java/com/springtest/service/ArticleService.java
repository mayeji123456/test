package com.springtest.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springtest.DTO.ArticleDTO;
import com.springtest.data.Article;
import com.springtest.data.User;
import com.springtest.exception.CustomizeErrorCode;
import com.springtest.exception.CustomizeException;
import com.springtest.repository.ArticleRepository;
import com.springtest.repository.UserRepository;

@Service
public class ArticleService {
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private UserRepository userRepository;
	
	
	public void updatearticle(Article article) {
		if(article.getId()==null) {
			article.setCommentcount(0);
			article.setLikecount(0);
			article.setViewcount(0);
			article.setGmtcreate(System.currentTimeMillis());
			article.setGmtmodified(article.getGmtcreate());
			articleRepository.save(article);
		}else {
			Article oldarticle=articleRepository.findById(article.getId()).orElse(null);
			if(oldarticle==null) {
				article.setCommentcount(0);
				article.setLikecount(0);
				article.setViewcount(0);
				article.setGmtcreate(System.currentTimeMillis());
				article.setGmtmodified(article.getGmtcreate());
				articleRepository.save(article);
			}else {
				article.setCommentcount(oldarticle.getCommentcount());
				article.setLikecount(oldarticle.getLikecount());
				article.setViewcount(oldarticle.getViewcount());
				article.setGmtcreate(oldarticle.getGmtcreate());
				article.setGmtmodified(System.currentTimeMillis());
				articleRepository.save(article);
			}
			
		}
		
	}
	public int getmaxpage() {
		int articlecount=articleRepository.findAllByOrderByGmtmodifiedDesc().size();
		int maxpage=(articlecount-1)/5+1;
		if(maxpage==0)maxpage++;
		return maxpage;
	}
	
	public List<ArticleDTO> getarticlebypage(int page){
		List<Article> articles=articleRepository.findAllByOrderByGmtmodifiedDesc();
		return getArticleDTObypage(page, articles);
	}
	
	public List<ArticleDTO> getarticlebypage(int page,Long autherid){
		List<Article> articles=articleRepository.findByAuthoridOrderByGmtmodifiedDesc(autherid);
		return getArticleDTObypage(page, articles);
	}
	
	private List<ArticleDTO> getArticleDTObypage(int page,List<Article> articles){
		int articlecount=articles.size();
		int maxpage=(articlecount-1)/5+1;
		if(maxpage==0)maxpage++;
		
		page=Math.max(1, page);
		List<Article> a=new ArrayList<>();
		
		if(page>=maxpage) {		
			for(int i=(maxpage-1)*5;i<articles.size();i++) {
				a.add(articles.get(i));
			}
		}else {
			for(int i=(page-1)*5;i<page*5;i++) {
				a.add(articles.get(i));
			}
		}
		
		List<ArticleDTO> articleDTOs=new ArrayList<>();
		for (int i = 0; i < a.size(); i++) {
			ArticleDTO articleDTO=new ArticleDTO();
			articleDTO.setArticle(a.get(i));
			articleDTO.setAuthor(userRepository.findById(a.get(i).getAuthorid()).orElse(null));
			articleDTOs.add(articleDTO);
		}
		
		return articleDTOs;
	}
	
	public ArticleDTO getarticlebyid(Long id) {
		Article article=articleRepository.findById(id).orElse(null);
		if(article==null) {
			throw new CustomizeException(CustomizeErrorCode.ARTICLE_NOT_FOUND);
		}
		User author=userRepository.findById(article.getAuthorid()).orElse(null);
		ArticleDTO articleDTO=new ArticleDTO();
		articleDTO.setArticle(article);
		articleDTO.setAuthor(author);
		return articleDTO;
	}
	
	
	
	public List<Article> getrelatedarticle(Long id){
		Article article=articleRepository.findById(id).orElse(null);
		if(article==null) {
			throw new CustomizeException(CustomizeErrorCode.ARTICLE_NOT_FOUND);
		}
		List<Article> relatedArticles=articleRepository.findByTagOrderByGmtmodified(article.getTag());
		for(Article a:relatedArticles) {
			if(a.getId()==article.getId()) {
				relatedArticles.remove(a);
				break;
			}
		}
		return relatedArticles;
	}
	
	public synchronized void incview(Long id) {
		Article article=articleRepository.findById(id).orElse(null);
		if(article!=null) {
			article.setViewcount(article.getViewcount()+1);
			articleRepository.save(article);
		}
	}
	public synchronized void incthumbsup(Long id) {
		Article article=articleRepository.findById(id).orElse(null);
		if(article!=null) {
			article.setLikecount(article.getLikecount()+1);
			articleRepository.save(article);
		}
	}
}
