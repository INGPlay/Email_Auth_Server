FROM openjdk:11
COPY build/libs/*.jar app.jar
ARG PROFILE
ENV ENV_PROFILE=$PROFILE
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=${ENV_PROFILE}", "--java.net.preferIPv4Stack=true", "--log-driver", "local", "--restart-always"]