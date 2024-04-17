package com.prokopchuk.tgbotpersonalassistant.sender.impl;

import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
public class DefaultSenderService extends DefaultAbsSender implements SenderService {

  public DefaultSenderService(DefaultBotOptions options, @Value("${telegram.bot.token}") String token) {
    super(options, token);
  }

  @Override
  public void sendMessage(Long chatId, String text) {
    try {
      log.info("Sending message. Chat id: {}, text: {}", chatId, text);
      SendMessage message = createMessage(chatId, text);
      execute(message);
    } catch (TelegramApiException e) {
      log.warn("Unable to send message. Cause: {}", e);
      throw new RuntimeException(e);
    }
  }

  private SendMessage createMessage(Long chatId, String text) {
    SendMessage message = new SendMessage();
    message.setChatId(chatId);
    message.setText(text);

    return message;
  }

  @Override
  public void sendMessage(Long chatId, String text, ReplyKeyboard keyboard) {
    try {
      log.info("Sending message. Chat id: {}, text: {}, keyboard: {}", chatId, text, keyboard);
      SendMessage message = createMessage(chatId, text, keyboard);
      execute(message);
    } catch (TelegramApiException e) {
      log.warn("Unable to send message. Cause: {}", e);
      throw new RuntimeException(e);
    }
  }

  private SendMessage createMessage(Long chatId, String text, ReplyKeyboard keyboard) {
    SendMessage message = createMessage(chatId, text);
    message.setReplyMarkup(keyboard);

    return message;
  }

}
