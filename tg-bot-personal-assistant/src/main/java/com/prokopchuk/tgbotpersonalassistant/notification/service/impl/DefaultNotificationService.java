package com.prokopchuk.tgbotpersonalassistant.notification.service.impl;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.notification.NotificationDto;
import com.prokopchuk.tgbotpersonalassistant.exception.IllegalTimeFormatException;
import com.prokopchuk.tgbotpersonalassistant.notification.domain.Notification;
import com.prokopchuk.tgbotpersonalassistant.notification.mapper.NotificationMapper;
import com.prokopchuk.tgbotpersonalassistant.notification.repository.NotificationRepository;
import com.prokopchuk.tgbotpersonalassistant.notification.service.NotificationService;
import com.prokopchuk.tgbotpersonalassistant.util.DateTimeUtils;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultNotificationService implements NotificationService {

  private final NotificationRepository repository;
  private final NotificationMapper mapper;
  private final SendNotificationExecutor sendExecutor;
  private final JobScheduler scheduler;

  @Override
  public Long createNotification(NotificationDto notification) {
    if (DateTimeUtils.isBeforeNow(notification.getSchedulingTime())) {
      throw new IllegalTimeFormatException("Illegal time provided on notificaion creation");
    }

    UUID processUuid = UUID.randomUUID();
    Notification entity = mapper.toEntity(notification);

    entity.setProcessId(processUuid);
    Long notificationId = repository.save(entity).getId();
    schedule(processUuid, notificationId, notification);

    return notificationId;
  }

  private void schedule(UUID processUuid, Long notificationId, NotificationDto notification) {
    scheduler.schedule(
        processUuid,
        notification.getSchedulingTime(),
        () -> scheduleInternal(notificationId, notification)
    );
  }

  public void scheduleInternal(Long notificationId, NotificationDto notification) {
    repository.deleteById(notificationId);
    sendExecutor.execute(notification);
  }

  @Override
  public void deleteNotificationById(Long id) {
    //TODO: impl deleting
  }

}
