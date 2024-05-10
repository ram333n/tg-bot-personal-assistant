package com.prokopchuk.tgbotpersonalassistant.handler.impl.notification;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.SaveNotificationStateData;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnteredDescriptionOnNotificationScheduleHandler extends AbstractUserRequestHandler {

  private static final String ENTER_SCHEDULE_TIME_TIP = """
  Enter schedule time\\. Supported formats:
  
  _2024\\-05\\-10 14\\:00_ \\- Schedule for May 10, 2024 at 14\\:00
  _01\\:30_ \\- Schedule for today at 01\\:30
  """;

  @Autowired
  public EnteredDescriptionOnNotificationScheduleHandler(
      UserSessionService userSessionService,
      SenderService senderService,
      StartConversationKeyboardBuilder startConversationKeyboardBuilder
  ) {
    super(userSessionService, senderService, startConversationKeyboardBuilder);
  }

  @Override
  public boolean isApplicable(UserRequestDto request) {
    return request.isWaitingForDescriptionToScheduleNotification();
  }

  @Override
  public void handle(UserRequestDto request) {
    String description = request.getText();
    SaveNotificationStateData stateData = createStateData(description);
    senderService.replyWithMessageAndMarkdown(request.getChatId(), request.getMessageId(), ENTER_SCHEDULE_TIME_TIP);
    userSessionService.changeSessionStateBySessionId(
        request.getSessionId(),
        ConversationState.WAITING_FOR_TIME_TO_SCHEDULE_NOTIFICATION,
        stateData
    );
  }

  private SaveNotificationStateData createStateData(String description) {
    SaveNotificationStateData stateData = new SaveNotificationStateData();
    stateData.setDescription(description);

    return stateData;
  }

}
