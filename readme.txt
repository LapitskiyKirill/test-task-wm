Swagger ui: http://localhost:8081/api/swagger-ui.html
            http://localhost:8082/api/swagger-ui.html

Run tests: >mvn test

Run Docker: >mvn package -DskipTests
>docker-compose build
>docker-compose up

h2 console: http://localhost:8081/h2-console
jdbc url: jdbc:h2:mem:challengedb
User name: sa
Password: sa

url prefix: /api