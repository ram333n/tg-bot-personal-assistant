package com.prokopchuk.tgbotpersonalassistant.bot;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.UserSessionDto;
import com.prokopchuk.tgbotpersonalassistant.config.TelegramConfig;
import com.prokopchuk.tgbotpersonalassistant.handler.DispatcherHandler;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomTelegramLongPollingBot extends TelegramLongPollingBot {

  private final TelegramConfig telegramConfig;
  private final UserSessionService userSessionService;
  private final DispatcherHandler dispatcher;

  @Override
  public void onUpdateReceived(Update update) {
    if (isIgnoreMessage(update)) {
      return;
    }

    UserRequestDto request = getUserRequest(update);
    boolean isDispatched = dispatcher.dispatch(request);

    if (!isDispatched) {
      log.warn("Request wasn't dispatched. Request: {}", request);
    }
  }

  private boolean isIgnoreMessage(Update update) {
    return !(update.hasMessage() && update.getMessage().hasText());
  }

  private UserRequestDto getUserRequest(Update update) {
    Long chatId = update.getMessage().getChatId();
    UserSessionDto session = userSessionService.getUserSessionByChatId(chatId)
        .orElse(UserSessionDto.start(chatId));

    return new UserRequestDto(update, session);
  }

  @Override
  public String getBotToken() {
    return telegramConfig.getToken();
  }

  @Override
  public String getBotUsername() {
    return telegramConfig.getName();
  }

}
