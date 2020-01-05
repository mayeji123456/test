package com.springtest.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springtest.model.AccessTokenDTO;
import com.springtest.model.GithubUser;
import com.springtest.model.User;
import com.springtest.repository.UserRepository;
import com.springtest.tool.GithubProvider;

import okhttp3.Request;

@Controller
public class AuthorizeController {
	
	@Autowired
	private GithubProvider githubProvider;
	
	@Autowired
	private UserRepository userRepository;
	
	@Value("${github.Client.id}")
	private String Client_id;
	@Value("${github.Client.secret}")
	private String Client_secret;
	@Value("${github.Redirect.uri}")
	private String Redirect_uri;
	@GetMapping("/callback")
	public String callback(@RequestParam(name="code") String code,@RequestParam(name="state") String state
			,HttpServletRequest request) {

		AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
		accessTokenDTO.setClient_id(Client_id);
		accessTokenDTO.setClient_secret(Client_secret);
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri(Redirect_uri);
		accessTokenDTO.setState(state);
		String accessToken = githubProvider.GetAccessToken(accessTokenDTO);
		GithubUser githubUser=githubProvider.getUser(accessToken);
		
		//System.out.println(githubUser.getName());
		if(githubUser!=null) {
			request.getSession().setAttribute("user", githubUser);
			User u=new User();
			u.setName(githubUser.getName());
			u.setAccount_id(String.valueOf(githubUser.getId()));
			u.setGmt_create(System.currentTimeMillis());
			u.setGmt_modified(u.getGmt_create());
			u.setToken(UUID.randomUUID().toString());
			userRepository.save(u);
			return "redirect:/";
		}else {
			return "redirect:/";
		}
		
	}
}
