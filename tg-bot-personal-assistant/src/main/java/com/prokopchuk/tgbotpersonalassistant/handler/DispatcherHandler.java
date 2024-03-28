package com.prokopchuk.tgbotpersonalassistant.handler;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;

public interface DispatcherHandler {

  boolean dispatch(UserRequestDto request);

}
