package com.prokopchuk.tgbotpersonalassistant.commons.dto.weather;

import lombok.Data;

@Data
public class RainStatsDto {

  private static final String DEFAULT_UNIT = "mm";

  private double level;

  public String getUnit() {
    return DEFAULT_UNIT;
  }

}
