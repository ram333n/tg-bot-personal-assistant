package com.prokopchuk.tgbotpersonalassistant.commons.dto.button;

import java.util.List;
import lombok.Getter;

@Getter
public enum NotificationsNavigationButtonText {

  GET_NOTIFICATIONS_PAGE("\uD83D\uDCD1 List notifications"),
  SCHEDULE_NOTIFICATION("🕒 Schedule a notification"),
  BACK_TO_MAIN_MENU("Back to main menu");

  private final String text;

  NotificationsNavigationButtonText(String text) {
    this.text = text;
  }

  public static List<NotificationsNavigationButtonText> getFirstLevelOptions() {
    return List.of(GET_NOTIFICATIONS_PAGE, SCHEDULE_NOTIFICATION, BACK_TO_MAIN_MENU);
  }

  public static boolean isGetNotificationsPage(String text) {
    return GET_NOTIFICATIONS_PAGE.getText().equals(text);
  }

  public static boolean isScheduleNotification(String text) {
    return SCHEDULE_NOTIFICATION.getText().equals(text);
  }

  public static boolean isBackToMainMenu(String text) {
    return BACK_TO_MAIN_MENU.getText().equals(text);
  }

}
