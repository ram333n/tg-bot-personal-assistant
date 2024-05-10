package com.prokopchuk.tgbotpersonalassistant.notification.util;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.notification.NotificationDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NotificationMessageFormatter {

  private static final String NOTIFICATION_MESSAGE_FORMAT = """
  Hello👋
  Here is a notification with the following description:
  
  `%s`
  """;

  public static String format(NotificationDto notification) {
    return String.format(NOTIFICATION_MESSAGE_FORMAT, notification.getDescription());
  }

}
