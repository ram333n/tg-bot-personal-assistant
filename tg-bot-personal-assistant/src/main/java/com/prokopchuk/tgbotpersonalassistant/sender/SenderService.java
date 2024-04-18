package com.prokopchuk.tgbotpersonalassistant.sender;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public interface SenderService {

  void sendMessage(Long chatId, String text);

  void sendMessage(Long chatId, String text, ReplyKeyboard keyboard);

  void sendMessageWithMarkdown(Long chatId, String text);

  void sendMessageAndRemoveKeyboard(Long chatId, String text);

}
