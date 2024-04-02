package com.prokopchuk.tgbotpersonalassistant.commons.dto.session;

import lombok.Data;

@Data
public class UserSessionDto {

  private Long id;
  private Long userId;
  private Long chatId;
  private ConversationState state;
  private String stateData;

  public static UserSessionDto start(Long chatId) {
    UserSessionDto result = new UserSessionDto();
    result.setChatId(chatId);
    result.setState(ConversationState.START);

    return result;
  }

}
