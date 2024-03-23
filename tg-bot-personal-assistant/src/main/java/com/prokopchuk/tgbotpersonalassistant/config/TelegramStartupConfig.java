package com.prokopchuk.tgbotpersonalassistant.config;

import com.prokopchuk.tgbotpersonalassistant.bot.CustomTelegramLongPollingBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Configuration
public class TelegramStartupConfig {

  @Autowired
  public void initBot(TelegramConfig config, CustomTelegramLongPollingBot bot) {
    try {
      log.info("Starting polling telegram bot: {}", config.getName());
      TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
      botsApi.registerBot(bot);
    } catch (TelegramApiException e) {
      log.warn("Unable to start polling telegram bot: {}, cause: {}", config.getName(), e);
      throw new RuntimeException(e);
    }
  }

}
