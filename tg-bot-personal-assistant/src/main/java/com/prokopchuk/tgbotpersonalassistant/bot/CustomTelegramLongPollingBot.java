package com.prokopchuk.tgbotpersonalassistant.bot;

import com.prokopchuk.tgbotpersonalassistant.config.TelegramConfig;
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

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
      message.setChatId(update.getMessage().getChatId().toString());
      message.setText(update.getMessage().getText());

      try {
        execute(message); // Call method to send the message
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }
    }
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
