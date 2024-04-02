package com.prokopchuk.tgbotpersonalassistant.commons.dto.session;

import lombok.Getter;

@Getter
public enum ConversationState {
  START,
  WAITING_FOR_TEXT,
  WAITING_FOR_LANGUAGE;

  private Class<?> stateDataClass;
}
