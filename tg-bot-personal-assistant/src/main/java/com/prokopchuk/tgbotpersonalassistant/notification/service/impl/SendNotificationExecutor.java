package com.prokopchuk.tgbotpersonalassistant.notification.service.impl;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.notification.NotificationDto;
import com.prokopchuk.tgbotpersonalassistant.notification.util.NotificationMessageFormatter;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendNotificationExecutor {

  private final SenderService senderService;

  public void execute(NotificationDto notification) {
    senderService.sendMessageWithMarkdown(notification.getChatId(), NotificationMessageFormatter.format(notification));
  }

}
