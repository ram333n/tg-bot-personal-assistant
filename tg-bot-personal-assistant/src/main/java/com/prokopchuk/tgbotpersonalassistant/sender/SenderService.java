package com.prokopchuk.tgbotpersonalassistant.sender;

import java.io.InputStream;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface SenderService {

  void sendMessage(Long chatId, String text);

  void sendMessage(Long chatId, String text, ReplyKeyboard keyboard);

  void sendMessageWithMarkdown(Long chatId, String text);

  void sendMessageAndRemoveKeyboard(Long chatId, String text);

  void replyWithMessageAndMarkdown(Long chatId, Integer messageId, String text);

  void replyWithImage(Long chatId, Integer messageId, InputStream image, String text);

  void replyAndRemoveKeyboard(Long chatId, Integer messageId, String text);

  void reply(Long chatId, Integer messageId, String text, ReplyKeyboard keyboard);

  void sendMessageWithMarkdown(Long chatId, String text, ReplyKeyboard keyboard);

  void reply(Long chatId, Integer messageId, String text);

}
