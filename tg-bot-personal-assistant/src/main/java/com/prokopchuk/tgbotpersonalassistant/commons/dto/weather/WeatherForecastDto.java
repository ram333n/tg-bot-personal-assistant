package com.prokopchuk.tgbotpersonalassistant.commons.dto.weather;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class WeatherForecastDto {

  private LocalDateTime calculationTime;
  private LocationDto location;
  private TemperatureStatsDto temperatureStats;
  private HumidityStatsDto humidityStats;
  private WindStatsDto windStats;
  private RainStatsDto rainStats;

  public boolean hasRainStats() {
    return rainStats != null;
  }

}
