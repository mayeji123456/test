package com.springtest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springtest.DTO.NotificationDTO;
import com.springtest.data.Comment;
import com.springtest.data.Notification;
import com.springtest.exception.CustomizeErrorCode;
import com.springtest.exception.CustomizeException;
import com.springtest.repository.ArticleRepository;
import com.springtest.repository.CommentRepository;
import com.springtest.repository.NotifiedRepository;
import com.springtest.repository.UserRepository;

@Service
public class NotificationService {
	@Autowired
	private NotifiedRepository notifiedRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private ArticleRepository articleRepository;
	
	public void notified(Comment comment) {
		Notification notification=new Notification();
		notification.setNotifierid(comment.getCommentatorid());
		notification.setGmtcreate(System.currentTimeMillis());
		notification.setOuterid(comment.getParentid());
		if(comment.getType()==1) {
			notification.setType(1);
			notification.setReceiverid(articleRepository.findById(comment.getParentid()).orElse(null).getAuthorid());	
		}else{
			notification.setType(3);
			notification.setReceiverid(commentRepository.findById(comment.getParentid()).orElse(null).getCommentatorid());
		}
		notifiedRepository.save(notification);
	}
	
	public List<NotificationDTO> getNotification(int page,Long receiverid){
		List<Notification> notifications=notifiedRepository.findByReceiveridOrderByGmtcreate(receiverid);
		int maxpage=(notifications.size()-1)/5+1;
		if(maxpage==0)maxpage++;
		page=Math.max(1, page);
		List<NotificationDTO> notificationDTOs=new ArrayList<>();
		List<Notification> tempList=new ArrayList<Notification>();
		
		if(page>=maxpage) {		
			for(int i=(maxpage-1)*10;i<notifications.size();i++) {
				tempList.add(notifications.get(i));
			}
		}else {
			for(int i=(page-1)*10;i<page*10;i++) {
				tempList.add(notifications.get(i));
			}
		}

		for (int i = 0; i < tempList.size(); i++) {
			NotificationDTO notificationDTO=new NotificationDTO();
			Notification n=tempList.get(i);
			notificationDTO.setNotification(n);
			notificationDTO.setNotifier(userRepository.findById(n.getNotifierid()).orElse(null));
			if(n.getType()==1) {
				notificationDTO.setTitle(articleRepository.findById(n.getOuterid()).orElse(null).getTitle());
				notificationDTO.setDescription("回复了你的文章");
			}else if(n.getType()==2){
				notificationDTO.setTitle(commentRepository.findById(n.getOuterid()).orElse(null).getText());
				notificationDTO.setDescription("点赞了你的文章");
			}else if(n.getType()==3) {
				notificationDTO.setTitle(commentRepository.findById(n.getOuterid()).orElse(null).getText());
				notificationDTO.setDescription("回复了你的评论");
			}else if(n.getType()==4) {
				notificationDTO.setTitle(commentRepository.findById(n.getOuterid()).orElse(null).getText());
				notificationDTO.setDescription("点赞了你的评论");
			}
			notificationDTOs.add(notificationDTO);
		}
		
		return notificationDTOs;
	}
	
	public int getmaxpage(Long receiverid) {
		int notificationcount=notifiedRepository.findByReceiveridOrderByGmtcreate(receiverid).size();
		int maxpage=(notificationcount-1)/10+1;
		if(maxpage==0)maxpage++;
		return maxpage;
	}
	
	public int getnotificationcount(Long receiverid) {
		return notifiedRepository.findByReceiveridOrderByGmtcreate(receiverid).size();
	}
	
	public String gettargetarticle(Long notificationid) {
		Notification notification=notifiedRepository.findById(notificationid).orElse(null);
		if(notification==null) {
			throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
		}
		String targetarticle="redirect:/article/";
		if(notification.getType()==1||notification.getType()==2) {
			targetarticle+=String.valueOf(notification.getOuterid());
		}else if(notification.getType()==3||notification.getType()==4) {
			Long articleid=commentRepository.findById(notification.getOuterid()).orElse(null).getParentid();
			targetarticle+=String.valueOf(articleid);
		}
		return targetarticle;
	}
	
	public void removenotification(Long notificationid) {
		notifiedRepository.deleteById(notificationid);
	}
}
