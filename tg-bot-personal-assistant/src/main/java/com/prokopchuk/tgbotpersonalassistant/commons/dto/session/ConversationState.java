package com.prokopchuk.tgbotpersonalassistant.commons.dto.session;

import lombok.Getter;

@Getter
public enum ConversationState {

  START(MockStateData.class),
  WAITING_FOR_TEXT_TO_TRANSLATE(MockStateData.class),
  WAITING_FOR_LANGUAGE_TO_TRANSLATE(TranslateStateData.class),
  WAITING_FOR_TEXT_TO_GENERATE_QR(MockStateData.class),
  WAITING_FOR_FIRST_LEVEL_OPTION_FOR_NOTES(MockStateData.class),
  WAITING_FOR_TITLE_TO_CREATE_NOTE(MockStateData.class),
  WAITING_FOR_CONTENT_TO_CREATE_NOTE(SaveNoteStateData.class),
  WAITING_FOR_SECOND_LEVEL_OPTION_FOR_NOTES(ListNotesStateData.class),
  WAITING_FOR_OPERATION_FOR_SPECIFIC_NOTE(SpecificNoteStateData.class),
  WAITING_FOR_TITLE_TO_UPDATE_NOTE(SaveNoteStateData.class),
  WAITING_FOR_CONTENT_TO_UPDATE_NOTE(SaveNoteStateData.class),
  WAITING_FOR_CONFIRMATION_TO_DELETE_NOTE(SpecificNoteStateData.class),
  WAITING_FOR_FIRST_LEVEL_OPTION_FOR_NOTIFICATIONS(MockStateData.class),
  WAITING_FOR_DESCRIPTION_TO_SCHEDULE_NOTIFICATION(MockStateData.class),
  WAITING_FOR_TIME_TO_SCHEDULE_NOTIFICATION(SaveNotificationStateData.class),
  WAITING_FOR_SECOND_LEVEL_OPTION_FOR_NOTIFICATIONS(ListNotificationsStateData.class),
  WAITING_FOR_CONFIRMATION_TO_DELETE_NOTIFICATION(SpecificNotificationStateData.class),
  WAITING_FOR_SETTLEMENT_FOR_WEATHER(MockStateData.class),
  WAITING_FOR_PROMPT_TO_GENERATE_NOTIFICATIONS(MockStateData.class),
  WAITING_FOR_GENERATED_NOTIFICATIONS_APPROVAL(ApproveGenNotificationsStateData.class);

  private final Class<?> stateDataClass;

  ConversationState(Class<?> stateDataClass) {
    this.stateDataClass = stateDataClass;
  }

}
