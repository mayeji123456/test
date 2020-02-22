package com.springtest.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.springtest.DTO.CommentCreateDTO;
import com.springtest.DTO.CommentDTO;
import com.springtest.DTO.ResultDTO;
import com.springtest.data.Comment;
import com.springtest.data.User;
import com.springtest.service.CommentService;
import com.springtest.service.NotificationService;

@Controller
public class CommentController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private NotificationService notificationService;
	@ResponseBody
	@RequestMapping(value = "/comment", method = RequestMethod.POST)
	public ResultDTO post(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");

		if (user == null) {
			return ResultDTO.errorOf("用户未登录");
		}
		
		if(commentCreateDTO.getText().equals("")) {
			return ResultDTO.errorOf("评论内容为空");
		}
		Comment comment = new Comment();
		comment.setParentid(commentCreateDTO.getParentid());
		comment.setText(commentCreateDTO.getText());
		comment.setType(commentCreateDTO.getType());
		comment.setCommentatorid(user.getId());
		commentService.save(comment);
		commentService.inccomment(comment.getParentid(), comment.getType());
		notificationService.notified(comment);
		
		return ResultDTO.okOf("评论成功");
	}
	
	@ResponseBody
	@RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
	public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id")Long id) {
		List<CommentDTO> comments=commentService.findcommentbyid(id, 2);
		return ResultDTO.okOf(comments);
	}
}
