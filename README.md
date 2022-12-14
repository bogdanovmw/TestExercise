# Тестовое задание для Инсайд
## Описание приложения
Приложение позволяет хранить, получать и работать с пользователями и их сообщениями. В базе данных созданы две таблицы users(id, name, password) и message(id, id_user, message), между users.id и message. id_user связь 1кМ. Для авторизации пользователя необходимо выполнить POST запрос по адресу api/user с телом {name: 'user', password: 'password'}, если пароль пользователя верный генерируется jwt токен со сроком жизни 3 минуты.  Для сохранения сообщения от пользователя необходим верный и действующий jwt токен, сохранение сообщения происходит POST запросом по адресу api/message с телом {name: 'user', message: “message”}, в случаи соблюдения всех условий сообщение будет сохранено в таблицу. Для получения 10 последний сообщений, также необходим верный jwt, выполнить данный запрос можно по POST адресу api/message, с указанием в теле {name: 'user', message: “history 10”}, в случаи отсутствия history 10, будет происходить сохранения сообщения.  
  
Технологии:
- Java 8
- Spring Boot 2.7.3
  - (org.springframework.boot:spring-boot-starter-web:2.7.3)
  - org.springframework.boot:spring-boot-starter-data-jpa:2.7.3
- Liquibase org.liquibase:liquibase-core:4.9.1
- Jjwt (io.jsonwebtoken:jjwt:0.9.1)
- Postgresql (org.postgresql:postgresql:42.3.6)
- Lombok (org.projectlombok:lombok:1.18.24)
- Junit 5 (org.junit.jupiter:junit-jupiter:5.8.2)
- Mockito (org.mockito:mockito-junit-jupiter:4.5.1)



## Установка и запуск программы  
Проект разрабатывался, запускался и тестировался в Windows 11  
1. В операционной системе должен быть установлен и запущен Docker
2. Необходимо скачать и разархивировать архив [по ссылке](https://drive.google.com/file/d/10W2DncYEag8jJ8vLV-myQnObAtFhE63d/view?usp=sharing)  
3. Перейти в разархивированную папку DockerExercise 
4. Запустить start.bat
5. Проект запущен по http://localhost:8080/  
  
Ссылки на Docker Hub
- [Ссылка на postgres Image](https://hub.docker.com/repository/docker/bogdanovmw/testexercise-postgres)
- [Ссылка на application Image](https://hub.docker.com/repository/docker/bogdanovmw/testexercise-application)  

## Данные в таблице:  
В таблице есть 3 пользователя:
- {name: "user1", password: "password1"}
- {name: "user2", password: "password2"}
- {name: "user3", password: "password3"}  
  
Посмотреть список всех пользователей и сообщений можно по запросу: **curl http://localhost:8080/api/user**  
  
## Curl запросы:  
**(Проверены только в Windows 11 и терменале CMD)**  
**Срок жизни JWT ключа 3 минуты**  
### Запрос на авторизацию пользователя
curl -X POST http://localhost:8080/api/user -H "Content-Type: application/json" -d "{\\"name\\":\\"user1\\", \\"password\\":\\"password1\\"}"
- name - имя пользователя
- password - пароль пользователя
### Запрос на сохранение сообщения
curl -X POST http://localhost:8080/api/message -H "Content-Type: application/json" -H "Authorization:Bearer_" -d "{\\"name\\":\\"user1\\", \\"message\\":\\"Hello World!\\"}"
- name - имя пользователя
- message - сообщение пользователя  
Для выполнения запроса необходимо в -H "Authorization:Bearer_", после _ вставить jwt токен, полученный из запроса авторизации  
### Запрос на получение 10 последних сообщений из БД
curl -X POST http://localhost:8080/api/message -H "Content-Type: application/json" -H "Authorization:Bearer_" -d "{\\"name\\":\\"user1\\", \\"message\\":\\"history 10\\"}"
- name - имя пользователя  
Для работы запроса поле message должно быть неизменным  
Для выполнения запроса необходимо в -H "Authorization:Bearer_", после _ вставить jwt токен, полученный из запроса авторизации    
