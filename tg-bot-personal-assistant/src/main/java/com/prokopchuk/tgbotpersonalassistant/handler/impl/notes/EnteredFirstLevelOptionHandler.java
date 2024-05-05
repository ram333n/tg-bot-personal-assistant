package com.prokopchuk.tgbotpersonalassistant.handler.impl.notes;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.NavigationButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.NotesNavigationButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.notes.NoteDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ListNotesStateData;
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
public class EnteredFirstLevelOptionHandler extends AbstractUserRequestHandler {

  private final NoteService noteService;
  private final NotesNavigationKeyboardBuilder notesNavigationKeyboardBuilder;

  @Autowired
  public EnteredFirstLevelOptionHandler(
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
    return request.isWaitingForFirstLevelOptionForNotes();
  }

  @Override
  public void handle(UserRequestDto request) {
    Long chatId = request.getChatId();
    String input = request.getText();
    Integer messageId = request.getMessageId();

    if (NotesNavigationButtonText.isCreateNote(input)) {
      userSessionService.changeState(chatId, ConversationState.WAITING_FOR_TITLE_TO_CREATE_NOTE);
      senderService.replyAndRemoveKeyboard(chatId, messageId, "Enter title for new note");
    } else if (NotesNavigationButtonText.isGetNotesPage(input)) {
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
    } else if (NotesNavigationButtonText.isBackToMainMenu(input)) {
      moveToStartState(chatId);
    } else {
      throw new IllegalStateException("Illegal state for on entering first level option for notes");
    }
  }

}
