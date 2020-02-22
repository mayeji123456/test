package com.springtest.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springtest.DTO.ResultDTO;
import com.springtest.DTO.ThumbsupDTO;
import com.springtest.data.User;
import com.springtest.service.ArticleService;
import com.springtest.service.CommentService;

@Controller
public class ThumbsupController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private CommentService commentService;
	
	@ResponseBody
	@RequestMapping(value = "/thumbsup", method = RequestMethod.POST)
	public ResultDTO post(@RequestBody ThumbsupDTO thumbsupDTO,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return ResultDTO.errorOf("用户未登录");
		}
		if(thumbsupDTO.getType()==1) {
			articleService.incthumbsup(thumbsupDTO.getId());
		}else {
			commentService.incthumbsup(thumbsupDTO.getId());
		}
		return ResultDTO.okOf("点赞成功");
	}
	
}
