package com.prokopchuk.tgbotpersonalassistant.handler.impl.notes;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.ConfirmationButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.notes.NoteDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.SpecificNoteStateData;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.NotesNavigationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.notes.service.NoteService;
import com.prokopchuk.tgbotpersonalassistant.notes.util.NoteMessageFormatter;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnteredConfirmationOnNoteDeleteHandler extends AbstractUserRequestHandler {

  private static final String NOTE_WAS_DELETED_FORMAT = "Note with id: `%s` was successfully deleted";
  private final NoteService noteService;
  private final NotesNavigationKeyboardBuilder notesNavigationKeyboardBuilder;

  @Autowired
  public EnteredConfirmationOnNoteDeleteHandler(
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
    return request.isWaitingForConfirmationToDeleteNote();
  }

  @Override
  public void handle(UserRequestDto request) {
    Long chatId = request.getChatId();
    String input = request.getText();
    Integer messageId = request.getMessageId();
    SpecificNoteStateData stateData = userSessionService.getStateData(request.getSession());

    if (ConfirmationButtonText.isYes(input)) {
      handleYes(chatId, messageId, stateData);
    } else if (ConfirmationButtonText.isNo(input)) {
      handleNo(chatId, stateData);
    } else {
      throw new IllegalStateException("Illegal state on note deleting");
    }
  }

  private void handleYes(Long chatId, Integer messageId, SpecificNoteStateData stateData) {
    Long noteId = stateData.getNoteId();
    noteService.deleteNoteById(noteId);
    userSessionService.changeSessionStateWithStateDataReset(
        chatId,
        ConversationState.WAITING_FOR_FIRST_LEVEL_OPTION_FOR_NOTES
    );
    senderService.replyWithMarkdown(
        chatId,
        messageId,
        String.format(NOTE_WAS_DELETED_FORMAT, noteId),
        notesNavigationKeyboardBuilder.buildFirstLevelOptions()
    );
  }

  private void handleNo(Long chatId, SpecificNoteStateData stateData) {
    Long noteId = stateData.getNoteId();
    NoteDto note = noteService.getNoteById(noteId)
            .orElseThrow(() -> new IllegalStateException("Illegal state on handling 'no' option on deleting note"));

    userSessionService.changeState(chatId, ConversationState.WAITING_FOR_OPERATION_FOR_SPECIFIC_NOTE);
    senderService.sendMessageWithMarkdown(
        chatId,
        NoteMessageFormatter.formatSingleNoteFull(note),
        notesNavigationKeyboardBuilder.buildSpecificNoteOptions()
    );
  }

}
