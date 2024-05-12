package com.prokopchuk.tgbotpersonalassistant.handler.impl.notification;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.button.NavigationButtonText;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.notes.NoteDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.notification.NotificationDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ListNotificationsStateData;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.SpecificNotificationStateData;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.NotificationsNavigationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.notes.util.NoteMessageFormatter;
import com.prokopchuk.tgbotpersonalassistant.notification.service.NotificationService;
import com.prokopchuk.tgbotpersonalassistant.notification.util.NotificationMessageFormatter;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class EnteredSecondLevelNotificationOptionHandler extends AbstractUserRequestHandler {

  private final NotificationService notificationService;
  private final NotificationsNavigationKeyboardBuilder notificationsNavigationKeyboardBuilder;

  @Autowired
  public EnteredSecondLevelNotificationOptionHandler(
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
    return request.isWaitingForSecondLevelOptionForNotifications();
  }

  @Override
  public void handle(UserRequestDto request) {
    Long chatId = request.getChatId();
    String input = request.getText();
    Integer messageId = request.getMessageId();
    ListNotificationsStateData paginationData = userSessionService.getStateData(request.getSession());

    if (NavigationButtonText.isBack(input)) {
      handleMoveBack(chatId, paginationData);
    } else if (NavigationButtonText.isForward(input)) {
      handleMoveForward(chatId, paginationData);
    } else if (NavigationButtonText.isBackToPreviousMenu(input)) {
      handleBackToPreviousMenu(chatId);
    } else {
      handleSpecificNoteButton(chatId, messageId, input);
    }
  }

  private void handleMoveBack(Long chatId, ListNotificationsStateData paginationData) {
    if (paginationData.getPage() == 0) {
      senderService.sendMessage(chatId, "You can't move back, because you are on the first page of notifications!");
      return;
    }

    paginationData.moveToPreviousPage();
    Page<NotificationDto> notes = notificationService.getNotificationsByChatId(
        chatId,
        paginationData.getPage(),
        NotificationMessageFormatter.NOTIFICATIONS_PAGE_SIZE
    );

    userSessionService.changeStateDataByChatId(chatId, paginationData);
    senderService.sendMessageWithMarkdown(
        chatId,
        NotificationMessageFormatter.format(notes),
        notificationsNavigationKeyboardBuilder.buildNotificationsPage(notes)
    );
  }

  private void handleMoveForward(Long chatId, ListNotificationsStateData paginationData) {
    boolean isLastPage = notificationService.isLastPage(
        chatId,
        paginationData.getPage(),
        NotificationMessageFormatter.NOTIFICATIONS_PAGE_SIZE
    );

    if (isLastPage) {
      senderService.sendMessage(chatId, "You can't move forward, because you are on the last page of notifications!");
      return;
    }

    paginationData.moveToNextPage();
    Page<NotificationDto> notes = notificationService.getNotificationsByChatId(
        chatId,
        paginationData.getPage(),
        NotificationMessageFormatter.NOTIFICATIONS_PAGE_SIZE
    );

    userSessionService.changeStateDataByChatId(chatId, paginationData);
    senderService.sendMessageWithMarkdown(
        chatId,
        NotificationMessageFormatter.format(notes),
        notificationsNavigationKeyboardBuilder.buildNotificationsPage(notes)
    );
  }

  private void handleBackToPreviousMenu(Long chatId) {
    userSessionService.changeSessionStateWithStateDataReset(
        chatId,
        ConversationState.WAITING_FOR_FIRST_LEVEL_OPTION_FOR_NOTIFICATIONS
    );
    senderService.sendMessage(
        chatId,
        "Previous menu:",
        notificationsNavigationKeyboardBuilder.buildFirstLevelOptions()
    );
  }

  private void handleSpecificNoteButton(Long chatId, Integer messageId, String input) {
    Long notificationId = Long.valueOf(input);
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

    userSessionService.changeSessionStateByChatId(
        chatId,
        ConversationState.WAITING_FOR_CONFIRMATION_TO_DELETE_NOTIFICATION,
        SpecificNotificationStateData.of(notificationId)
    );
    senderService.reply(
        chatId,
        messageId,
        "Are you sure you want to delete this notification?",
        notificationsNavigationKeyboardBuilder.buildConfirmationButtons()
    );
  }

}
