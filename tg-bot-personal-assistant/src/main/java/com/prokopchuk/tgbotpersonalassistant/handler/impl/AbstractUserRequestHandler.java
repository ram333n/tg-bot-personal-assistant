package com.prokopchuk.tgbotpersonalassistant.handler.impl;

import com.prokopchuk.tgbotpersonalassistant.handler.UserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractUserRequestHandler implements UserRequestHandler {

  protected final UserSessionService userSessionService;
  protected final SenderService senderService;

}
