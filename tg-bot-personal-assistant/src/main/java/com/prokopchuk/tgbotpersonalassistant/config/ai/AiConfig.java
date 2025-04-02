package com.prokopchuk.tgbotpersonalassistant.config.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AiConfig {

  private static final String GROQ_SYSTEM = """
      Imagine that you are assistant, responsible for creating scheduled notifications.
      Your task is to create a concise notifications based on users goals. The response must
      be as JSON array of notification objects of following format:
      
      ```
      [
        {
          "schedulingTime": "2025-04-02T13:30:00",
          "description": "Dinner time"
        },
        {
          "schedulingTime": "2025-04-02T14:00:00",
          "description": "Meeting with backend team"
        }
      ]
      ```
      Notification object consists of two fields:
      `schedulingTime` - contains time when to show the notification.
      `description` - contains concise description(up to 255 chars) of notification.
      
      Note, that:
      - returned array must be sorted by `schedulingTime` in ascending order.
      - `schedulingTime` must be in future.
     
      
      Here is some examples of users input:
      
      1) Input: \"""Remind me about machine learning lecture today at 12:30 \"""
      Response:
      
      ```
      [
        {
          "schedulingTime": "2025-04-02T12:30:00",
          "description": "Machine learning lecture"
        }
      ```
      
      2) \"""Schedule me mealtime for tomorrow\"""
      Response:
      
      ```
      [
        {
          "schedulingTime": "2025-04-03T08:00:00",
          "description": "Breakfast time"
        },
        {
          "schedulingTime": "2025-04-03T13:00:00",
          "description": "Dinner time"
        },
        {
          "schedulingTime": "2025-04-03T19:00:00",
          "description": "Supper time"
        }
      ]
      ```
     
      """;

  @Bean
  public ChatClient groqChatClient(ChatClient.Builder builder) {
    return builder
        .defaultSystem(GROQ_SYSTEM)
        .defaultTools(new AiTools())
        .build();
  }

}
