package com.prokopchuk.tgbotpersonalassistant.commons.dto.session;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.notification.AiGeneratedNotificationDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApproveGenNotificationsStateData {

  private List<AiGeneratedNotificationDto> generatedNotes;

  public static ApproveGenNotificationsStateData of(List<AiGeneratedNotificationDto> generatedNotes) {
    return new ApproveGenNotificationsStateData(generatedNotes);
  }

}
