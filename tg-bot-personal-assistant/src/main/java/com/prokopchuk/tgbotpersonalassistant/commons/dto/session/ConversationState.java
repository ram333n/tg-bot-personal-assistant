package com.prokopchuk.tgbotpersonalassistant.commons.dto.session;

public enum ConversationState {
  WAITING_FOR_TEXT,
  WAITING_FOR_LANGUAGE;

  private Class<?> clazz;
}
