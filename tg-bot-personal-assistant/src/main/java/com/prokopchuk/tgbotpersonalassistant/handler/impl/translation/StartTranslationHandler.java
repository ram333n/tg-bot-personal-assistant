package com.prokopchuk.tgbotpersonalassistant.handler.impl.translation;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.StartButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartTranslationHandler extends AbstractUserRequestHandler {

  @Autowired
  public StartTranslationHandler(
      UserSessionService userSessionService,
      SenderService senderService,
      StartConversationKeyboardBuilder startConversationKeyboardBuilder
  ) {
    super(userSessionService, senderService, startConversationKeyboardBuilder);
  }

  @Override
  public boolean isApplicable(UserRequestDto request) {
    return request.isStartState() && request.hasTextMessage(StartButtonText.TRANSLATE.getText());
  }

  @Override
  public void handle(UserRequestDto request) {
    userSessionService.changeState(request.getChatId(), ConversationState.WAITING_FOR_TEXT_TO_TRANSLATE);
    senderService.replyAndRemoveKeyboard(request.getChatId(), request.getMessageId(), "Enter the text to translate");
  }

}
