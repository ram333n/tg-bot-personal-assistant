package com.prokopchuk.tgbotpersonalassistant.handler.impl.translation;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.NavigationButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.TranslateStateData;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.translation.Language;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.translation.TranslationResultDto;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.SelectLanguageReplyKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import com.prokopchuk.tgbotpersonalassistant.translation.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnteredTargetLanguageHandler extends AbstractUserRequestHandler {

  private static final String TRANSLATION_RESPONSE_FORMAT
      = "Your translation from %s to %s is:\n"
      + "%s";

  private final TranslationService translationService;
  private final SelectLanguageReplyKeyboardBuilder languageKeyboardBuilder;

  @Autowired
  public EnteredTargetLanguageHandler(
      UserSessionService userSessionService,
      SenderService senderService,
      StartConversationKeyboardBuilder startConversationKeyboardBuilder,
      TranslationService translationService,
      SelectLanguageReplyKeyboardBuilder languageKeyboardBuilder
  ) {
    super(userSessionService, senderService, startConversationKeyboardBuilder);
    this.translationService = translationService;
    this.languageKeyboardBuilder = languageKeyboardBuilder;
  }

  @Override
  public boolean isApplicable(UserRequestDto request) {
    return request.isWaitingForLanguageToTranslate() && request.hasText();
  }

  @Override
  public void handle(UserRequestDto request) {
    String input = request.getText();
    TranslateStateData translateStateData = userSessionService.getStateData(request.getSession());

    if (NavigationButtonText.isBack(input)) {
      handlePageBack(request, translateStateData);
      return;
    }

    if (NavigationButtonText.isForward(input)) {
      handlePageForward(request, translateStateData);
      return;
    }

    if (Language.isNotSupportedByName(input)) {
      senderService.sendMessage(
          request.getChatId(),
          String.format("Language \"%s\" isn't supported by bot. Please, choose another one", input)
      );

      return;
    }

    Language language = Language.fromName(input);
    TranslationResultDto translationResult = translationService.translate(translateStateData.getText(), language.getCode());
    sendTranslationResponse(request.getChatId(), translationResult);
    moveToStartState(request.getChatId());
  }

  private void handlePageBack(UserRequestDto request, TranslateStateData translateStateData) {
    if (translateStateData.getLanguagePage() == 0) {
      senderService.sendMessage(request.getChatId(), "You can't move back, because you are on the first page of available languages!");
      return;
    }

    translateStateData.moveToPreviousPage();
    userSessionService.changeStateData(request.getSessionId(), translateStateData);
    senderService.sendMessage(
        request.getChatId(),
        "Previous page of languages",
        languageKeyboardBuilder.build(translateStateData.getLanguagePage())
    );
  }

  private void handlePageForward(UserRequestDto request, TranslateStateData translateStateData) {
    int lastPageNumber = languageKeyboardBuilder.lastPageNumber();

    if (translateStateData.getLanguagePage() >= lastPageNumber) {
      senderService.sendMessage(request.getChatId(), "You can't move forward, because you are on the last page of available languages!");
      return;
    }

    translateStateData.moveToNextPage();
    userSessionService.changeStateData(request.getSessionId(), translateStateData);
    senderService.sendMessage(
        request.getChatId(),
        "Next page of languages",
        languageKeyboardBuilder.build(translateStateData.getLanguagePage())
    );
  }

  private void sendTranslationResponse(Long chatId, TranslationResultDto translationResult) { //TODO: maybe add src text
    String message = String.format(TRANSLATION_RESPONSE_FORMAT,
        Language.fromCode(translationResult.getSourceLang()).getName(),
        Language.fromCode(translationResult.getTargetLang()).getName(),
        translationResult.getTranslation()
    );

    senderService.sendMessage(chatId, message);
  }

}
