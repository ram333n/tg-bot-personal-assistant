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

  public boolean isStart() {
    return ConversationState.START.equals(session.getState());
  }

}
