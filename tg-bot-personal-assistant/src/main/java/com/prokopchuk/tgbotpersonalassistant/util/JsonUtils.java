package com.prokopchuk.tgbotpersonalassistant.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtils {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static String writeAsString(Object obj) {
    try {
      return OBJECT_MAPPER.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Unable write object as JSON string", e);
    }
  }

  public static <T> T read(String json, Class<T> cl) {
    try {
      return OBJECT_MAPPER.readValue(json, cl);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Unable read object from string", e);
    }
  }

}
