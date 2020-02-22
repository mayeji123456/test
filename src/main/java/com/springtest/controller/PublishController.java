package com.springtest.controller;


import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springtest.DTO.ArticleDTO;
import com.springtest.data.Article;
import com.springtest.data.User;
import com.springtest.exception.CustomizeErrorCode;
import com.springtest.exception.CustomizeException;
import com.springtest.service.ArticleService;


@Controller
public class PublishController {
	
	@Autowired
	private ArticleService articleservice;
	
	@GetMapping("/publish")
	public String publish(HttpServletRequest request) {
		User user=(User)request.getSession().getAttribute("user");
		if(user==null) {
			throw new CustomizeException(CustomizeErrorCode.USER_NOT_LOG_IN);
		}
		return "publish";
	}
	
	@GetMapping("/publish/{id}")
	public String edit(@PathVariable(name = "id")Long id,Model model) {
		ArticleDTO articledto=articleservice.getarticlebyid(id);
		model.addAttribute("title", articledto.getArticle().getTitle());
		model.addAttribute("tag", articledto.getArticle().getTag());
		model.addAttribute("text", articledto.getArticle().getText());
		model.addAttribute("id", articledto.getArticle().getId());
		return "publish";
	}
	
	@PostMapping("/publish")
	public String doPublish(@RequestParam("title")String title,@RequestParam("content")String content,
			@RequestParam("tag")String tag,HttpServletRequest request,Model model,
			@RequestParam(name = "id",required = false)Long id) {
		User user=(User)request.getSession().getAttribute("user");
		
		
		if(user==null) {
			throw new CustomizeException(CustomizeErrorCode.USER_NOT_LOG_IN);
		}
		Article a=new Article();
		a.setId(id);
		a.setTitle(title);
		a.setTag(tag);
		a.setText(content);
		a.setAuthorid(user.getId());
		articleservice.updatearticle(a);
	
		return "redirect:/";
	}
}
