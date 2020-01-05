package com.springtest.tool;


import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.springtest.model.AccessTokenDTO;
import com.springtest.model.GithubUser;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class GithubProvider {
	public String GetAccessToken(AccessTokenDTO accessTokenDTO) {
		MediaType mediaType = MediaType.get("application/json; charset=utf-8");

		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO),mediaType);
		String url="https://github.com/login/oauth/access_token";
		Request request = new Request.Builder()
				.url(url)
				.post(body)
				.build();
		try (Response response = client.newCall(request).execute()) {
			String result=response.body().string();
			String token=result.split("&")[0].split("=")[1];
			return token;	
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	public GithubUser getUser(String accesstoken) {
		OkHttpClient client = new OkHttpClient();
		String url="https://api.github.com/user?access_token="+accesstoken;
		Request request = new Request.Builder()
		    .url(url)
		    .build();

		try (Response response = client.newCall(request).execute()) {
			String result=response.body().string();
			GithubUser githubUser=JSON.parseObject(result,GithubUser.class);
			return githubUser;
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
