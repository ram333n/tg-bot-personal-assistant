package com.prokopchuk.tgbotpersonalassistant.commons.dto.button;

import lombok.Getter;

@Getter
public enum NavigationButtonText {

  BACK("⬅"),
  FORWARD("➡");

  private final String text;

  NavigationButtonText(String text) {
    this.text = text;
  }

  public static boolean isBack(String input) {
    return BACK.getText().equals(input);
  }

  public static boolean isForward(String input) {
    return FORWARD.getText().equals(input);
  }

}
