package com.prokopchuk.tgbotpersonalassistant.commons.dto.session;

import lombok.Data;

@Data
public class SaveNoteStateData {

  private Long id;
  private String title;
  private String content;

}
