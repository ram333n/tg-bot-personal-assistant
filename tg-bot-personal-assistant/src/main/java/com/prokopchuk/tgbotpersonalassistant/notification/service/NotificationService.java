package com.prokopchuk.tgbotpersonalassistant.notification.service;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.notification.NotificationDto;
import org.springframework.data.domain.Page;

public interface NotificationService {

  Long createNotification(NotificationDto notification);

  void deleteNotificationById(Long id);

  Page<NotificationDto> getNotificationsByChatId(Long chatId, int page, int limit);

  Page<NotificationDto> getNotificationsFirstPageByChatId(Long chatId, int limit);

  boolean isLastPage(Long chatId, int page, int limit);

  boolean existsById(Long id);

}
