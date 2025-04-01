package com.prokopchuk.tgbotpersonalassistant.ai.impl;

import com.prokopchuk.tgbotpersonalassistant.ai.AiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultAiService implements AiService {

  private final ChatClient chatClient;

  public void test() { //TODO: remove
    var chatResponse  = chatClient.prompt()
        .user("Tell me some joke")
        .call()
        .content();

    log.info("Got response from llm: {}", chatResponse);
  }

}
