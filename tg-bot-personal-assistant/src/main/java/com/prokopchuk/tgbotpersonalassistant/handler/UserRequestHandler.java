package com.prokopchuk.tgbotpersonalassistant.handler;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;

public interface UserRequestHandler {

  boolean isApplicable(UserRequestDto request);

  void handle(UserRequestDto request);

}
