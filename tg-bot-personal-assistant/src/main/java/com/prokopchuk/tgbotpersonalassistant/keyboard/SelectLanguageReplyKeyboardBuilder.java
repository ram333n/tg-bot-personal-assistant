package com.prokopchuk.tgbotpersonalassistant.keyboard;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.NavigationButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.translation.Language;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Component
public class SelectLanguageReplyKeyboardBuilder {

  private static final int DEFAULT_LIMIT_VALUE = 5;

  public ReplyKeyboardMarkup build() {
    return build(0, DEFAULT_LIMIT_VALUE);
  }

  public ReplyKeyboardMarkup build(int page) {
    return build(page, DEFAULT_LIMIT_VALUE);
  }

  public ReplyKeyboardMarkup build(int page, int limit) {
    Language[] languages = Language.values();
    int lowerBound = Math.min(page * limit, languages.length);
    int upperBound = Math.min((page + 1) * limit, languages.length);

    List<KeyboardRow> rows = new ArrayList<>();

    for (int i = lowerBound; i < upperBound; i++) {
      rows.add(createRowWithLanguage(languages[i]));
    }

    rows.add(createControlRow());

    return ReplyKeyboardMarkup.builder()
        .keyboard(rows)
        .selective(true)
        .resizeKeyboard(true)
        .oneTimeKeyboard(false)
        .build();
  }

  private KeyboardRow createRowWithLanguage(Language language) {
    KeyboardRow row = new KeyboardRow();
    row.add(language.getName());

    return row;
  }

  private KeyboardRow createControlRow() {
    KeyboardRow row = new KeyboardRow();
    row.add(NavigationButtonText.BACK.getText());
    row.add(NavigationButtonText.FORWARD.getText());

    return row;
  }

  public int lastPageNumber(int limit) {
    return Language.values().length / limit;
  }

  public int lastPageNumber() {
    return lastPageNumber(DEFAULT_LIMIT_VALUE);
  }

}
