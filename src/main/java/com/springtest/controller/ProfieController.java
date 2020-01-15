package com.springtest.controller;

import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.springtest.model.Article;
import com.springtest.model.User;
import com.springtest.service.ArticleService;


@Controller

public class ProfieController {
	@Autowired
	private ArticleService articleservice;
	
	
	@GetMapping("/profile/{action}")
	public String profile(@PathVariable(name="action") String action,
			Model model,
			HttpServletRequest request,
			@RequestParam(value="page",defaultValue="1")String page) {
		
		User user=(User)request.getSession().getAttribute("user");
		
		if(user==null) {
			return "redirect:/";
		}
		
		if(action.equals("article")) {
			model.addAttribute("section", "article");
			model.addAttribute("title", "我的发布");
			
			int maxpage=articleservice.getmaxpage();
			model.addAttribute("maxpage", maxpage);
			List<Article> a=articleservice.getarticlebyuser(Integer.parseInt(page),user);
			model.addAttribute("article", a);
			model.addAttribute("page", Math.min(Integer.parseInt(page), maxpage));
		}else if(action.equals("reply")) {
			model.addAttribute("section", "reply");
			model.addAttribute("title", "我的回复");
		}
		return "profile";
	}
}
