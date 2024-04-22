package com.instagram.clone.ServiceImpl;

import com.instagram.clone.Repository.NotificationRepository;
import com.instagram.clone.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
}
