package com.prokopchuk.tgbotpersonalassistant.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AiConfig {

  @Bean
  public ChatClient chatClient(ChatClient.Builder builder) {
    return builder
        .defaultSystem("You are the person, who tells funny jokes")
        .build();
  }

}
