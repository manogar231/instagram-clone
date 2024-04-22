package com.instagram.clone.Controller;

import com.instagram.clone.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
}
