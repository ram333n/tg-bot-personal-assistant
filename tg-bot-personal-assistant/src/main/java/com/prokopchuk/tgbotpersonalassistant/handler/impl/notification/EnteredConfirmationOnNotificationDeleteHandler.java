package com.prokopchuk.tgbotpersonalassistant.handler.impl.notification;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.ConfirmationButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.notes.NoteDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.SpecificNoteStateData;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.SpecificNotificationStateData;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.NotesNavigationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.keyboard.NotificationsNavigationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.notes.service.NoteService;
import com.prokopchuk.tgbotpersonalassistant.notes.util.NoteMessageFormatter;
import com.prokopchuk.tgbotpersonalassistant.notification.service.NotificationService;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnteredConfirmationOnNotificationDeleteHandler extends AbstractUserRequestHandler {

  private static final String NOTIFICATION_WAS_DELETED_FORMAT = "Notification with id: `%s` was successfully deleted";
  private static final String DELETION_CANCELED_FORMAT = "Deletion of notification with id: `%s` was canceled";
  private final NotificationService notificationService;
  private final NotificationsNavigationKeyboardBuilder notificationsNavigationKeyboardBuilder;

  @Autowired
  public EnteredConfirmationOnNotificationDeleteHandler(
      UserSessionService userSessionService,
      SenderService senderService,
      StartConversationKeyboardBuilder startConversationKeyboardBuilder,
      NotificationService notificationService,
      NotificationsNavigationKeyboardBuilder notificationsNavigationKeyboardBuilder
  ) {
    super(userSessionService, senderService, startConversationKeyboardBuilder);
    this.notificationService = notificationService;
    this.notificationsNavigationKeyboardBuilder = notificationsNavigationKeyboardBuilder;
  }

  @Override
  public boolean isApplicable(UserRequestDto request) {
    return request.isWaitingForConfirmationToDeleteNotification();
  }

  @Override
  public void handle(UserRequestDto request) {
    Long chatId = request.getChatId();
    String input = request.getText();
    Integer messageId = request.getMessageId();
    SpecificNotificationStateData stateData = userSessionService.getStateData(request.getSession());

    if (ConfirmationButtonText.isYes(input)) {
      handleYes(chatId, messageId, stateData);
    } else if (ConfirmationButtonText.isNo(input)) {
      handleNo(chatId, messageId, stateData);
    } else {
      throw new IllegalStateException("Illegal state on note deleting");
    }
  }

  private void handleYes(Long chatId, Integer messageId, SpecificNotificationStateData stateData) {
    Long notificationId = stateData.getNotificationId();
    boolean existsNotification = notificationService.existsById(notificationId);

    if (!existsNotification) {
      userSessionService.changeState(chatId, ConversationState.WAITING_FOR_FIRST_LEVEL_OPTION_FOR_NOTIFICATIONS);
      senderService.reply(
          chatId,
          messageId,
          "Selected notification already sent or doesn't exist",
          notificationsNavigationKeyboardBuilder.buildFirstLevelOptions()
      );
      return;
    }

    notificationService.deleteNotificationById(notificationId);
    userSessionService.changeSessionStateWithStateDataReset(
        chatId,
        ConversationState.WAITING_FOR_FIRST_LEVEL_OPTION_FOR_NOTIFICATIONS
    );
    senderService.replyWithMarkdown(
        chatId,
        messageId,
        String.format(NOTIFICATION_WAS_DELETED_FORMAT, notificationId),
        notificationsNavigationKeyboardBuilder.buildFirstLevelOptions()
    );
  }

  private void handleNo(Long chatId, Integer messageId, SpecificNotificationStateData stateData) {
    Long notificationId = stateData.getNotificationId();

    userSessionService.changeSessionStateWithStateDataReset(
        chatId,
        ConversationState.WAITING_FOR_FIRST_LEVEL_OPTION_FOR_NOTIFICATIONS
    );
    senderService.replyWithMarkdown(
        chatId,
        messageId,
        String.format(DELETION_CANCELED_FORMAT, notificationId),
        notificationsNavigationKeyboardBuilder.buildFirstLevelOptions()
    );
  }

}
