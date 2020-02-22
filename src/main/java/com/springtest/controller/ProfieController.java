package com.springtest.controller;

import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.springtest.DTO.ArticleDTO;
import com.springtest.DTO.NotificationDTO;
import com.springtest.data.User;
import com.springtest.service.ArticleService;
import com.springtest.service.NotificationService;


@Controller

public class ProfieController {
	@Autowired
	private ArticleService articleservice;
	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/profile/{action}")
	public String profile(@PathVariable(name="action") String action,
			Model model,
			HttpServletRequest request,
			@RequestParam(value="page",defaultValue="1")int page) {
		
		User user=(User)request.getSession().getAttribute("user");
		
		if(user==null) {
			return "redirect:/";
		}
		int notificationcount=notificationService.getnotificationcount(user.getId());
		model.addAttribute("notificationcount", notificationcount);
		if(action.equals("article")) {
			model.addAttribute("section", "article");
			model.addAttribute("title", "我的发布");
			
			int maxpage=articleservice.getmaxpage();
			model.addAttribute("maxpage", maxpage);
			List<ArticleDTO> a=articleservice.getarticlebypage(page,user.getId());
			model.addAttribute("articleDTO", a);
			model.addAttribute("page", Math.min(page, maxpage));
		}else if(action.equals("reply")) {
			model.addAttribute("section", "reply");
			model.addAttribute("title", "我的通知");
			
			int maxpage=notificationService.getmaxpage(user.getId());
			model.addAttribute("maxpage", maxpage);
			List<NotificationDTO> notificationDTOs=notificationService.getNotification(page, user.getId());
			model.addAttribute("notificationDTO", notificationDTOs);
			model.addAttribute("page", Math.min(page, maxpage));
		}
		return "profile";
	}
}
