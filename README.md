# Лента постов на Spring MVC

Веб-приложение блога с возможностью просмотра и создания постов, построенное на Spring Boot 3 с использованием встроенного Tomcat.

## 📋 Технический стек

### Основные технологии
- **Backend**:
    - Spring Boot 3
    - Spring JDBC
    - Встроенный Tomcat 10
- **База данных**: H2 (in-memory)
- **Шаблонизация**: Thymeleaf 3
- **Тестирование**: JUnit 5, Mockito, Spring TestContext

### Системные требования
- Java 21
- Gradle 8.5+

## 🛠️ Сборка и запуск

### Сборка проекта
```bash
./gradlew clean build

Запуск из командной строки
java -jar build/libs/blog-app-0.0.1-SNAPSHOT.jar

Запуск из IntelliJ IDEA
Откройте класс Main в пакете org.bea
Нажмите ▶️ Run

Запуск всех тестов:
./gradlew test
````
### Доступ к приложению
После запуска приложение будет доступно по адресу:
http://localhost:8080
