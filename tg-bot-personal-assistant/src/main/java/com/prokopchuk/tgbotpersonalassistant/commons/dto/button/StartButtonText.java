package com.prokopchuk.tgbotpersonalassistant.commons.dto.button;

import lombok.Getter;

@Getter
public enum StartButtonText {

  TRANSLATE("\uD83C\uDDEC\uD83C\uDDE7 Translate"),
  GENERATE_QR("\uD83D\uDCF1 Generate QR code"),
  NOTES("\uD83D\uDDD2 Open notes"),
  SCHEDULE_NOTIFICATION("\uD83D\uDD54 Schedule a notification"),
  WEATHER("⛅ Check weather");

  private final String text;

  StartButtonText(String text) {
    this.text = text;
  }

}
