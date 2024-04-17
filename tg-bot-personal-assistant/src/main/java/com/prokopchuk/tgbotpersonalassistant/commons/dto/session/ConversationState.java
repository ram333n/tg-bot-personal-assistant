package com.prokopchuk.tgbotpersonalassistant.commons.dto.session;

import java.util.EnumSet;
import lombok.Getter;

@Getter
public enum ConversationState {

  START(MockStateData.class),
  WAITING_FOR_TEXT_TO_TRANSLATE(MockStateData.class),
  WAITING_FOR_LANGUAGE_TO_TRANSLATE(TranslateStateData.class);

  private static final EnumSet<ConversationState> TRANSLATION_STATES = EnumSet.of(
      START,
      WAITING_FOR_TEXT_TO_TRANSLATE,
      WAITING_FOR_LANGUAGE_TO_TRANSLATE
  );

  private Class<?> stateDataClass;

  ConversationState(Class<?> stateDataClass) {
    this.stateDataClass = stateDataClass;
  }

  public boolean isTranslationState() {
    return TRANSLATION_STATES.contains(this);
  }

}
