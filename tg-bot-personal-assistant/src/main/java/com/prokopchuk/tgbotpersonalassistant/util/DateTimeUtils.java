package com.prokopchuk.tgbotpersonalassistant.util;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateTimeUtils {

  public static boolean isBeforeNow(LocalDateTime dateTime) {
    return dateTime.isBefore(LocalDateTime.now());
  }

}
