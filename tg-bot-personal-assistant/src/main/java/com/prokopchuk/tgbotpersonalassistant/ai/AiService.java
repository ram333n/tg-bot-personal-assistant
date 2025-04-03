package com.prokopchuk.tgbotpersonalassistant.ai;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.notification.AiGeneratedNotificationDto;
import java.util.List;

public interface AiService {

  List<AiGeneratedNotificationDto> generateNotifications(String prompt);

}
