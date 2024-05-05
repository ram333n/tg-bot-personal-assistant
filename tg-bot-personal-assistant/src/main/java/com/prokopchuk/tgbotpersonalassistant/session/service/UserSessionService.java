package com.prokopchuk.tgbotpersonalassistant.session.service;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.UserSessionDto;
import java.util.Optional;

public interface UserSessionService {

  Optional<UserSessionDto> getUserSessionByChatId(Long chatId);

  UserSessionDto changeState(Long chatId, ConversationState state);

  void updateSession(UserSessionDto session);

  <T> void changeSessionStateBySessionId(Long id, ConversationState newState, T newStateData);

  <T> void changeStateData(Long id, T newStateData);

  <T> void changeSessionStateByChatId(Long chatId, ConversationState newState, T newStateData);

  void changeSessionStateToStartByChatId(Long chatId);

  <T> T getStateData(UserSessionDto request);

  void changeSessionStateWithStateDataReset(Long chatId, ConversationState newState);

  <T> void changeStateDataByChatId(Long chatId, T newStateData);

}
