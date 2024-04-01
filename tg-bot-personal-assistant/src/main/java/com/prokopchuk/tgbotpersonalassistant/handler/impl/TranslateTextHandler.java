package com.prokopchuk.tgbotpersonalassistant.handler.impl;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.translation.TranslationResultDto;
import com.prokopchuk.tgbotpersonalassistant.handler.UserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.translation.TranslationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@AllArgsConstructor
public class TranslateTextHandler implements UserRequestHandler {

  private final TranslationService translationService;
  private final SenderService senderService;

  @Override
  public boolean isApplicable(UserRequestDto request) {
    return true; //TODO: just for test
  }

  @Override
  public void handle(UserRequestDto request) {
//    String message = request.getUpdate().getMessage().getText();
//    final String testLanguage = "uk";
//    TranslationResultDto result = translationService.translate(message, testLanguage);
//    SendMessage.builder()
//        .text(String.format("Your translation: %s", result.getTranslation()))
//        .chatId(request.getUpdate().getMessage().getChatId())
//        .build();
    senderService.sendMessage(request.getUpdate().getMessage().getChatId(), "YES! Dispatcher works");
  }

}
