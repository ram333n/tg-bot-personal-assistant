FROM eclipse-temurin:17-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} tg-bot-personal-assistant.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "tg-bot-personal-assistant.jar"]
