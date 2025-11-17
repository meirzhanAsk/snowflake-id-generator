FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle gradle

RUN ./gradlew dependencies || return 0

COPY src src

RUN ./gradlew clean build -x test -x detekt -x ktlintCheck  \
    -x ktlintKotlinScriptCheck  \
    -x ktlintTestSourceSetCheck  \
    -x ktlintMainSourceSetCheck

FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

RUN useradd --system --create-home --uid 1001 appuser

COPY --chown=appuser:appuser --from=builder /app/build/libs/*.jar app.jar

USER appuser

ENTRYPOINT ["java", "-jar", "app.jar"]
