package com.prokopchuk.tgbotpersonalassistant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TgBotPersonalAssistantApplication {

  public static void main(String[] args) {
    final String host = System.getenv("current_env");
    final EnvType envType = EnvType.getEnvByHost(host);

    new SpringApplicationBuilder(TgBotPersonalAssistantApplication.class)
        .profiles(EnvType.DEFAULT.getProfile(), envType.getProfile())
        .build()
        .run(args);
  }

  @Getter
  private enum EnvType {

    DEFAULT("default", "default"),
    DEV("dev", "dev"),
    PROD("prod", "prod");

    private static final Map<String, EnvType> ENVS_BY_HOST = new HashMap<>();

    static {
      Arrays.stream(values())
          .forEach(v -> ENVS_BY_HOST.put(v.hostName, v));
    }

    private final String hostName;
    private final String profile;

    EnvType(String hostName, String profile) {
      this.hostName = hostName;
      this.profile = profile;
    }

    public static EnvType getEnvByHost(String host) {
      return ENVS_BY_HOST.getOrDefault(host, DEV);
    }

  }

}
