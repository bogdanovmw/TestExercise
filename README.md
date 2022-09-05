# Тестовое задание для Инсайд

Dependency: 
- Spring Boot (WEB, JPA)
- Liquibase
- Jjwt
- Postgresql
- Lombok
- Junit 5 
- Mockito

Срок жизни ключа 3 минуты

В таблице есть 3 пользователя:
{name: "user1", password: "password1"}
{name: "user2", password: "password2"}
{name: "user3", password: "password3"}
Посмотреть список всех пользователей и сообщений можно по запросу: curl http://localhost:8080/api/users

Curl запросы:
(Проверены только в Windows 11)
### Заголовок 4 Запрос на авторизацию пользователя
curl -X POST http://localhost:8080/api/user -H "Content-Type: application/json" -d "{\"name\":\"user1\", \"password\":\"password1\"}"
curl -X POST http://localhost:8080/api/message -H "Content-Type: application/json" -H "Authorization:Bearer_" -d "{\"name\":\"user1\", \"message\":\"Hello World!\"}"
curl -X POST http://localhost:8080/api/message -H "Content-Type: application/json" -H "Authorization:Bearer_" -d "{\"name\":\"user1\", \"message\":\"history 10\"}"
