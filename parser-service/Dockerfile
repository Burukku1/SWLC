FROM eclipse-temurin:19-jre-focal
# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем JAR-файл внутрь контейнера
COPY target/parser-service-0.0.1-SNAPSHOT.jar parse-service.jar

# Определяем команду для запуска приложения при старте контейнера
CMD ["java", "-jar", "user-service.jar"]
