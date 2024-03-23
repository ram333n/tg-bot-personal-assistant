package com.prokopchuk.tgbotpersonalassistant.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TelegramConfig {

  @Value("${telegram.bot.token}")
  private String token;

  @Value("${telegram.bot.name}")
  private String name;

}
