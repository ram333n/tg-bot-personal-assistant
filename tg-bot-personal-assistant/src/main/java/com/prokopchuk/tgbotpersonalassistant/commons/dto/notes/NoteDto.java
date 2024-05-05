package com.prokopchuk.tgbotpersonalassistant.commons.dto.notes;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NoteDto {

  private Long id;
  private String title;
  private String content;
  private Long chatId;
  private LocalDateTime dateCreated;
  private LocalDateTime dateModified;

  public boolean isModified() {
    return !dateCreated.isEqual(dateModified);
  }

}
