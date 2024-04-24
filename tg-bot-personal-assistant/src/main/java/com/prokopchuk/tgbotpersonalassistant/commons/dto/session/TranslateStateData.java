package com.prokopchuk.tgbotpersonalassistant.commons.dto.session;

import lombok.Data;

@Data
public class TranslateStateData {

  private String text;
  private Integer textMessageId;
  private String targetLang;
  private int languagePage = 0;

  public void moveToNextPage() {
    languagePage++;
  }

  public void moveToPreviousPage() {
    if (languagePage > 0) {
      languagePage--;
    }
  }

}
