package com.springtest.controller;


import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springtest.model.Article;
import com.springtest.model.User;
import com.springtest.repository.ArticleRepository;
import com.springtest.service.ArticleService;


@Controller
public class PublishController {
	
	@Autowired
	private ArticleService articleservice;
	
	@GetMapping("/publish")
	public String publish() {
		return "publish";
	}
	
	@GetMapping("/publish/{id}")
	public String edit(@PathVariable(name = "id")Integer id,Model model) {
		Article article=articleservice.findarticlebyid(id);
		model.addAttribute("title", article.getTitle());
		model.addAttribute("tag", article.getTag());
		model.addAttribute("text", article.getText());
		model.addAttribute("id", article.getId());
		return "publish";
	}
	
	@PostMapping("/publish")
	public String doPublish(@RequestParam("title")String title,@RequestParam("content")String content,
			@RequestParam("tag")String tag,HttpServletRequest request,Model model,
			@RequestParam(name = "id",required = false)Integer id) {
		User user=(User)request.getSession().getAttribute("user");
		
		
		if(user==null) {
			model.addAttribute("error","用户未登录");
			return "publish";
		}
		Article a=new Article();
		a.setId(id);
		a.setTitle(title);
		a.setTag(tag);
		a.setText(content);
		a.setAuthor(user.getName());
		a.setCommentcount(0);
		a.setLikecount(0);
		a.setViewcount(0);
		articleservice.updatearticle(a);
	
		return "redirect:/";
	}
}
