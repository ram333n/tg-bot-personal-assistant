package com.prokopchuk.tgbotpersonalassistant.handler.impl.weather;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.StartButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartWeatherConversationHandler extends AbstractUserRequestHandler {

  @Autowired
  public StartWeatherConversationHandler(
      UserSessionService userSessionService,
      SenderService senderService,
      StartConversationKeyboardBuilder startConversationKeyboardBuilder
  ) {
    super(userSessionService, senderService, startConversationKeyboardBuilder);
  }

  @Override
  public boolean isApplicable(UserRequestDto request) {
    return request.isStartState() && request.hasTextMessage(StartButtonText.WEATHER.getText());
  }

  @Override
  public void handle(UserRequestDto request) {
    Long chatId = request.getChatId();
    Integer messageId = request.getMessageId();

    userSessionService.changeState(chatId, ConversationState.WAITING_FOR_SETTLEMENT_FOR_WEATHER);
    senderService.replyAndRemoveKeyboard(chatId, messageId, "Enter settlement name");
  }

}
