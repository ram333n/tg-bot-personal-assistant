package com.prokopchuk.tgbotpersonalassistant.commons.dto.session;

import lombok.Data;

@Data
public class ListNotesStateData {

  private int page = 0;

  public void moveToNextPage() {
    page++;
  }

  public void moveToPreviousPage() {
    if (page > 0) {
      page--;
    }
  }

}
