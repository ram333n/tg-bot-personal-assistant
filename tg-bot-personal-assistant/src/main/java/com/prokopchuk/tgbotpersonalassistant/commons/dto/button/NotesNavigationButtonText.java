package com.prokopchuk.tgbotpersonalassistant.commons.dto.button;

import java.util.List;
import lombok.Getter;

@Getter
public enum NotesNavigationButtonText {

  GET_NOTES_PAGE("\uD83D\uDCD1 List notes"),
  CREATE_NOTE("✍ Create note"),
  BACK_TO_MAIN_MENU("Back to main menu");

//  OPEN_NOTE("\uD83D\uDDD2 Open note"),
//  UPDATE_NOTE("✍ Update note"),
//  DELETE_NOTE("\uD83D\uDDD1 Delete note");

  private final String text;

  NotesNavigationButtonText(String text) {
    this.text = text;
  }

  public static List<NotesNavigationButtonText> getFirstLevelOptions() {
    return List.of(GET_NOTES_PAGE, CREATE_NOTE, BACK_TO_MAIN_MENU);
  }

  public static boolean isGetNotesPage(String text) {
    return GET_NOTES_PAGE.getText().equals(text);
  }

  public static boolean isCreateNote(String text) {
    return CREATE_NOTE.getText().equals(text);
  }

  public static boolean isBackToMainMenu(String text) {
    return BACK_TO_MAIN_MENU.getText().equals(text);
  }

}
