package com.prokopchuk.tgbotpersonalassistant.sender.impl;

import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
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
      SendMessage message = createMessage(chatId, text, false);
      execute(message);
    } catch (TelegramApiException e) {
      log.warn("Unable to send message. Cause: {}", e);
      throw new RuntimeException(e);
    }
  }

  private SendMessage createMessage(Long chatId, String text, boolean enableMarkdown) {
    SendMessage message = new SendMessage();
    message.setChatId(chatId);
    message.setText(text);
    message.enableMarkdownV2(enableMarkdown);

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
    SendMessage message = createMessage(chatId, text, false);
    message.setReplyMarkup(keyboard);

    return message;
  }

  @Override
  public void sendMessageWithMarkdown(Long chatId, String text) {
    try {
      log.info("Sending message. Chat id: {}, text: {}", chatId, text);
      SendMessage message = createMessage(chatId, text, true);
      execute(message);
    } catch (TelegramApiException e) {
      log.warn("Unable to send message. Cause: {}", e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public void sendMessageAndRemoveKeyboard(Long chatId, String text) {
    try {
      log.info("Sending message. Chat id: {}, text: {}", chatId, text);
      SendMessage message = createMessage(chatId, text, false);
      message.setReplyMarkup(new ReplyKeyboardRemove(true));
      execute(message);
    } catch (TelegramApiException e) {
      log.warn("Unable to send message. Cause: {}", e);
      throw new RuntimeException(e);
    }
  }

  @Override
  public void replyWithMessageAndMarkdown(Long chatId, Integer messageId, String text) {
    try {
      log.info("Replying to message. Chat id: {}, message id: {}, text: {}", chatId, messageId, text);
      SendMessage message = createReplyMessage(chatId, messageId, text, true);
      execute(message);
    } catch (TelegramApiException e) {
      log.warn("Unable to reply to message. Cause: ", e);
      throw new RuntimeException(e);
    }
  }

  private SendMessage createReplyMessage(Long chatId, Integer messageId, String text, boolean enableMarkdown) {
    SendMessage message = createMessage(chatId, text, enableMarkdown);
    message.setReplyToMessageId(messageId);

    return message;
  }

  @Override
  public void replyWithImage(Long chatId, Integer messageId, InputStream image, String text) {
    try {
      log.info("Replying to message with image. Chat id: {}, message id: {}, text: {}", chatId, messageId, text);
      SendPhoto message = SendPhoto.builder()
          .chatId(chatId)
          .replyToMessageId(messageId)
          .photo(new InputFile(image, text))
          .build();

      execute(message);
    } catch (TelegramApiException e) {
      log.warn("Unable to reply to message with image. Cause: ", e);
      throw new RuntimeException(e);
    }
  }

}
