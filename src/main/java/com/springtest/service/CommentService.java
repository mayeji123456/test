package com.springtest.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springtest.DTO.CommentDTO;
import com.springtest.data.Article;
import com.springtest.data.Comment;
import com.springtest.data.User;
import com.springtest.exception.CustomizeErrorCode;
import com.springtest.exception.CustomizeException;
import com.springtest.repository.ArticleRepository;
import com.springtest.repository.CommentRepository;
import com.springtest.repository.UserRepository;

@Service
public class CommentService {
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private UserRepository userRepository;
	
	public void save(Comment comment) {
		if(comment.getId()==null) {
			comment.setLikecount(0);
			comment.setCommentcount(0);
			comment.setGmtcreate(System.currentTimeMillis());
			comment.setGmtmodified(comment.getGmtcreate());
			commentRepository.save(comment);
		}else {
			Comment oldcomment=commentRepository.findById(comment.getId()).orElse(null);
			if(oldcomment==null) {
				comment.setLikecount(0);
				comment.setCommentcount(0);
				comment.setGmtcreate(System.currentTimeMillis());
				comment.setGmtmodified(comment.getGmtcreate());
				commentRepository.save(comment);
			}else {
				comment.setLikecount(oldcomment.getLikecount());
				comment.setCommentcount(oldcomment.getCommentcount());
				comment.setGmtcreate(oldcomment.getGmtcreate());
				comment.setGmtmodified(System.currentTimeMillis());
				commentRepository.save(comment);
			}
		}
	}
	
	public synchronized void inccomment(Long id,int type) {
		if(type==1) {
			Article article=articleRepository.findById(id).orElse(null);
			if(article==null) {
				throw new CustomizeException(CustomizeErrorCode.ARTICLE_NOT_FOUND);
			}
			article.setCommentcount(article.getCommentcount()+1);
			articleRepository.save(article);
		}else if(type==2) {
			Comment comment=commentRepository.findById(id).orElse(null);
			if(comment==null) {
				throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
			}
			
			comment.setCommentcount(comment.getCommentcount()+1);
			commentRepository.save(comment);
		}
		
	}
	
	public synchronized void incthumbsup(Long id) {
		Comment comment=commentRepository.findById(id).orElse(null);
		if(comment!=null) {
			comment.setLikecount(comment.getLikecount()+1);
			commentRepository.save(comment);
		}
	}
	
	public List<CommentDTO> findcommentbyid(Long id,int type) {
		List<Comment> comments=commentRepository.findByParentidAndTypeOrderByGmtcreateAsc(id,type);
		List<CommentDTO> result=new ArrayList<CommentDTO>();
		for (int i = 0; i < comments.size(); i++) {
			User user=userRepository.findById(comments.get(i).getCommentatorid()).orElse(null);
			CommentDTO commentDTO=new CommentDTO();
			commentDTO.setComment(comments.get(i));
			commentDTO.setCommentator(user);
			result.add(commentDTO);
		}
		return result;
	}
}
