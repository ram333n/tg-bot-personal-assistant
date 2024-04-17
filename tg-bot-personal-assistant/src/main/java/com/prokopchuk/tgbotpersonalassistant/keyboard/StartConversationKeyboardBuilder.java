package com.prokopchuk.tgbotpersonalassistant.keyboard;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.StartButtonText;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Component
public class StartConversationKeyboardBuilder {

  public ReplyKeyboardMarkup build() {
    List<KeyboardRow> rows = new ArrayList<>();

    for (StartButtonText item : StartButtonText.values()) {
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
