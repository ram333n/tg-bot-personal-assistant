package com.prokopchuk.tgbotpersonalassistant.handler.impl.translation;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.TranslateStateData;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.SelectLanguageReplyKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnteredTextToTranslateHandler extends AbstractUserRequestHandler {

  private final SelectLanguageReplyKeyboardBuilder languageKeyboardBuilder;

  @Autowired
  public EnteredTextToTranslateHandler(
      UserSessionService userSessionService,
      SenderService senderService,
      StartConversationKeyboardBuilder startConversationKeyboardBuilder,
      SelectLanguageReplyKeyboardBuilder languageKeyboardBuilder
  ) {
    super(userSessionService, senderService, startConversationKeyboardBuilder);
    this.languageKeyboardBuilder = languageKeyboardBuilder;
  }

  @Override
  public boolean isApplicable(UserRequestDto request) {
    return request.isWaitingTextToTranslateState() && request.hasText();
  }

  @Override
  public void handle(UserRequestDto request) {
    TranslateStateData translateStateData = createTranslateData(request.getText());
    userSessionService.changeSessionState(request.getSessionId(), ConversationState.WAITING_FOR_LANGUAGE_TO_TRANSLATE, translateStateData);
    senderService.sendMessage(request.getChatId(), "Select the target language", languageKeyboardBuilder.build());
  }

  private TranslateStateData createTranslateData(String textToTranslate) {
    TranslateStateData result = new TranslateStateData();
    result.setText(textToTranslate);

    return result;
  }

}
