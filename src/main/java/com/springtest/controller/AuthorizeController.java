package com.springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springtest.tool.AccessTokenDTO;
import com.springtest.tool.GithubProvider;
import com.springtest.tool.GithubUser;
import com.springtest.tool.HttpRequest;

@Controller
public class AuthorizeController {
	
	@Autowired
	private GithubProvider githubProvider;
	
	@Value("${github.Client_id}")
	private String Client_id;
	@Value("${github.Client_secret}")
	private String Client_secret;
	@Value("${github.Redirect_uri}")
	private String Redirect_uri;
	@GetMapping("/callback")
	public String callback(@RequestParam(name="code") String code,@RequestParam(name="state") String state) {
//		String sr=HttpRequest.sendPost("https://github.com/login/oauth/access_token", 
//				"client_id=16153c947bee8283a03e&client_secret=2c6b8ca6c80d0942a29691049f32109b7918cd59"
//				+"&code="+code+"&redirect_uri=localhot:9999/callback&state="+state);
		AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
		accessTokenDTO.setClient_id(Client_id);
		accessTokenDTO.setClient_secret(Client_secret);
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri(Redirect_uri);
		accessTokenDTO.setState(state);
		String accessToken = githubProvider.GetAccessToken(accessTokenDTO);
		GithubUser githubUser=githubProvider.getUser(accessToken);
		System.out.println(githubUser.getName());
		return "index";
	}
}
