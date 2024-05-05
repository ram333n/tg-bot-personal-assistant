package com.prokopchuk.tgbotpersonalassistant.session.service.impl;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.UserSessionDto;
import com.prokopchuk.tgbotpersonalassistant.session.domain.UserSession;
import com.prokopchuk.tgbotpersonalassistant.session.mapper.UserSessionMapper;
import com.prokopchuk.tgbotpersonalassistant.session.repository.UserSessionRepository;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import com.prokopchuk.tgbotpersonalassistant.utils.JsonUtils;
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

  @Override
  public UserSessionDto changeState(Long chatId, ConversationState state) {
    UserSession session = userSessionRepository.findByChatId(chatId)
        .map(existent -> {
          existent.setState(state);
          return existent;
        })
        .orElse(createSession(chatId, state));

    return mapper.toDto(userSessionRepository.save(session));
  }

  private UserSession createSession(Long chatId, ConversationState state) {
    UserSession result = new UserSession();
    result.setChatId(chatId);
    result.setState(state);

    return result;
  }

  @Override
  public void updateSession(UserSessionDto session) {
    validateSessionExistence(session.getId());

    UserSession entity = mapper.toEntity(session);
    entity.setId(session.getId());

    userSessionRepository.save(entity);
  }

  private void validateSessionExistence(Long sessionId) {
    boolean exists = userSessionRepository.existsById(sessionId);

    if (!exists) {
      throw new IllegalStateException(String.format("Can't find session with id: %s", sessionId)); //TODO: handle exception properly
    }
  }

  @Override
  public <T> void changeSessionStateBySessionId(Long id, ConversationState newState, T newStateData) {
    UserSession entity = userSessionRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException(String.format("Can't find session with id: %s", id)));

    saveStateAndStateData(entity, newState, JsonUtils.writeAsString(newStateData));
  }

  private void saveStateAndStateData(UserSession entity, ConversationState newState, String newStateDataJson) {
    entity.setState(newState);
    entity.setStateData(newStateDataJson);

    userSessionRepository.save(entity);
  }

  @Override
  public <T> void changeStateData(Long id, T newStateData) {
    UserSession entity = userSessionRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException(String.format("Can't find session with id: %s", id)));

    entity.setStateData(JsonUtils.writeAsString(newStateData));

    userSessionRepository.save(entity);
  }

  @Override
  public <T> void changeSessionStateByChatId(Long chatId, ConversationState newState, T newStateData) {
    UserSession entity = userSessionRepository.findByChatId(chatId)
        .orElseThrow(() -> new IllegalStateException(String.format("Can't find session with chat id: %s", chatId)));

    saveStateAndStateData(entity, newState, JsonUtils.writeAsString(newStateData));
  }

  @Override
  public void changeSessionStateToStartByChatId(Long chatId) {
    UserSession entity = userSessionRepository.findByChatId(chatId)
        .orElseThrow(() -> new IllegalStateException(String.format("Can't find session with chat id: %s", chatId)));

    saveStateAndStateData(entity, ConversationState.START, "");
  }

  @Override
  public <T> T getStateData(UserSessionDto session) {
    return JsonUtils.read(session.getStateData(), (Class<T>) session.getState().getStateDataClass());
  }

  @Override
  public void changeSessionStateWithStateDataReset(Long chatId, ConversationState newState) {
    UserSession entity = userSessionRepository.findByChatId(chatId)
        .orElseThrow(() -> new IllegalStateException(String.format("Can't find session with chat id: %s", chatId)));

    saveStateAndStateData(entity, newState, "");
  }

  @Override
  public <T> void changeStateDataByChatId(Long chatId, T newStateData) {
    UserSession entity = userSessionRepository.findByChatId(chatId)
        .orElseThrow(() -> new IllegalStateException(String.format("Can't find session by chat id: %s", chatId)));

    entity.setStateData(JsonUtils.writeAsString(newStateData));

    userSessionRepository.save(entity);
  }

}
