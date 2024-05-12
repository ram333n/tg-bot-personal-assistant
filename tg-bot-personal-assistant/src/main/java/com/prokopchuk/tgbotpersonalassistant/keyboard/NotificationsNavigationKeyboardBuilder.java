package com.prokopchuk.tgbotpersonalassistant.keyboard;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.ConfirmationButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.NavigationButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.NotificationsNavigationButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.notification.NotificationDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Component
public class NotificationsNavigationKeyboardBuilder {

  private static final String NOTIFICATION_BUTTON_TEXT_FORMAT = "%d";

  public ReplyKeyboardMarkup buildFirstLevelOptions() {
    List<KeyboardRow> rows = new ArrayList<>();

    for (NotificationsNavigationButtonText item : NotificationsNavigationButtonText.getFirstLevelOptions()) {
      KeyboardRow currentRow = new KeyboardRow();
      currentRow.add(item.getText());
      rows.add(currentRow);
    }

    return ReplyKeyboardMarkup.builder()
        .keyboard(rows)
        .selective(true)
        .resizeKeyboard(true)
        .oneTimeKeyboard(false)
        .build();
  }

  public ReplyKeyboardMarkup buildNotificationsPage(Page<NotificationDto> page) {
    List<KeyboardRow> rows = new ArrayList<>();

    for (NotificationDto notification : page) {
      rows.add(buildRowForSingleNotification(notification));
    }

    KeyboardRow navigation = page.isEmpty()
        ? buildBackToPreviousMenuButton()
        : buildNavigationButtons();

    rows.add(navigation);

    return ReplyKeyboardMarkup.builder()
        .keyboard(rows)
        .selective(true)
        .resizeKeyboard(true)
        .oneTimeKeyboard(false)
        .build();
  }

  private KeyboardRow buildRowForSingleNotification(NotificationDto notification) {
    KeyboardRow result = new KeyboardRow();
    result.add(String.format(NOTIFICATION_BUTTON_TEXT_FORMAT, notification.getId()));

    return result;
  }

  private KeyboardRow buildBackToPreviousMenuButton() {
    KeyboardRow result = new KeyboardRow();
    result.add(NavigationButtonText.BACK_TO_PREVIOUS_MENU.getText());

    return result;
  }

  private KeyboardRow buildNavigationButtons() {
    KeyboardRow result = new KeyboardRow();
    result.add(NavigationButtonText.BACK.getText());
    result.add(NavigationButtonText.BACK_TO_PREVIOUS_MENU.getText());
    result.add(NavigationButtonText.FORWARD.getText());

    return result;
  }

  public ReplyKeyboardMarkup buildConfirmationButtons() {
    List<KeyboardRow> rows = new ArrayList<>(); //TODO: refactor to remove duplicated code

    for (ConfirmationButtonText item : ConfirmationButtonText.values()) {
      KeyboardRow currentRow = new KeyboardRow();
      currentRow.add(item.getText());
      rows.add(currentRow);
    }

    return ReplyKeyboardMarkup.builder()
        .keyboard(rows)
        .selective(true)
        .resizeKeyboard(true)
        .oneTimeKeyboard(false)
        .build();
  }

}
