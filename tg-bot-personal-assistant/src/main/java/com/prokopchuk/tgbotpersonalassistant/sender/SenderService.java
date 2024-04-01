package com.prokopchuk.tgbotpersonalassistant.sender;

public interface SenderService {

  void sendMessage(Long chatId, String text);

}
