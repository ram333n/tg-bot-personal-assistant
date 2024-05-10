package com.prokopchuk.tgbotpersonalassistant.notification.util;

import com.prokopchuk.tgbotpersonalassistant.commons.Pair;
import com.prokopchuk.tgbotpersonalassistant.exception.IllegalTimeFormatException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScheduleTimeParser {

  private static final List<ScheduleTimeFormat> AVAILABLE_FORMATS = List.of(
      ScheduleTimeFormat.dateTime(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
      ScheduleTimeFormat.time(DateTimeFormatter.ofPattern("HH:mm"))
  );

  public static LocalDateTime parse(String input) {
    for (ScheduleTimeFormat format : AVAILABLE_FORMATS) {
      Pair<Boolean, LocalDateTime> p = format.parse(input);

      if (p.getV1()) {
        return p.getV2();
      }
    }

    throw new IllegalTimeFormatException("Illegal time format provided on parsing scheduling time");
  }

  @Data
  @AllArgsConstructor
  private static class ScheduleTimeFormat {

    private DateTimeFormatter formatter;
    private TimeUnitType timeUnitType;

    public static ScheduleTimeFormat time(DateTimeFormatter formatter) {
      return new ScheduleTimeFormat(formatter, TimeUnitType.TIME);
    }

    public static ScheduleTimeFormat dateTime(DateTimeFormatter formatter) {
      return new ScheduleTimeFormat(formatter, TimeUnitType.DATE_TIME);
    }

    public Pair<Boolean, LocalDateTime> parse(String input) {
      try {
        return Pair.of(true, parseInternal(input));
      } catch (DateTimeParseException e) {
        return Pair.of(false, null);
      }
    }

    private LocalDateTime parseInternal(String input) {
      if (timeUnitType.isTime()) {
        LocalTime time = LocalTime.parse(input, formatter);

        return LocalDateTime.of(LocalDate.now(), time);
      } else if (timeUnitType.isDateTime()) {
        return LocalDateTime.parse(input, formatter);
      } else {
        throw new IllegalStateException("Illegal state on parsing schedule time");
      }
    }

    enum TimeUnitType {
      DATE_TIME,
      TIME;

      public boolean isDateTime() {
        return DATE_TIME.equals(this);
      }

      public boolean isTime() {
        return TIME.equals(this);
      }

    }

  }

}
