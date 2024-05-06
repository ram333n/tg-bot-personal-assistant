package com.prokopchuk.tgbotpersonalassistant.commons.dto.button;

import lombok.Getter;

@Getter
public enum ConfirmationButtonText {

  YES("✅Yes"),
  NO("❌No");

  private final String text;

  ConfirmationButtonText(String text) {
    this.text = text;
  }

  public static boolean isYes(String text) {
    return YES.getText().equals(text);
  }

  public static boolean isNo(String text) {
    return NO.getText().equals(text);
  }

}
