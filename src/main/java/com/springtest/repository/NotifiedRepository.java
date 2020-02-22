package com.springtest.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.springtest.data.Notification;

public interface NotifiedRepository extends CrudRepository<Notification, Long>{
	List<Notification> findByReceiveridOrderByGmtcreate(Long receiverid);
}
