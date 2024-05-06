package com.prokopchuk.tgbotpersonalassistant.keyboard;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.ConfirmationButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.NavigationButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.NotesNavigationButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.notes.NoteDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

@Component
public class NotesNavigationKeyboardBuilder {

  private static final String NOTE_BUTTON_TEXT_FORMAT = "%s (%d)";

  public ReplyKeyboardMarkup buildFirstLevelOptions() {
    List<KeyboardRow> rows = new ArrayList<>();

    for (NotesNavigationButtonText item : NotesNavigationButtonText.getFirstLevelOptions()) {
      KeyboardRow currentRow = new KeyboardRow();
      currentRow.add(item.getText());
      rows.add(currentRow);
    }

    return ReplyKeyboardMarkup.builder()
        .keyboard(rows)
        .selective(true)
        .resizeKeyboard(true)
        .oneTimeKeyboard(false)
        .build();
  }

  public ReplyKeyboardMarkup buildNotesPage(Page<NoteDto> page) {
    List<KeyboardRow> rows = new ArrayList<>();

    for (NoteDto note : page) {
      rows.add(buildRowForSingleNote(note));
    }

    KeyboardRow navigation = page.isEmpty()
        ? buildBackToPreviousMenuButton()
        : buildNavigationButtons();

    rows.add(navigation);

    return ReplyKeyboardMarkup.builder()
        .keyboard(rows)
        .selective(true)
        .resizeKeyboard(true)
        .oneTimeKeyboard(false)
        .build();
  }

  private KeyboardRow buildRowForSingleNote(NoteDto note) {
    KeyboardRow result = new KeyboardRow();
    result.add(String.format(NOTE_BUTTON_TEXT_FORMAT, note.getTitle(), note.getId()));

    return result;
  }

  private KeyboardRow buildBackToPreviousMenuButton() {
    KeyboardRow result = new KeyboardRow();
    result.add(NavigationButtonText.BACK_TO_PREVIOUS_MENU.getText());

    return result;
  }

  private KeyboardRow buildNavigationButtons() {
    KeyboardRow result = new KeyboardRow();
    result.add(NavigationButtonText.BACK.getText());
    result.add(NavigationButtonText.BACK_TO_PREVIOUS_MENU.getText());
    result.add(NavigationButtonText.FORWARD.getText());

    return result;
  }

  public ReplyKeyboardMarkup buildSpecificNoteOptions() {
    List<KeyboardRow> rows = new ArrayList<>();

    for (NotesNavigationButtonText item : NotesNavigationButtonText.getSpecificNoteOptions()) {
      KeyboardRow currentRow = new KeyboardRow();
      currentRow.add(item.getText());
      rows.add(currentRow);
    }

    return ReplyKeyboardMarkup.builder()
        .keyboard(rows)
        .selective(true)
        .resizeKeyboard(true)
        .oneTimeKeyboard(false)
        .build();
  }

  public ReplyKeyboardMarkup buildConfirmationButtons() {
    List<KeyboardRow> rows = new ArrayList<>();

    for (ConfirmationButtonText item : ConfirmationButtonText.values()) {
      KeyboardRow currentRow = new KeyboardRow();
      currentRow.add(item.getText());
      rows.add(currentRow);
    }

    return ReplyKeyboardMarkup.builder()
        .keyboard(rows)
        .selective(true)
        .resizeKeyboard(true)
        .oneTimeKeyboard(false)
        .build();
  }

}
