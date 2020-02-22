package com.springtest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.springtest.service.NotificationService;


@Controller
public class NotificationController {
	@Autowired
	private NotificationService notificationService;
	
	@GetMapping("/notification/{id}")
	public String notification(@PathVariable(name="id")Long id) {
		String target=notificationService.gettargetarticle(id);
		notificationService.removenotification(id);
		return target;
	}
	
}
