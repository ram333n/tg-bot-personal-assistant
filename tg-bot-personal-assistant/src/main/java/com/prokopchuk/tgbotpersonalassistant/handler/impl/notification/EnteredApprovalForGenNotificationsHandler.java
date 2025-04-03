package com.prokopchuk.tgbotpersonalassistant.handler.impl.notification;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.ConfirmationButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ApproveGenNotificationsStateData;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.SpecificNotificationStateData;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.UserSessionDto;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.NotificationsNavigationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.notification.service.NotificationService;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import org.springframework.stereotype.Component;

@Component
public class EnteredApprovalForGenNotificationsHandler extends AbstractUserRequestHandler {

  private final NotificationsNavigationKeyboardBuilder navigationKeyboardBuilder;
  private final NotificationService notificationService;

  public EnteredApprovalForGenNotificationsHandler(
      UserSessionService userSessionService,
      SenderService senderService,
      StartConversationKeyboardBuilder startConversationKeyboardBuilder,
      NotificationsNavigationKeyboardBuilder navigationKeyboardBuilder,
      NotificationService notificationService
  ) {
    super(userSessionService, senderService, startConversationKeyboardBuilder);
    this.navigationKeyboardBuilder = navigationKeyboardBuilder;
    this.notificationService = notificationService;
  }

  @Override
  public boolean isApplicable(UserRequestDto request) {
    return request.isWaitingForGeneratedNotificationsApproval();
  }

  @Override
  public void handle(UserRequestDto request) {
    Long chatId = request.getChatId();
    String input = request.getText();
    Integer messageId = request.getMessageId();

    if (ConfirmationButtonText.isYes(input)) {
      handleYes(chatId, messageId, request.getSession());
    } else if (ConfirmationButtonText.isNo(input)) {
      handleNo(chatId, messageId);
    } else {
      throw new IllegalStateException("Illegal state on note deleting");
    }
  }

  private void handleYes(Long chatId, Integer messageId, UserSessionDto session) {
    ApproveGenNotificationsStateData stateData = userSessionService.getStateData(session);
    notificationService.createNotifications(chatId, stateData.getGeneratedNotes());
    userSessionService.changeSessionStateWithStateDataReset(
        chatId,
        ConversationState.WAITING_FOR_FIRST_LEVEL_OPTION_FOR_NOTIFICATIONS
    );

    senderService.reply(
        chatId,
        messageId,
        "Notifications were saved successfully!",
        navigationKeyboardBuilder.buildFirstLevelOptions()
    );
  }

  private void handleNo(Long chatId, Integer messageId) {
    userSessionService.changeSessionStateWithStateDataReset(
        chatId,
        ConversationState.WAITING_FOR_FIRST_LEVEL_OPTION_FOR_NOTIFICATIONS
    );
    senderService.reply(
        chatId,
        messageId,
        "Saving cancelled",
        navigationKeyboardBuilder.buildFirstLevelOptions()
    );
  }

}
