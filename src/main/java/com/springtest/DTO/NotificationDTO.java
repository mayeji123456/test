package com.springtest.DTO;

import com.springtest.data.Notification;
import com.springtest.data.User;

import lombok.Data;

@Data
public class NotificationDTO {
	private User Notifier;
	private Notification notification;
	private String title;
	private String description;
}
