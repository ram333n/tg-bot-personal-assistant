package com.prokopchuk.tgbotpersonalassistant.notification.service;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.notification.NotificationDto;

public interface NotificationService {

  Long createNotification(NotificationDto notification);

  void deleteNotificationById(Long id);

}
