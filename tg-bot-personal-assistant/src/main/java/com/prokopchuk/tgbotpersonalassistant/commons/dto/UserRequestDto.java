package com.prokopchuk.tgbotpersonalassistant.commons.dto;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.UserSessionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Update;

@Data
@AllArgsConstructor
public class UserRequestDto {

  private final Update update;
  private final UserSessionDto session;

  public boolean hasText() {
    return update.hasMessage() && update.getMessage().hasText();
  }

  public boolean hasTextMessage(String message) {
    return update.hasMessage()
        && update.getMessage().hasText()
        && getText().equals(message);
  }

  public String getText() {
    return update.getMessage().getText();
  }

  public boolean isStartState() {
    return ConversationState.START.equals(session.getState());
  }

  public boolean isWaitingTextToTranslateState() {
    return ConversationState.WAITING_FOR_TEXT_TO_TRANSLATE.equals(session.getState());
  }

  public boolean isToTranslate() {
    return getState().isTranslationState();
  }

  public ConversationState getState() {
    return session.getState();
  }

  public Long getChatId() {
    return update.getMessage().getChatId();
  }

  public boolean isWaitingForLanguageToTranslate() {
    return ConversationState.WAITING_FOR_LANGUAGE_TO_TRANSLATE.equals(session.getState());
  }

  public String getStateData() {
    return session.getStateData();
  }

  public Long getSessionId() {
    return session.getId();
  }

  public boolean isWaitingForTextToGenerateQr() {
    return ConversationState.WAITING_FOR_TEXT_TO_GENERATE_QR.equals(session.getState());
  }

  public Integer getMessageId() {
    return update.getMessage().getMessageId();
  }

}
