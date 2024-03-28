package com.prokopchuk.tgbotpersonalassistant.commons.dto;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.UserSessionDto;
import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.Update;

@Data
public class UserRequestDto {
  private final Update update;
  private final UserSessionDto session;
}
