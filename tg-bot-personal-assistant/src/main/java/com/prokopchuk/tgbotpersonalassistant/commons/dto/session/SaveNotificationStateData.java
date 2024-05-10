package com.prokopchuk.tgbotpersonalassistant.commons.dto.session;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SaveNotificationStateData {

  private String description;
  private LocalDateTime scheduleTime;

}
