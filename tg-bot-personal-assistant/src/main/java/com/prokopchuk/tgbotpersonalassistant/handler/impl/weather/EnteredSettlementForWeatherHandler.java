package com.prokopchuk.tgbotpersonalassistant.handler.impl.weather;

import com.github.prominence.openweathermap.api.exception.NoDataFoundException;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.weather.WeatherForecastDto;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import com.prokopchuk.tgbotpersonalassistant.weather.WeatherService;
import com.prokopchuk.tgbotpersonalassistant.weather.util.WeatherFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnteredSettlementForWeatherHandler extends AbstractUserRequestHandler {

  private final WeatherService weatherService;

  @Autowired
  public EnteredSettlementForWeatherHandler(
      UserSessionService userSessionService,
      SenderService senderService,
      StartConversationKeyboardBuilder startConversationKeyboardBuilder,
      WeatherService weatherService
  ) {
    super(userSessionService, senderService, startConversationKeyboardBuilder);
    this.weatherService = weatherService;
  }

  @Override
  public boolean isApplicable(UserRequestDto request) {
    return request.isWaitingForSettlementForWeather();
  }

  @Override
  public void handle(UserRequestDto request) {
    Long chatId = request.getChatId();
    Integer messageId = request.getMessageId();
    String settlement = request.getText();

    try {
      WeatherForecastDto weather = weatherService.getWeatherBySettlement(settlement);
      senderService.reply(chatId, messageId, WeatherFormatter.format(weather));
      moveToStartState(chatId);
    } catch (NoDataFoundException e) {
      senderService.reply(chatId, messageId, "Weather for this settlement not found");
      moveToStartState(chatId);
    }
  }

}
