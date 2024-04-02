package com.prokopchuk.tgbotpersonalassistant.session.service.impl;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.UserSessionDto;
import com.prokopchuk.tgbotpersonalassistant.session.mapper.UserSessionMapper;
import com.prokopchuk.tgbotpersonalassistant.session.repository.UserSessionRepository;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserSessionService implements UserSessionService {

  private final UserSessionRepository userSessionRepository;
  private final UserSessionMapper mapper;

  @Override
  public Optional<UserSessionDto> getUserSessionByChatId(Long chatId) {
    return userSessionRepository.findByChatId(chatId)
        .map(mapper::toDto);
  }

}
