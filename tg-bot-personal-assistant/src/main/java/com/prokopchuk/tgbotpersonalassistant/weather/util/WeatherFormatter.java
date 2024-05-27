package com.prokopchuk.tgbotpersonalassistant.weather.util;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.weather.HumidityStatsDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.weather.LocationDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.weather.RainStatsDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.weather.TemperatureStatsDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.weather.WeatherForecastDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.weather.WindStatsDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WeatherFormatter {

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
  private static final String LOCATION_HEADER = "⛅ Weather for %s(%s) (at the %s):";
  private static final String TEMPERATURE_FORMAT = "🌡 Temperature: %.2f %s (feels like %.2f %s)";
  private static final String HUMIDITY_FORMAT = "\uD83D\uDCA7 Humidity: %d %s";
  private static final String WIND_FORMAT = "\uD83D\uDCA8 Wind: %.2f %s";
  private static final String RAIN_FORMAT = "\uD83C\uDF27 Rain: %.2f %s";

  public static String format(WeatherForecastDto weatherForecast) {
    StringJoiner joiner = new StringJoiner("\n");
    appendLocationHeader(joiner, weatherForecast.getLocation(), weatherForecast.getCalculationTime());
    appendTemperatureStats(joiner, weatherForecast.getTemperatureStats());
    appendHumidityStats(joiner, weatherForecast.getHumidityStats());
    appendWindStats(joiner, weatherForecast.getWindStats());

    if (weatherForecast.hasRainStats()) {
      appendRainStats(joiner, weatherForecast.getRainStats());
    }

    return joiner.toString();
  }

  private static void appendLocationHeader(StringJoiner joiner, LocationDto location, LocalDateTime calculationTime) {
    joiner.add(String.format(
        LOCATION_HEADER,
        location.getSettlementName(),
        location.getCountryCode(),
        calculationTime.format(DATE_TIME_FORMATTER)
    ));
  }

  private static void appendTemperatureStats(StringJoiner joiner, TemperatureStatsDto temperatureStats) {
    joiner.add(String.format(
        TEMPERATURE_FORMAT,
        temperatureStats.getTemperatureValue(),
        temperatureStats.getUnit(),
        temperatureStats.getFeelsLike(),
        temperatureStats.getUnit()
    ));
  }

  private static void appendHumidityStats(StringJoiner joiner, HumidityStatsDto humidityStats) {
    joiner.add(String.format(HUMIDITY_FORMAT, humidityStats.getValue(), humidityStats.getUnit()));
  }

  private static void appendWindStats(StringJoiner joiner, WindStatsDto windStats) {
    joiner.add(String.format(WIND_FORMAT, windStats.getSpeed(), windStats.getUnit()));
  }

  private static void appendRainStats(StringJoiner joiner, RainStatsDto rainStats) {
    joiner.add(String.format(RAIN_FORMAT, rainStats.getLevel(), rainStats.getUnit()));
  }

}
