package com.prokopchuk.tgbotpersonalassistant.commons.dto.notification;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NotificationDto {

  private Long id;
  private Long chatId;
  private String description;
  private LocalDateTime schedulingTime;
  private LocalDateTime dateCreated;

}
