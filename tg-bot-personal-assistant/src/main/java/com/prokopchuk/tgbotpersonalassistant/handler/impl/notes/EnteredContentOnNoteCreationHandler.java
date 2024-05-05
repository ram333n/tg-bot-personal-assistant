package com.prokopchuk.tgbotpersonalassistant.handler.impl.notes;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.notes.NoteDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.SaveNoteStateData;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.NotesNavigationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.notes.service.NoteService;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnteredContentOnNoteCreationHandler extends AbstractUserRequestHandler {

  private static final int MAX_CONTENT_LENGTH = 2000;
  private static final String NOTE_SUCCESSFULLY_CREATED_FORMAT = "The note was successfully created\\. New note id: `%d`";
  private final NoteService noteService;
  private final NotesNavigationKeyboardBuilder notesNavigationKeyboardBuilder;

  @Autowired
  public EnteredContentOnNoteCreationHandler(
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
    return request.isWaitingForContentToCreateNote();
  }

  @Override
  public void handle(UserRequestDto request) {
    Long chatId = request.getChatId();
    String content = request.getText();

    if (content.length() > MAX_CONTENT_LENGTH) {
      senderService.reply(
          chatId,
          request.getMessageId(),
          String.format("The max length of content is %d characters. Please, reduce the content size to save note.", MAX_CONTENT_LENGTH)
      );

      return;
    }

    SaveNoteStateData saveNoteStateData = userSessionService.getStateData(request.getSession());

    Long noteId = saveNote(chatId, saveNoteStateData.getTitle(), content);
    userSessionService.changeSessionStateWithStateDataReset(chatId, ConversationState.WAITING_FOR_FIRST_LEVEL_OPTION_FOR_NOTES);
    senderService.sendMessageWithMarkdown(
        chatId,
        String.format(NOTE_SUCCESSFULLY_CREATED_FORMAT, noteId),
        notesNavigationKeyboardBuilder.buildFirstLevelOptions()
    );
  }

  private Long saveNote(Long chatId, String title, String content) {
    NoteDto note = new NoteDto();
    note.setChatId(chatId);
    note.setTitle(title);
    note.setContent(content);

    return noteService.createNote(note);
  }

}
