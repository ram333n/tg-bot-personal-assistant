package com.prokopchuk.tgbotpersonalassistant.handler.impl.notification;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.NotificationsNavigationButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.notification.NotificationDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ListNotificationsStateData;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.NotificationsNavigationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.notification.service.NotificationService;
import com.prokopchuk.tgbotpersonalassistant.notification.util.NotificationMessageFormatter;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class EnteredFirstLevelNotificationOptionHandler extends AbstractUserRequestHandler {

  private final NotificationService notificationService;
  private final NotificationsNavigationKeyboardBuilder notificationsNavigationKeyboardBuilder;

  @Autowired
  public EnteredFirstLevelNotificationOptionHandler(
      UserSessionService userSessionService,
      SenderService senderService,
      StartConversationKeyboardBuilder startConversationKeyboardBuilder,
      NotificationService notificationService,
      NotificationsNavigationKeyboardBuilder notificationsNavigationKeyboardBuilder
  ) {
    super(userSessionService, senderService, startConversationKeyboardBuilder);
    this.notificationService = notificationService;
    this.notificationsNavigationKeyboardBuilder = notificationsNavigationKeyboardBuilder;
  }

  @Override
  public boolean isApplicable(UserRequestDto request) {
    return request.isWaitingForFirstLevelOptionForNotifications();
  }

  @Override
  public void handle(UserRequestDto request) {
    Long chatId = request.getChatId();
    Integer messageId = request.getMessageId();
    String input = request.getText();

    if (NotificationsNavigationButtonText.isScheduleNotification(input)) {
      handleScheduleNotification(chatId, messageId);
    } else if (NotificationsNavigationButtonText.isGetNotificationsPage(input)) {
      handleGetNotificationsPage(chatId);
    } else if (NotificationsNavigationButtonText.isBackToMainMenu(input)) {
      moveToStartState(chatId);
    } else {
      throw new IllegalStateException("Illegal state for on entering first level option for notifications");
    }
  }

  private void handleScheduleNotification(Long chatId, Integer messageId) {
    userSessionService.changeState(chatId, ConversationState.WAITING_FOR_DESCRIPTION_TO_SCHEDULE_NOTIFICATION);
    senderService.replyAndRemoveKeyboard(chatId, messageId,"Enter description for notification");
  }

  private void handleGetNotificationsPage(Long chatId) {
    Page<NotificationDto> notificationsPage = notificationService.getNotificationsFirstPageByChatId(
        chatId,
        NotificationMessageFormatter.NOTIFICATIONS_PAGE_SIZE
    );
    userSessionService.changeSessionStateByChatId(
        chatId,
        ConversationState.WAITING_FOR_SECOND_LEVEL_OPTION_FOR_NOTIFICATIONS,
        new ListNotificationsStateData()
    );
    senderService.sendMessageWithMarkdown(
        chatId,
        NotificationMessageFormatter.format(notificationsPage),
        notificationsNavigationKeyboardBuilder.buildNotificationsPage(notificationsPage)
    );
  }

}
