package com.prokopchuk.tgbotpersonalassistant.qr.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.prokopchuk.tgbotpersonalassistant.qr.QrService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DefaultQrService implements QrService {

  private static final int DEFAULT_HEIGHT = 300;
  private static final int DEFAULT_WIDTH = 300;

  @Override
  public InputStream generateQrCode(String data) {
    try {
      log.info("Generating QR code for text: {}", data);

      String characterSet = "UTF-8";
      BitMatrix matrix = new MultiFormatWriter().encode(
          data,
          BarcodeFormat.QR_CODE,
          DEFAULT_WIDTH,
          DEFAULT_HEIGHT,
          Map.of(
              EncodeHintType.CHARACTER_SET, characterSet
          )
      );

      BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);

      return getInputStreamFromImage(image);
    } catch (WriterException e) {
      log.warn("Unable to generate QR code on input: {}. Cause: {}", data, e);
      throw new RuntimeException(e);
    }
  }

  private InputStream getInputStreamFromImage(BufferedImage image) {
    try {
      ByteArrayOutputStream os = new ByteArrayOutputStream();
      ImageIO.write(image, "jpeg", os);
      return new ByteArrayInputStream(os.toByteArray());
    } catch (IOException e) {
      log.warn("Unable to get InputStream from image. Cause: {}", e);
      throw new RuntimeException(e);
    }
  }

}
