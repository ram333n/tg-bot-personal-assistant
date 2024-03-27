package com.prokopchuk.tgbotpersonalassistant.bot;

import com.prokopchuk.tgbotpersonalassistant.config.TelegramConfig;
import com.prokopchuk.tgbotpersonalassistant.translation.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class CustomTelegramLongPollingBot extends TelegramLongPollingBot {

  private final TelegramConfig telegramConfig;
  private final TranslationService translationService;

  @Override
  public void onUpdateReceived(Update update) {
    if (isIgnoreMessage(update)) {
      return;
    }

    SendMessage message = new SendMessage();
    message.setChatId(update.getMessage().getChatId().toString());
    message.setText(String.format("Your translation is here: %s", translationService.translate(update.getMessage().getText(), "uk")));

    try {
      execute(message); // Call method to send the message
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

  private boolean isIgnoreMessage(Update update) {
    return !(update.hasMessage() && update.getMessage().hasText());
  }

  @Override
  public String getBotToken() {
    return telegramConfig.getToken();
  }

  @Override
  public String getBotUsername() {
    return telegramConfig.getName();
  }

}
