# Тестовое задание для Инсайд

Технологии: 
- Spring Boot (WEB, JPA)
- Liquibase
- Jjwt
- Postgresql
- Lombok
- Junit 5 
- Mockito

## Установка и запуск программы  
1. В операционной системе должен быть установлен и запущен Docker
2. Необходимо скачать и разархивировать архив [по ссылке](https://drive.google.com/file/d/10W2DncYEag8jJ8vLV-myQnObAtFhE63d/view?usp=sharing)  
3. Перейти в разархивированную папку DockerExercise 
4. Запустить start.bat
5. Проект запущен по [адресу](http://localhost:8080/)  
  
### Данные в таблице:  
В таблице есть 3 пользователя:
- {name: "user1", password: "password1"}
- {name: "user2", password: "password2"}
- {name: "user3", password: "password3"}  
  
Посмотреть список всех пользователей и сообщений можно по запросу: **curl http://localhost:8080/api/user**  
  
### Curl запросы:  
**(Проверены только в Windows 11)**  
**Срок жизни JWT ключа 3 минуты**  
### Запрос на авторизацию пользователя
curl -X POST http://localhost:8080/api/user -H "Content-Type: application/json" -d "{\"name\":\"user1\", \"password\":\"password1\"}"
- name - имя пользователя
- password - пароль пользователя
### Запрос на сохранение сообщения
curl -X POST http://localhost:8080/api/message -H "Content-Type: application/json" -H "Authorization:Bearer_" -d "{\"name\":\"user1\", \"message\":\"Hello World!\"}"
- name - имя пользователя
- message - сообщение пользователя  
Для выполнения запроса необходимо в -H "Authorization:Bearer_", после _ вставить jwt токен, полученный из запроса авторизации  
### Запрос на получение 10 последних сообщений из БД
curl -X POST http://localhost:8080/api/message -H "Content-Type: application/json" -H "Authorization:Bearer_" -d "{\"name\":\"user1\", \"message\":\"history 10\"}"
- name - имя пользователя  
Для работы запроса поле message должно быть неизменным  
Для выполнения запроса необходимо в -H "Authorization:Bearer_", после _ вставить jwt токен, полученный из запроса авторизации    
