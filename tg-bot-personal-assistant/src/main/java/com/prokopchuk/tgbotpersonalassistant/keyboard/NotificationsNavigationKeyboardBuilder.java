package com.prokopchuk.tgbotpersonalassistant.keyboard;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.NotificationsNavigationButtonText;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Component
public class NotificationsNavigationKeyboardBuilder {

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

}
