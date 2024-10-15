## Простейший пример работы jwt аутентификации и method based авторизации на основе scope из токена 
я так понимаю что spring boot 3 не требует явно делать кастомный фильтр для аутентификации,
вся магия уже под капотом
### Генерируем private/public ключи
```shell
openssl genrsa -out keypair.pem 2048
openssl rsa -in keypair.pem -pubout -out public.pem
openssl pkcs8 -in keypair.pem -topk8 -nocrypt -inform PEM -outform PEM -out private.pem
```
### Запросы
```shell
curl --location --request POST 'localhost:8080/token' --header 'Authorization: Basic dXNlcjowMDAw'
curl --location 'localhost:8080/hello' --header 'Authorization: Bearer <выхлоп предыдущего запроса>'
```
Но лучше смотреть тест HelloControllerTests 
### Доки 
<https://github.com/spring-projects/spring-security-samples/tree/6.3.x/servlet/spring-boot/java/jwt/login>  
<https://www.baeldung.com/spring-security-method-security>  
<https://bootify.io/spring-security/signing-jwts-with-private-key-spring-security.html>  
<https://www.geeksforgeeks.org/spring-boot-3-0-jwt-authentication-with-spring-security-using-mysql-database>  
<https://habr.com/ru/articles/784508>  
