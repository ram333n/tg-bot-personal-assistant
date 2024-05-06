package com.prokopchuk.tgbotpersonalassistant.handler.impl.notes;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.notes.NoteDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.SaveNoteStateData;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.SpecificNoteStateData;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.NotesNavigationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.notes.service.NoteService;
import com.prokopchuk.tgbotpersonalassistant.notes.util.NoteConstants;
import com.prokopchuk.tgbotpersonalassistant.notes.util.NoteMessageFormatter;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnteredContentOnNoteUpdateHandler extends AbstractUserRequestHandler {

  private static final String MAX_CONTENT_SIZE_REACHED_FORMAT
      = "The max length of content is %d characters. Please, reduce the content size to update note.";
  private static final String NOTE_SUCCESSFULLY_UPDATED_FORMAT = "The note was successfully updated\\. Updated note:\n\n%s";
  private final NoteService noteService;
  private final NotesNavigationKeyboardBuilder notesNavigationKeyboardBuilder;

  @Autowired
  public EnteredContentOnNoteUpdateHandler(
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
    return request.isWaitingForContentToUpdateNote();
  }

  @Override
  public void handle(UserRequestDto request) {
    Long chatId = request.getChatId();
    String content = request.getText();

    if (content.length() > NoteConstants.MAX_CONTENT_LENGTH) {
      senderService.reply(
          chatId,
          request.getMessageId(),
          String.format(MAX_CONTENT_SIZE_REACHED_FORMAT, NoteConstants.MAX_CONTENT_LENGTH)
      );

      return;
    }

    SaveNoteStateData saveNoteStateData = userSessionService.getStateData(request.getSession());
    saveNoteStateData.setContent(content);
    updateNote(chatId, saveNoteStateData);

    Long noteId = saveNoteStateData.getId();
    NoteDto updatedNote = noteService.getNoteById(noteId)
            .orElseThrow(() -> new IllegalStateException(String.format("Unable to find updated note. Id: %d", noteId)));

    userSessionService.changeSessionStateByChatId(
        chatId,
        ConversationState.WAITING_FOR_OPERATION_FOR_SPECIFIC_NOTE,
        SpecificNoteStateData.of(noteId)
    );
    senderService.sendMessageWithMarkdown(
        chatId,
        String.format(NOTE_SUCCESSFULLY_UPDATED_FORMAT, NoteMessageFormatter.formatSingleNoteFull(updatedNote)),
        notesNavigationKeyboardBuilder.buildSpecificNoteOptions()
    );
  }

  private void updateNote(Long chatId, SaveNoteStateData saveNoteStateData) {
    NoteDto note = new NoteDto();
    note.setId(saveNoteStateData.getId());
    note.setChatId(chatId);
    note.setTitle(saveNoteStateData.getTitle());
    note.setContent(saveNoteStateData.getContent());

    noteService.updateNote(note);
  }

}
