package com.prokopchuk.tgbotpersonalassistant.handler.impl.notification;

import com.prokopchuk.tgbotpersonalassistant.ai.AiService;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.notification.AiGeneratedNotificationDto;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ApproveGenNotificationsStateData;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.SpecificNotificationStateData;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.NotificationsNavigationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.notification.util.NotificationMessageFormatter;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class EnteredPromptToGenerateNotificationsHandler extends AbstractUserRequestHandler {

  private final AiService aiService;
  private final NotificationsNavigationKeyboardBuilder navigationKeyboardBuilder;

  public EnteredPromptToGenerateNotificationsHandler(
      UserSessionService userSessionService,
      SenderService senderService,
      StartConversationKeyboardBuilder startConversationKeyboardBuilder,
      AiService aiService,
      NotificationsNavigationKeyboardBuilder navigationKeyboardBuilder
  ) {
    super(userSessionService, senderService, startConversationKeyboardBuilder);
    this.aiService = aiService;
    this.navigationKeyboardBuilder = navigationKeyboardBuilder;
  }

  @Override
  public boolean isApplicable(UserRequestDto request) {
    return request.isWaitingForPromptToGenerateNotifications();
  }

  @Override
  public void handle(UserRequestDto request) {
    Long chatId = request.getChatId();
    Integer messageId = request.getMessageId();
    String prompt = request.getText();

    List<AiGeneratedNotificationDto> generatedNotifications = aiService.generateNotifications(prompt);

    if (generatedNotifications.isEmpty()) {
      handleEmptyGenResult(chatId, messageId);
      return;
    }

    String response = NotificationMessageFormatter.formatAiGeneratedNotes(generatedNotifications);

    userSessionService.changeSessionStateByChatId(
        chatId,
        ConversationState.WAITING_FOR_GENERATED_NOTIFICATIONS_APPROVAL,
        ApproveGenNotificationsStateData.of(generatedNotifications)
    );
    senderService.replyWithMarkdown(
        chatId,
        messageId,
        response,
        navigationKeyboardBuilder.buildConfirmationButtons()
    );
  }

  private void handleEmptyGenResult(Long chatId, Integer messageId) {
    senderService.reply(
        chatId,
        messageId,
        "Notifications aren't generated :(. Try again later"
    );
    moveToStartState(chatId);
  }

}
