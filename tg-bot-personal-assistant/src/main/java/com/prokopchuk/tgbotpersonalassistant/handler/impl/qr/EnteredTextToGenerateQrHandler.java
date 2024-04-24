package com.prokopchuk.tgbotpersonalassistant.handler.impl.qr;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.UserRequestDto;
import com.prokopchuk.tgbotpersonalassistant.handler.impl.AbstractUserRequestHandler;
import com.prokopchuk.tgbotpersonalassistant.keyboard.StartConversationKeyboardBuilder;
import com.prokopchuk.tgbotpersonalassistant.qr.QrService;
import com.prokopchuk.tgbotpersonalassistant.sender.SenderService;
import com.prokopchuk.tgbotpersonalassistant.session.service.UserSessionService;
import java.io.InputStream;
import org.springframework.stereotype.Component;

@Component
public class EnteredTextToGenerateQrHandler extends AbstractUserRequestHandler {

  private final QrService qrService;

  public EnteredTextToGenerateQrHandler(
      UserSessionService userSessionService,
      SenderService senderService,
      StartConversationKeyboardBuilder startConversationKeyboardBuilder,
      QrService qrService
  ) {
    super(userSessionService, senderService, startConversationKeyboardBuilder);
    this.qrService = qrService;
  }

  @Override
  public boolean isApplicable(UserRequestDto request) {
    return request.isWaitingForTextToGenerateQr();
  }

  @Override
  public void handle(UserRequestDto request) {
    Long chatId = request.getChatId();
    Integer messageId = request.getMessageId();
    String data = request.getText();
    InputStream image = qrService.generateQrCode(data);
    senderService.replyWithImage(chatId, messageId, image, "Your QR code:");
    moveToStartState(chatId);
  }

}
