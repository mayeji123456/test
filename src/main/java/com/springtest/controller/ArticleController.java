package com.springtest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.springtest.model.Article;
import com.springtest.model.User;
import com.springtest.service.ArticleService;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleservice;
	
	@GetMapping("/article/{id}")
	public String article(@PathVariable(name = "id")Integer id,Model model
			,HttpServletRequest request) {
		Article article=articleservice.findarticlebyid(id);
		if(article==null) {
			return "index";
		}
		model.addAttribute("article", article);
	
		
		return "article";
	}
}
