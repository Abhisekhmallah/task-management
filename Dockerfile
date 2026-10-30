FROM eclipse-temurin:21-jdk AS build

WORKDIR /app

COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle

RUN chmod +x gradlew

COPY src ./src

RUN ./gradlew clean bootJar --no-daemon

FROM eclipse-temurin:21-jre

WORKDIR /app

RUN useradd --system --uid 10001 appuser

COPY --from=build /app/build/libs/task-management-0.0.1-SNAPSHOT.jar app.jar

USER 10001

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.jar"]