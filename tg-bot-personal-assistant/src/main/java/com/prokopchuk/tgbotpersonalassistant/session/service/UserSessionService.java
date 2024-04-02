package com.prokopchuk.tgbotpersonalassistant.session.service;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.UserSessionDto;
import java.util.Optional;

public interface UserSessionService {

  Optional<UserSessionDto> getUserSessionByChatId(Long chatId);

}
