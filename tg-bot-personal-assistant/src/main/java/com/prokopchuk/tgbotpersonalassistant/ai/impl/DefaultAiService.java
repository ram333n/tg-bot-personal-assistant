package com.prokopchuk.tgbotpersonalassistant.ai.impl;

import com.prokopchuk.tgbotpersonalassistant.ai.AiService;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.notification.AiGeneratedNotificationDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultAiService implements AiService {

  private final ChatClient chatClient;

  @Override
  public List<AiGeneratedNotificationDto> generateNotifications(String prompt) {
    log.info("Sending prompt to generate notifications. Prompt: {}", prompt);

    return chatClient.prompt()
        .user(prompt)
        .call()
        .entity(new ParameterizedTypeReference<>() {});
  }

}
