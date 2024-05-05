package com.prokopchuk.tgbotpersonalassistant.handler.impl.notes;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.SaveNoteStateData;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnteredTitleOnNoteCreationHandler extends AbstractUserRequestHandler {

  @Autowired
  public EnteredTitleOnNoteCreationHandler(
      UserSessionService userSessionService,
      SenderService senderService,
      StartConversationKeyboardBuilder startConversationKeyboardBuilder
  ) {
    super(userSessionService, senderService, startConversationKeyboardBuilder);
  }

  @Override
  public boolean isApplicable(UserRequestDto request) {
    return request.isWaitingForTitleToCreateNote();
  }

  @Override
  public void handle(UserRequestDto request) {
    String title = request.getText();
    SaveNoteStateData stateData = createStateData(title);
    senderService.replyWithMessageAndMarkdown(request.getChatId(), request.getMessageId(), "Enter content of note");
    userSessionService.changeSessionStateBySessionId(
        request.getSessionId(),
        ConversationState.WAITING_FOR_CONTENT_TO_CREATE_NOTE,
        stateData
    );
  }

  private SaveNoteStateData createStateData(String title) {
    SaveNoteStateData result = new SaveNoteStateData();
    result.setTitle(title);

    return result;
  }

}
