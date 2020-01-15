package com.springtest.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springtest.model.AccessTokenDTO;
import com.springtest.model.GithubUser;
import com.springtest.model.User;
import com.springtest.repository.UserRepository;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class GithubService {
	@Autowired
	private UserRepository userRepository;
	
	@Value("${github.Client.id}")
	private String Client_id;
	@Value("${github.Client.secret}")
	private String Client_secret;
	@Value("${github.Redirect.uri}")
	private String Redirect_uri;
	
	public User selectuserbytoken(String token) {
		List<User> list=userRepository.findByToken(token);
		if(list==null||list.size()==0)return null;
		return list.get(0);
	}
	
	public void saveuser(User user) {
		userRepository.save(user);
	}
	

	private String GetAccessToken(AccessTokenDTO accessTokenDTO) {
		MediaType mediaType = MediaType.get("application/json; charset=utf-8");

		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
		String url="https://github.com/login/oauth/access_token";
		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.build();
		try (Response response = client.newCall(request).execute()) {
			String result=response.body().string();
			
			String token=result.split("&")[0].split("=")[1];
			//System.out.println(token);
			return token;	
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public GithubUser getUser(String code,String state) {
		AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
		accessTokenDTO.setClient_id(Client_id);
		accessTokenDTO.setClient_secret(Client_secret);
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri(Redirect_uri);
		accessTokenDTO.setState(state);
		String accessToken = GetAccessToken(accessTokenDTO);
		
		OkHttpClient client = new OkHttpClient();
		String url="https://api.github.com/user?access_token="+accessToken;
		Request request = new Request.Builder()
		    .url(url)
		    .build();

		try (Response response = client.newCall(request).execute()) {
			String result=response.body().string();
			//System.out.println(result);
			GithubUser githubUser=JSON.parseObject(result,GithubUser.class);
			return githubUser;
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
