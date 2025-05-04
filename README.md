# Лента постов на Spring MVC

Веб-приложение блога с возможностью просмотра и создания постов, построенное на Spring MVC 6 с использованием встроенного Tomcat.

## 📋 Технический стек

### Основные технологии
- **Backend**:
    - Spring MVC 6
    - Spring JDBC
    - Встроенный Tomcat 10
- **База данных**: H2 (in-memory)
- **Шаблонизация**: Thymeleaf 3
- **Тестирование**: JUnit 5, Mockito, Spring TestContext

### Системные требования
- Java 21
- Maven 3.8+

## 🛠️ Сборка и запуск

### Сборка проекта
```bash
mvn clean package
Запуск из командной строки
java -jar target/spring-web-demo.jar

Запуск из IntelliJ IDEA
Откройте класс Main в пакете org.bea
Нажмите ▶️ Run

Настройки Tomcat
server.port=8082

Тестирование
Запуск всех тестов:

mvn test
````
### Доступ к приложению
После запуска приложение будет доступно по адресу:
http://localhost:8082
