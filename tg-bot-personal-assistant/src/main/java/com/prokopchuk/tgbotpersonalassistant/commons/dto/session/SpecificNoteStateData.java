package com.prokopchuk.tgbotpersonalassistant.commons.dto.session;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificNoteStateData {

  private Long noteId;

  public static SpecificNoteStateData of(Long noteId) {
    return new SpecificNoteStateData(noteId);
  }

}
