package com.prokopchuk.tgbotpersonalassistant.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Configuration
@Getter
public class TelegramConfig {

  @Value("${telegram.bot.token}")
  private String token;

  @Value("${telegram.bot.name}")
  private String name;

  @Bean
  public DefaultBotOptions botOptions() {
    return new DefaultBotOptions();
  }

}
