package com.prokopchuk.tgbotpersonalassistant.weather;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.weather.WeatherForecastDto;

public interface WeatherService {

  WeatherForecastDto getWeatherBySettlement(String settlement);

}
