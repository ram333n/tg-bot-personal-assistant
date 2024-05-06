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
public class EnteredTitleOnNoteUpdateHandler extends AbstractUserRequestHandler {

  @Autowired
  public EnteredTitleOnNoteUpdateHandler(
      UserSessionService userSessionService,
      SenderService senderService,
      StartConversationKeyboardBuilder startConversationKeyboardBuilder
  ) {
    super(userSessionService, senderService, startConversationKeyboardBuilder);
  }

  @Override
  public boolean isApplicable(UserRequestDto request) {
    return request.isWaitingForTitleToUpdateNote();
  }

  @Override
  public void handle(UserRequestDto request) {
    Long chatId = request.getChatId();
    String title = request.getText();
    Integer messageId = request.getMessageId();
    SaveNoteStateData stateData = userSessionService.getStateData(request.getSession());

    stateData.setTitle(title);
    senderService.reply(chatId, messageId, "Enter new content of note");
    userSessionService.changeSessionStateBySessionId(
        request.getSessionId(),
        ConversationState.WAITING_FOR_CONTENT_TO_UPDATE_NOTE,
        stateData
    );
  }

}
