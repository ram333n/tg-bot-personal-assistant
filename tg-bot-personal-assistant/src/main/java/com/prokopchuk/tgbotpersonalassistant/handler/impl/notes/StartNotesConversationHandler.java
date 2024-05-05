package com.prokopchuk.tgbotpersonalassistant.handler.impl.notes;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.StartButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.NotesNavigationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartNotesConversationHandler extends AbstractUserRequestHandler {

  private final NotesNavigationKeyboardBuilder notesNavigationKeyboardBuilder;

  @Autowired
  public StartNotesConversationHandler(
      UserSessionService userSessionService,
      SenderService senderService,
      StartConversationKeyboardBuilder startConversationKeyboardBuilder,
      NotesNavigationKeyboardBuilder notesNavigationKeyboardBuilder
  ) {
    super(userSessionService, senderService, startConversationKeyboardBuilder);
    this.notesNavigationKeyboardBuilder = notesNavigationKeyboardBuilder;
  }

  @Override
  public boolean isApplicable(UserRequestDto request) {
    return request.isStartState() && request.hasTextMessage(StartButtonText.NOTES.getText());
  }

  @Override
  public void handle(UserRequestDto request) {
    Long chatId = request.getChatId();
    userSessionService.changeState(chatId, ConversationState.WAITING_FOR_FIRST_LEVEL_OPTION_FOR_NOTES);
    senderService.sendMessage(chatId, "Select option:", notesNavigationKeyboardBuilder.buildFirstLevelOptions());
  }

}
