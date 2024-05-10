package com.prokopchuk.tgbotpersonalassistant.handler.impl.notes;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.NavigationButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.notes.NoteDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ListNotesStateData;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.SpecificNoteStateData;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.NotesNavigationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.notes.service.NoteService;
import com.prokopchuk.tgbotpersonalassistant.notes.util.NoteMessageFormatter;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class EnteredSecondLevelNoteOptionHandler extends AbstractUserRequestHandler {

  private static final Pattern NOTE_BUTTON_TEXT_PATTERN = Pattern.compile("(?<noteTitle>.*) \\((?<id>\\d+)\\)");
  private final NoteService noteService;
  private final NotesNavigationKeyboardBuilder notesNavigationKeyboardBuilder;

  @Autowired
  public EnteredSecondLevelNoteOptionHandler(
      UserSessionService userSessionService,
      SenderService senderService,
      StartConversationKeyboardBuilder startConversationKeyboardBuilder,
      NoteService noteService,
      NotesNavigationKeyboardBuilder notesNavigationKeyboardBuilder
  ) {
    super(userSessionService, senderService, startConversationKeyboardBuilder);
    this.noteService = noteService;
    this.notesNavigationKeyboardBuilder = notesNavigationKeyboardBuilder;
  }

  @Override
  public boolean isApplicable(UserRequestDto request) {
    return request.isWaitingForSecondLevelOptionForNotes();
  }

  @Override
  public void handle(UserRequestDto request) {
    Long chatId = request.getChatId();
    String input = request.getText();
    ListNotesStateData paginationData = userSessionService.getStateData(request.getSession());

    if (NavigationButtonText.isBack(input)) {
      handleMoveBack(chatId, paginationData);
    } else if (NavigationButtonText.isForward(input)) {
      handleMoveForward(chatId, paginationData);
    } else if (NavigationButtonText.isBackToPreviousMenu(input)) {
      handleBackToPreviousMenu(chatId);
    } else {
      handleSpecificNoteButton(chatId, input);
    }
  }

  private void handleMoveBack(Long chatId, ListNotesStateData paginationData) {
    if (paginationData.getPage() == 0) {
      senderService.sendMessage(chatId, "You can't move back, because you are on the first page of available notes!");
      return;
    }

    paginationData.moveToPreviousPage();
    Page<NoteDto> notes = noteService.getNotesByChatId(
        chatId,
        paginationData.getPage(),
        NoteMessageFormatter.NOTES_PAGE_SIZE
    );

    userSessionService.changeStateDataByChatId(chatId, paginationData);
    senderService.sendMessageWithMarkdown(
        chatId,
        NoteMessageFormatter.format(notes),
        notesNavigationKeyboardBuilder.buildNotesPage(notes)
    );
  }

  private void handleMoveForward(Long chatId, ListNotesStateData paginationData) {
    boolean isLastPage = noteService.isLastPage(chatId, paginationData.getPage(), NoteMessageFormatter.NOTES_PAGE_SIZE);

    if (isLastPage) {
      senderService.sendMessage(chatId, "You can't move back, because you are on the last page of available notes!");
      return;
    }

    paginationData.moveToNextPage();
    Page<NoteDto> notes = noteService.getNotesByChatId(chatId, paginationData.getPage(), NoteMessageFormatter.NOTES_PAGE_SIZE);
    userSessionService.changeStateDataByChatId(chatId, paginationData);
    senderService.sendMessageWithMarkdown(
        chatId,
        NoteMessageFormatter.format(notes),
        notesNavigationKeyboardBuilder.buildNotesPage(notes)
    );
  }

  private void handleBackToPreviousMenu(Long chatId) {
    userSessionService.changeSessionStateWithStateDataReset(chatId, ConversationState.WAITING_FOR_FIRST_LEVEL_OPTION_FOR_NOTES);
    senderService.sendMessage(
        chatId,
        "Previous menu:",
        notesNavigationKeyboardBuilder.buildFirstLevelOptions()
    );
  }

  private void handleSpecificNoteButton(Long chatId, String input) {
    Matcher matcher = NOTE_BUTTON_TEXT_PATTERN.matcher(input);

    if (!matcher.matches()) {
      throw new IllegalStateException("Illegal state for on entering second level option for notes");
    }

    Long noteId = Long.valueOf(matcher.group("id"));
    NoteDto note = noteService.getNoteById(noteId)
            .orElseThrow(() -> new IllegalStateException(String.format("Unable to find note with id: %d", noteId)));

    userSessionService.changeSessionStateByChatId(
        chatId,
        ConversationState.WAITING_FOR_OPERATION_FOR_SPECIFIC_NOTE,
        SpecificNoteStateData.of(noteId)
    );
    senderService.sendMessageWithMarkdown(
        chatId,
        NoteMessageFormatter.formatSingleNoteFull(note),
        notesNavigationKeyboardBuilder.buildSpecificNoteOptions()
    );
  }

}
