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

  public boolean isWaitingForFirstLevelOptionForNotes() {
    return ConversationState.WAITING_FOR_FIRST_LEVEL_OPTION_FOR_NOTES.equals(session.getState());
  }

  public boolean isWaitingForTitleToCreateNote() {
    return ConversationState.WAITING_FOR_TITLE_TO_CREATE_NOTE.equals(session.getState());
  }

  public boolean isWaitingForContentToCreateNote() {
    return ConversationState.WAITING_FOR_CONTENT_TO_CREATE_NOTE.equals(session.getState());
  }

  public boolean isWaitingForSecondLevelOptionForNotes() {
    return ConversationState.WAITING_FOR_SECOND_LEVEL_OPTION_FOR_NOTES.equals(session.getState());
  }

  public boolean isWaitingForOperationForSpecificNote() {
    return ConversationState.WAITING_FOR_OPERATION_FOR_SPECIFIC_NOTE.equals(session.getState());
  }

  public boolean isWaitingForTitleToUpdateNote() {
    return ConversationState.WAITING_FOR_TITLE_TO_UPDATE_NOTE.equals(session.getState());
  }

  public boolean isWaitingForContentToUpdateNote() {
    return ConversationState.WAITING_FOR_CONTENT_TO_UPDATE_NOTE.equals(session.getState());
  }

  public boolean isWaitingForConfirmationToDeleteNote() {
    return ConversationState.WAITING_FOR_CONFIRMATION_TO_DELETE_NOTE.equals(session.getState());
  }

}
