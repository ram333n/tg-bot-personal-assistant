package com.prokopchuk.tgbotpersonalassistant.weather.impl;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import com.github.prominence.openweathermap.api.enums.UnitSystem;
import com.github.prominence.openweathermap.api.model.Humidity;
import com.github.prominence.openweathermap.api.model.Temperature;
import com.github.prominence.openweathermap.api.model.weather.Location;
import com.github.prominence.openweathermap.api.model.weather.Rain;
import com.github.prominence.openweathermap.api.model.weather.Weather;
import com.github.prominence.openweathermap.api.model.weather.Wind;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.weather.HumidityStatsDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.weather.LocationDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.weather.RainStatsDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.weather.TemperatureStatsDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.weather.WeatherForecastDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.weather.WindStatsDto;
import com.prokopchuk.tgbotpersonalassistant.weather.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultWeatherService implements WeatherService {

  private final OpenWeatherMapClient openWeatherMapClient;

  @Override
  public WeatherForecastDto getWeatherBySettlement(String settlement) {
    Weather weather = openWeatherMapClient.currentWeather()
        .single()
        .byCityName(settlement)
        .unitSystem(UnitSystem.METRIC)
        .retrieve()
        .asJava();

    return toDto(weather);
  }

  private WeatherForecastDto toDto(Weather weather) {
    WeatherForecastDto result = new WeatherForecastDto();
    result.setCalculationTime(weather.getCalculationTime());
    result.setLocation(toLocationDto(weather.getLocation()));
    result.setTemperatureStats(toTemperatureStatsDto(weather.getTemperature()));
    result.setHumidityStats(toHumidityStatsDto(weather.getHumidity()));
    result.setWindStats(toWindStatsDto(weather.getWind()));
    result.setRainStats(toRainStatsDto(weather.getRain()));

    return result;
  }


  private LocationDto toLocationDto(Location location) {
    LocationDto result = new LocationDto();
    result.setCountryCode(location.getCountryCode());
    result.setSettlementName(location.getName());

    return result;
  }

  private TemperatureStatsDto toTemperatureStatsDto(Temperature temperature) {
    TemperatureStatsDto result = new TemperatureStatsDto();
    result.setTemperatureValue(temperature.getValue());
    result.setMaxTemperature(temperature.getMaxTemperature());
    result.setMinTemperature(temperature.getMinTemperature());
    result.setFeelsLike(temperature.getFeelsLike());
    result.setUnit(temperature.getUnit());

    return result;
  }

  private HumidityStatsDto toHumidityStatsDto(Humidity humidity) {
    HumidityStatsDto result = new HumidityStatsDto();
    result.setValue(humidity.getValue());

    return result;
  }

  private WindStatsDto toWindStatsDto(Wind wind) {
    WindStatsDto result = new WindStatsDto();
    result.setSpeed(wind.getSpeed());
    result.setUnit(wind.getUnit());

    return result;
  }

  private RainStatsDto toRainStatsDto(Rain rain) {
    if (rain == null) {
      return null;
    }

    RainStatsDto result = new RainStatsDto();
    result.setLevel(rain.getThreeHourLevel());

    return result;
  }

}
