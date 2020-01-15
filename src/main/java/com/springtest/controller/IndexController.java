package com.springtest.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springtest.model.Article;
import com.springtest.model.GithubUser;
import com.springtest.model.User;

import com.springtest.service.ArticleService;
import com.springtest.service.GithubService;

@Controller
public class IndexController {
	@Autowired
	private GithubService githubservice;
	@Autowired
	private ArticleService articleservice;
	
	@GetMapping("/")
	public String index(HttpServletRequest request,@RequestParam(value="page",defaultValue="1")String page,Model model) {
		int maxpage=articleservice.getmaxpage();
		model.addAttribute("maxpage", maxpage);
		List<Article> a=articleservice.getarticlebypage(Integer.parseInt(page));
		model.addAttribute("article", a);
		model.addAttribute("page", Math.min(Integer.parseInt(page), maxpage));
		return "index";
	}
	
	@GetMapping("/callback")
	public String callback(@RequestParam(name="code") String code,@RequestParam(name="state") String state
			,HttpServletResponse response) {

		GithubUser githubUser=githubservice.getUser(code,state);
		
		//System.out.println(githubUser.getName());
		if(githubUser!=null) {
			//request.getSession().setAttribute("user", githubUser);
			String mytoken=UUID.randomUUID().toString();
			response.addCookie(new Cookie("token", mytoken));
			User u=new User();
			u.setName(githubUser.getName());
			u.setGmtcreate(System.currentTimeMillis());
			u.setGmtmodified(u.getGmtcreate());
			u.setToken(mytoken);
			githubservice.saveuser(u);
			return "redirect:/";
		}else {
			return "redirect:/";
		}
		
	}
	
	@GetMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		request.removeAttribute("user");
		Cookie cookie1=new Cookie("token",null);
		cookie1.setMaxAge(0);
		response.addCookie(cookie1);
		
		Cookie cookie2=new Cookie("JSESSIONID",null);
		cookie2.setMaxAge(0);
		response.addCookie(cookie2);
		return "redirect:/";
	}
}
