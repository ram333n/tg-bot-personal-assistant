package com.prokopchuk.tgbotpersonalassistant.handler.impl.notification;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.StartButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.NotificationsNavigationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartNotificationConversationHandler extends AbstractUserRequestHandler {

  private final NotificationsNavigationKeyboardBuilder notificationsNavigationKeyboardBuilder;

  @Autowired
  public StartNotificationConversationHandler(
      UserSessionService userSessionService,
      SenderService senderService,
      StartConversationKeyboardBuilder startConversationKeyboardBuilder,
      NotificationsNavigationKeyboardBuilder notificationsNavigationKeyboardBuilder
  ) {
    super(userSessionService, senderService, startConversationKeyboardBuilder);
    this.notificationsNavigationKeyboardBuilder = notificationsNavigationKeyboardBuilder;
  }

  @Override
  public boolean isApplicable(UserRequestDto request) {
    return request.isStartState() && request.hasTextMessage(StartButtonText.SCHEDULE_NOTIFICATION.getText());
  }

  @Override
  public void handle(UserRequestDto request) {
    Long chatId = request.getChatId();
    userSessionService.changeState(chatId, ConversationState.WAITING_FOR_FIRST_LEVEL_OPTION_FOR_NOTIFICATIONS);
    senderService.sendMessage(
        chatId,
        "Select option:",
        notificationsNavigationKeyboardBuilder.buildFirstLevelOptions()
    );
  }

}
