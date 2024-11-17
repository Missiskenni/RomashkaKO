# Используем официальный образ OpenJDK
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файл сборки Maven
COPY target/RomashkaKO-1.0-SNAPSHOT.jar app.jar

# Указываем команду запуска
ENTRYPOINT ["java", "-jar", "app.jar"]