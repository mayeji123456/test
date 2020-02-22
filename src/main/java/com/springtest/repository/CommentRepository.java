package com.springtest.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.springtest.data.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long>{
	List<Comment> findByParentidAndTypeOrderByGmtcreateAsc(Long commentatorid,int type);
}
