package com.prokopchuk.tgbotpersonalassistant.handler.impl;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.handler.DispatcherHandler;
import com.prokopchuk.tgbotpersonalassistant.handler.UserRequestHandler;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class DefaultDispatcherHandler implements DispatcherHandler {

  private final List<UserRequestHandler> handlers;

  @Override
  public boolean dispatch(UserRequestDto request) {
    log.info("Dispatching request. Request: {}", request);

    for (UserRequestHandler handler : handlers) {
      if (handler.isApplicable(request)) {
        handler.handle(request);
        return true;
      }
    }

    return false;
  }

}
