package com.prokopchuk.tgbotpersonalassistant.handler.impl.notification;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.NotificationsNavigationButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnteredFirstLevelNotificationOptionHandler extends AbstractUserRequestHandler {

  @Autowired
  public EnteredFirstLevelNotificationOptionHandler(
      UserSessionService userSessionService,
      SenderService senderService,
      StartConversationKeyboardBuilder startConversationKeyboardBuilder
  ) {
    super(userSessionService, senderService, startConversationKeyboardBuilder);
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
      //TODO: handle
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

}
