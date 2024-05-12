package com.prokopchuk.tgbotpersonalassistant.notification.util;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.notification.NotificationDto;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NotificationMessageFormatter {

  public static final int NOTIFICATIONS_PAGE_SIZE = 5;
  private static final String NOTIFICATION_SEND_MESSAGE_FORMAT = """
  Hello👋
  Here is a notification with the following description:
  
  `%s`
  """;

  private static final String YOU_DO_NOT_HAVE_ANY_NOTIFICATIONS_MESSAGE
      = "You haven't any scheduled notifications now \uD83D\uDE14";
  private static final String PAGES_COUNT_FORMAT = "Page: %d/%d";
  private static final String NOTIFICATION_FORMAT = """
  Id: `%d`
  \uD83D\uDDD2 Description: `%s`
  \uD83D\uDD52 Date created: _%s_
  \uD83D\uDD52 Send on: _%s_
  """;

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy\\-MM\\-dd HH:mm");
  private static final String SINGLE_NOTIFICATION_OPERATIONS_TIP
      = "If you want to delete notification, press on button with specific id";

  public static String formatOnSending(NotificationDto notification) {
    return String.format(NOTIFICATION_SEND_MESSAGE_FORMAT, notification.getDescription());
  }

  public static String format(Page<NotificationDto> notifications) {
    if (notifications.isEmpty()) {
      return YOU_DO_NOT_HAVE_ANY_NOTIFICATIONS_MESSAGE;
    }

    return formatPagesCount(notifications)
        + "\n\n"
        + formatNotifications(notifications)
        + "\n\n"
        + SINGLE_NOTIFICATION_OPERATIONS_TIP;
  }

  private static String formatPagesCount(Page<NotificationDto> notifications) {
    return String.format(PAGES_COUNT_FORMAT, notifications.getNumber() + 1, notifications.getTotalPages());
  }

  private static String formatNotifications(Page<NotificationDto> notifications) {
    StringJoiner result = new StringJoiner("\n");
    notifications.forEach(n -> result.add(formatSingleNotification(n)));

    return result.toString();
  }

  private static String formatSingleNotification(NotificationDto notification) {
    return String.format(
        NOTIFICATION_FORMAT,
        notification.getId(),
        notification.getDescription(),
        notification.getDateCreated().format(DATE_TIME_FORMATTER),
        notification.getSchedulingTime().format(DATE_TIME_FORMATTER)
    );
  }

}
