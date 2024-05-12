package com.prokopchuk.tgbotpersonalassistant.notes.util;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.notes.NoteDto;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NoteMessageFormatter {

  public static final int NOTES_PAGE_SIZE = 5;
  private static final String YOU_DO_NOT_HAVE_ANY_NOTES_MESSAGE
      = "You haven't any notes now \uD83D\uDE14";
  private static final String PAGES_COUNT_FORMAT = "Page: %d/%d";
  private static final String NOTE_FORMAT = """
  Id: `%d`
  \uD83D\uDDD2 Title: `%s`
  \uD83D\uDD52 Date created: _%s_
  \uD83D\uDD52 Date modified: _%s_
  """;

  private static final String SINGLE_NOTE_OPERATIONS_TIP
      = "Press button with specific description and id to operate with note\\(look, edit, delete\\)";
  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy\\-MM\\-dd HH:mm");

  private static final String NOTE_EXTENDED_FORMAT = """
  Id: `%d`
  \uD83D\uDDD2 Title: `%s`
  \uD83D\uDD52 Date created: _%s_
  \uD83D\uDD52 Date modified: _%s_
  
  Content:
  `%s`
  
  If you want to edit, delete or move back to notes list, press on the button with specific operation\\.
  """;

  public static String format(Page<NoteDto> notes) {
    if (notes.isEmpty()) {
      return YOU_DO_NOT_HAVE_ANY_NOTES_MESSAGE;
    }

    return formatPagesCount(notes)
        + "\n\n"
        + formatNotes(notes)
        + "\n\n"
        + SINGLE_NOTE_OPERATIONS_TIP;
  }

  private static String formatPagesCount(Page<NoteDto> notes) {
    return String.format(PAGES_COUNT_FORMAT, notes.getNumber() + 1, notes.getTotalPages());
  }

  private static String formatNotes(Page<NoteDto> notes) {
    StringJoiner result = new StringJoiner("\n");
    notes.forEach(n -> result.add(formatSingleNote(n)));

    return result.toString();
  }

  private static String formatSingleNote(NoteDto note) {
    return String.format(
        NOTE_FORMAT,
        note.getId(),
        note.getTitle(),
        note.getDateCreated().format(DATE_TIME_FORMATTER),
        note.isModified()
            ? note.getDateModified().format(DATE_TIME_FORMATTER)
            : "\\-"
    );
  }

  public static String formatSingleNoteFull(NoteDto note) {
    return String.format(
        NOTE_EXTENDED_FORMAT,
        note.getId(),
        note.getTitle(),
        note.getDateCreated().format(DATE_TIME_FORMATTER),
        note.isModified()
            ? note.getDateModified().format(DATE_TIME_FORMATTER)
            : "\\-",
        note.getContent()
    );
  }

}
