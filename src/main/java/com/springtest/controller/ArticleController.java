package com.springtest.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.springtest.DTO.ArticleDTO;
import com.springtest.DTO.CommentDTO;
import com.springtest.data.Article;
import com.springtest.service.ArticleService;
import com.springtest.service.CommentService;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleservice;
	@Autowired 
	private CommentService commentService;
	
	@GetMapping("/article/{id}")
	public String article(@PathVariable(name = "id")Long id,Model model
			,HttpServletRequest request) {
		ArticleDTO articledto=articleservice.getarticlebyid(id);
		model.addAttribute("articleDTO", articledto);
		articleservice.incview(id);
		
		List<CommentDTO> commentDTOs=commentService.findcommentbyid(id,1);
		model.addAttribute("commentDTO", commentDTOs);
		
		List<Article> relatedArticles=articleservice.getrelatedarticle(id);
		model.addAttribute("relatedarticles", relatedArticles);
		return "article";
	}
}
