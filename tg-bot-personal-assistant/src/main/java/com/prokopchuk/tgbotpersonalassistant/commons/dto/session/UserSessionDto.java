package com.prokopchuk.tgbotpersonalassistant.commons.dto.session;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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

  public static UserSessionDto waitingForLanguageToTranslate(Long chatId, String stateData) {
    return new UserSessionDto(chatId, ConversationState.WAITING_FOR_TEXT_TO_TRANSLATE, stateData);
  }

  public UserSessionDto(Long chatId, ConversationState state, String stateData) {
    this.chatId = chatId;
    this.state = state;
    this.stateData = stateData;
  }

}
