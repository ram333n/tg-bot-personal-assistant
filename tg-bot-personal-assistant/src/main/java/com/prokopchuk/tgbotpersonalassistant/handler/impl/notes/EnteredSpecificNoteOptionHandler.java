package com.prokopchuk.tgbotpersonalassistant.handler.impl.notes;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.NotesNavigationButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.notes.NoteDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ListNotesStateData;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.SaveNoteStateData;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.SpecificNoteStateData;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.NotesNavigationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.notes.service.NoteService;
import com.prokopchuk.tgbotpersonalassistant.notes.util.NoteMessageFormatter;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class EnteredSpecificNoteOptionHandler extends AbstractUserRequestHandler {

  private final NoteService noteService;
  private final NotesNavigationKeyboardBuilder notesNavigationKeyboardBuilder;

  @Autowired
  public EnteredSpecificNoteOptionHandler(
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
    return request.isWaitingForOperationForSpecificNote();
  }

  @Override
  public void handle(UserRequestDto request) {
    Long chatId = request.getChatId();
    Integer messageId = request.getMessageId();
    String input = request.getText();
    SpecificNoteStateData stateData = userSessionService.getStateData(request.getSession());

    if (NotesNavigationButtonText.isUpdateNote(input)) {
      handleUpdateNote(chatId, messageId, stateData);
    } else if (NotesNavigationButtonText.isDeleteNote(input)) {
      //TODO: handle
    } else if (NotesNavigationButtonText.isBackToNotesPage(input)) {
      handleBackToNotesPage(chatId);
    } else {
      throw new IllegalStateException("Illegal state on handling request related to specific note");
    }
  }

  private void handleUpdateNote(Long chatId, Integer messageId, SpecificNoteStateData stateData) {
    Long noteId = stateData.getNoteId();
    userSessionService.changeSessionStateByChatId(
        chatId,
        ConversationState.WAITING_FOR_TITLE_TO_UPDATE_NOTE,
        new SaveNoteStateData(noteId)
    );
    senderService.replyAndRemoveKeyboard(chatId, messageId, "Enter new title for note");
  }

  private void handleBackToNotesPage(Long chatId) {
    Page<NoteDto> notesPage = noteService.getNotesFirstPageByChatId(chatId, NoteMessageFormatter.NOTES_PAGE_SIZE);
    userSessionService.changeSessionStateByChatId(
        chatId,
        ConversationState.WAITING_FOR_SECOND_LEVEL_OPTION_FOR_NOTES,
        new ListNotesStateData()
    );
    senderService.sendMessageWithMarkdown(
        chatId,
        NoteMessageFormatter.format(notesPage),
        notesNavigationKeyboardBuilder.buildNotesPage(notesPage)
    );
  }

}
