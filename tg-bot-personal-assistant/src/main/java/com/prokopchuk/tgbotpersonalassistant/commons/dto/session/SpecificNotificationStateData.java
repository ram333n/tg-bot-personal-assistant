package com.prokopchuk.tgbotpersonalassistant.commons.dto.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificNotificationStateData {

  private Long notificationId;

  public static SpecificNotificationStateData of(Long notificationId) {
    return new SpecificNotificationStateData(notificationId);
  }

}
