package com.prokopchuk.tgbotpersonalassistant.config.ai;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.ai.tool.annotation.Tool;

public class AiTools {

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

  @Tool(description = "Get the current date and time of user in format: `yyyy-MM-dd'T'HH:mm:ss`")
  String getCurrentDateTime() {
    return LocalDateTime.now().format(DATE_TIME_FORMATTER);
  }

}
