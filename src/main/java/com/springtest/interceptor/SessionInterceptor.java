package com.springtest.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.springtest.model.User;

import com.springtest.service.GithubService;

@Service
public class SessionInterceptor implements HandlerInterceptor {
	
	@Autowired
	private GithubService githubservice;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
			Cookie[] cookies=request.getCookies();
			User user=null;
			if(cookies!=null) {
				for (int i = 0; i < cookies.length; i++) {
					if(cookies[i].getName().equals("token")) {
						String token=cookies[i].getValue();
						user=githubservice.selectuserbytoken(token);
						if(user!=null) {
							request.getSession().setAttribute("user", user);
						}
						break;
					}
				}
			}
		return true;
	}
	

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
