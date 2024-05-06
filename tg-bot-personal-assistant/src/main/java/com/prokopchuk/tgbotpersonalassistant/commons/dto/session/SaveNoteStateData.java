package com.prokopchuk.tgbotpersonalassistant.commons.dto.session;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SaveNoteStateData {

  private Long id;
  private String title;
  private String content;

  public SaveNoteStateData(Long id) {
    this.id = id;
  }

}
