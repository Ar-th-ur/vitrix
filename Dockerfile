FROM eclipse-temurin:17-alpine
LABEL author="ARTHUR"
WORKDIR /app
COPY target/vitrix-spring-0.0.1.jar /app/backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar", "--spring.profiles.active=docker"]