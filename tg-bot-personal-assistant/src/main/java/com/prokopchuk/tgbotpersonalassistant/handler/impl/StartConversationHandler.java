package com.prokopchuk.tgbotpersonalassistant.handler.impl;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Component
public class StartConversationHandler extends AbstractUserRequestHandler {

  @Autowired
  public StartConversationHandler(UserSessionService userSessionService, SenderService senderService) {
    super(userSessionService, senderService);
  }

  @Override
  public boolean isApplicable(UserRequestDto request) {
    return request.isStart();
  }

  @Override
  public void handle(UserRequestDto request) {
    KeyboardRow row = new KeyboardRow(); //TODO: refactor
    row.add("Translate");
    row.add("Generate QR");
    row.add("Create a note");
    row.add("Schedule a note");
    row.add("Weather");

    ReplyKeyboardMarkup keyboardMarkup = ReplyKeyboardMarkup.builder()
        .keyboard(List.of(row))
        .selective(true)
        .resizeKeyboard(true)
        .oneTimeKeyboard(false)
        .build();

    senderService.sendMessage(
        request.getSession().getChatId(),
        "YES! `/start` works!",
        keyboardMarkup
    );
  }

}
