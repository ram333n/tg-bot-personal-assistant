package com.prokopchuk.tgbotpersonalassistant.config;

import com.github.prominence.openweathermap.api.OpenWeatherMapClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeatherConfig {

  @Value("${weather.open-weather-map.token}")
  private String token;

  @Bean
  public OpenWeatherMapClient openWeatherMapClient() {
    return new OpenWeatherMapClient(token);
  }

}
