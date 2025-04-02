package com.prokopchuk.tgbotpersonalassistant.commons.dto.notification;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class AiGeneratedNotificationDto {

  private LocalDateTime schedulingTime;
  private String description;

}
