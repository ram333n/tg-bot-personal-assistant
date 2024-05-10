package com.prokopchuk.tgbotpersonalassistant.handler.impl.notification;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.notification.NotificationDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.SaveNotificationStateData;
import com.prokopchuk.tgbotpersonalassistant.exception.IllegalTimeFormatException;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.notification.service.NotificationService;
import com.prokopchuk.tgbotpersonalassistant.notification.util.ScheduleTimeParser;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import com.prokopchuk.tgbotpersonalassistant.util.DateTimeUtils;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnteredTimeOnNotificationScheduleHandler extends AbstractUserRequestHandler {

  private final NotificationService notificationService;

  @Autowired
  public EnteredTimeOnNotificationScheduleHandler(
      UserSessionService userSessionService,
      SenderService senderService,
      StartConversationKeyboardBuilder startConversationKeyboardBuilder,
      NotificationService notificationService
  ) {
    super(userSessionService, senderService, startConversationKeyboardBuilder);
    this.notificationService = notificationService;
  }

  @Override
  public boolean isApplicable(UserRequestDto request) {
    return request.isWaitingForTimeToScheduleNotification();
  }

  @Override
  public void handle(UserRequestDto request) {
    Long chatId = request.getChatId();
    Integer messageId = request.getMessageId();

    try {
      SaveNotificationStateData stateData = userSessionService.getStateData(request.getSession());
      LocalDateTime scheduleTime = ScheduleTimeParser.parse(request.getText());

      if (DateTimeUtils.isBeforeNow(scheduleTime)) {
        senderService.reply(chatId, messageId, "Entered time must be in future!");
        return;
      }

      stateData.setScheduleTime(scheduleTime);
      notificationService.createNotification(createNotification(chatId, stateData));
      senderService.reply(chatId, messageId, "Notification has been scheduled successfully!");
      moveToStartState(chatId);
    } catch (IllegalTimeFormatException e) {
      senderService.reply(chatId, messageId, "You entered incorrect time. Please, use supported format");
    }
  }

  private NotificationDto createNotification(Long chatId, SaveNotificationStateData stateData) {
    NotificationDto result = new NotificationDto();
    result.setChatId(chatId);
    result.setDescription(stateData.getDescription());
    result.setSchedulingTime(stateData.getScheduleTime());

    return result;
  }

}
