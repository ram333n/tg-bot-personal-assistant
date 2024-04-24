package com.prokopchuk.tgbotpersonalassistant.qr;

import java.io.InputStream;

public interface QrService {

  InputStream generateQrCode(String data);

}
