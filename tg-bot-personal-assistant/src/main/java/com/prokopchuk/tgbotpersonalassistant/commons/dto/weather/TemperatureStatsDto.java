package com.prokopchuk.tgbotpersonalassistant.commons.dto.weather;

import lombok.Data;

@Data
public class TemperatureStatsDto {

  private double temperatureValue;
  private Double maxTemperature;
  private Double minTemperature;
  private Double feelsLike;
  private String unit;

}
