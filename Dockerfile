# Этап сборки
FROM openjdk:17-jdk-slim AS builder
WORKDIR /app

# Копируем файлы для загрузки зависимостей
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline -B

# Копируем исходники и собираем JAR
COPY src src
RUN ./mvnw package -DskipTests -B
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

# Финальный образ
FROM openjdk:17-slim AS runner
VOLUME /tmp

ARG DEPENDENCY=/app/target/dependency
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=builder ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app

EXPOSE 9005

ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.example.telescopeTypeDetailService.TelescopeTypeDetailServiceApplication"]