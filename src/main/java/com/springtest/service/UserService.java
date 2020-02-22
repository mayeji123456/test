package com.springtest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.springtest.DTO.AccessTokenDTO;
import com.springtest.DTO.GithubUserDTO;
import com.springtest.data.User;
import com.springtest.repository.UserRepository;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class UserService {
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
	
	public User finduserbyid(Long id) {
		return userRepository.findById(id).orElse(null);
	}
	public void updateuser(User user) {
		List<User> users=userRepository.findByAccountid(user.getAccountid());
		if(users==null||users.size()==0) {
			user.setGmtcreate(System.currentTimeMillis());
			user.setGmtmodified(user.getGmtcreate());
			userRepository.save(user);
		}else {
			user.setId(users.get(0).getId());
			user.setGmtcreate(users.get(0).getGmtcreate());
			user.setGmtmodified(System.currentTimeMillis());
			userRepository.save(user);
		}
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
	
	public GithubUserDTO getUser(String code,String state) {
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
			GithubUserDTO githubUserDTO=JSON.parseObject(result,GithubUserDTO.class);
			return githubUserDTO;
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
