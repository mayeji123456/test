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

import com.springtest.DTO.ArticleDTO;
import com.springtest.DTO.GithubUserDTO;
import com.springtest.data.User;
import com.springtest.service.ArticleService;
import com.springtest.service.UserService;

@Controller
public class IndexController {
	@Autowired
	private UserService githubservice;
	@Autowired
	private ArticleService articleservice;
	
	@GetMapping("/")
	public String index(HttpServletRequest request,@RequestParam(value="page",defaultValue="1")String page,Model model) {
		int maxpage=articleservice.getmaxpage();
		
		List<ArticleDTO> a=articleservice.getarticlebypage(Integer.parseInt(page));
		model.addAttribute("maxpage", maxpage);
		model.addAttribute("articleDTO", a);
		model.addAttribute("page", Math.min(Integer.parseInt(page), maxpage));
		return "index";
	}
	
	@GetMapping("/callback")
	public String callback(@RequestParam(name="code") String code,@RequestParam(name="state") String state
			,HttpServletResponse response) {

		GithubUserDTO githubUserDTO=githubservice.getUser(code,state);
		
		//System.out.println(githubUser.getName());
		if(githubUserDTO!=null) {
			String mytoken=UUID.randomUUID().toString();
			response.addCookie(new Cookie("token", mytoken));
			User u=new User();
			u.setName(githubUserDTO.getName());
			u.setToken(mytoken);
			u.setAccountid(githubUserDTO.getId());
			githubservice.updateuser(u);
		}
		return "redirect:/";
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
