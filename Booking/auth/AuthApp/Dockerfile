# Используем официальный образ Gradle с JDK 17 для сборки
FROM gradle:jdk21 as builder

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем файлы сборки и исходный код в контейнер
COPY build.gradle settings.gradle ./
COPY src ./src
COPY gradlew gradlew.bat ./
COPY gradle ./gradle

# Делаем gradlew исполняемым
RUN chmod +x gradlew

# Выполняем сборку проекта
RUN gradle build --no-daemon

# Используем официальный образ OpenJDK для запуска Spring Boot приложения
FROM openjdk:21-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем скомпилированный jar файл из предыдущего этапа
COPY --from=builder /app/build/libs/*.jar app.jar

# Указываем порт, который будет использован приложением
EXPOSE 8080

# Указываем команду для запуска Spring Boot приложения
ENTRYPOINT ["java", "-jar", "app.jar"]