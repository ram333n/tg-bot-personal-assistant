package com.prokopchuk.tgbotpersonalassistant.commons.dto.weather;

import lombok.Data;

@Data
public class HumidityStatsDto {

  private static final String DEFAULT_UNIT = "%";

  private int value;

  public String getUnit() {
    return DEFAULT_UNIT;
  }

}
