package com.prokopchuk.tgbotpersonalassistant.handler.impl;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

@Component
public class StartConversationHandler extends AbstractUserRequestHandler {

  private static final String START_COMMAND = "/start";

  @Autowired
  public StartConversationHandler(
      UserSessionService userSessionService,
      SenderService senderService,
      StartConversationKeyboardBuilder startConversationKeyboardBuilder
  ) {
    super(userSessionService, senderService, startConversationKeyboardBuilder);
  }

  @Override
  public boolean isApplicable(UserRequestDto request) {
    return request.isStartState() && request.hasTextMessage(START_COMMAND);
  }

  @Override
  public void handle(UserRequestDto request) {
    ReplyKeyboardMarkup replyKeyboard = startConversationKeyboardBuilder.build();

    senderService.sendMessage(
        request.getSession().getChatId(),
        "YES! `/start` works!",
        replyKeyboard
    );
  }

}
