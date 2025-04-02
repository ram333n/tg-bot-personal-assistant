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

  @Scheduled(fixedRate = 500000)
  public void test() {
    var chatResponse = chatClient.prompt()
        .user("Create me daily schedule of Christiano Ronaldo")
        .call()
        .entity(new ParameterizedTypeReference<List<AiGeneratedNotificationDto>>() {
        });

    log.info("Got response from llm: {}", chatResponse);
  }

}
