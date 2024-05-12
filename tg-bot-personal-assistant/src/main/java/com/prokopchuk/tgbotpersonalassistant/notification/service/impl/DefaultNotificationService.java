package com.prokopchuk.tgbotpersonalassistant.notification.service.impl;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.notification.NotificationDto;
import com.prokopchuk.tgbotpersonalassistant.exception.IllegalTimeFormatException;
import com.prokopchuk.tgbotpersonalassistant.notification.domain.Notification;
import com.prokopchuk.tgbotpersonalassistant.notification.mapper.NotificationMapper;
import com.prokopchuk.tgbotpersonalassistant.notification.repository.NotificationRepository;
import com.prokopchuk.tgbotpersonalassistant.notification.service.NotificationService;
import com.prokopchuk.tgbotpersonalassistant.util.DateTimeUtils;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultNotificationService implements NotificationService {

  private static final String DATE_CREATED = "dateCreated";
  private final NotificationRepository repository;
  private final NotificationMapper mapper;
  private final SendNotificationExecutor sendExecutor;
  private final JobScheduler scheduler;

  @Override
  public Long createNotification(NotificationDto notification) {
    if (DateTimeUtils.isBeforeNow(notification.getSchedulingTime())) {
      throw new IllegalTimeFormatException("Illegal time provided on notification creation");
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
    Optional<Notification> notificationOpt = repository.findById(id);

    if (notificationOpt.isEmpty()) {
      return;
    }

    Notification notification = notificationOpt.get();
    UUID processId = notification.getProcessId();

    scheduler.delete(processId);
    repository.deleteById(id);
  }

  @Override
  public Page<NotificationDto> getNotificationsByChatId(Long chatId, int page, int limit) {
    Pageable pageable = createPageable(page, limit);

    return repository.findAllByChatId(chatId, pageable)
        .map(mapper::toDto);
  }

  private Pageable createPageable(int page, int limit) {
    return PageRequest.of(page, limit, Sort.by(DATE_CREATED).descending());
  }

  @Override
  public Page<NotificationDto> getNotificationsFirstPageByChatId(Long chatId, int limit) {
    return getNotificationsByChatId(chatId, 0, limit);
  }

  @Override
  public boolean isLastPage(Long chatId, int page, int limit) {
    return getNotificationsByChatId(chatId, page, limit)
        .isLast();
  }

  @Override
  public boolean existsById(Long id) {
    return repository.existsById(id);
  }

}
