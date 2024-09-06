# Book-Social-Network-Tutorial
Recreating project for learning purpose from tutorial https://www.youtube.com/watch?v=WuPa_XoWlJU
by Bouali Ali with some own changes as well as education comments

spring initializr project (with added docker compose and devtools)
https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.3.3&packaging=jar&jvmVersion=21&groupId=com.kmienko&artifactId=Book-Social-Network-Tutorial&name=Book-Social-Network-Tutorial-api&description=Tutorial%20project%20for%20Spring%20Boot%20&packageName=com.kmienko.Book-Social-Network-Tutorial&dependencies=web,data-jpa,thymeleaf,validation,mail,devtools,docker-compose,security,postgresql,lombok


## Technologies Used

- **Spring Boot 3**: A powerful framework for building Java-based applications.
- **Spring Security 6**: Provides authentication and authorization mechanisms for securing the application.
- **JWT Token Authentication**: Ensures secure communication between the client and server.
- **Spring Data JPA**: Simplifies data access and persistence using the Java Persistence API.
- **JSR-303 and Spring Validation**: Enables validation of objects based on annotations.
- **OpenAPI and Swagger UI Documentation**: Generates documentation for the API endpoints.
- **Docker**: Facilitates containerization of the backend application for deployment.
- **buildpacks.io**: Cloud Native Buildpacks (CNBs) transform your application source code into container images

<br>
Own improvments or trials: 
- Spring Boot Docker Compose Support: Even less configuration code (done)
- DevTools with BuildPacks live reload <br>
    -06.09.2024 1st result failed as we deleted hibernate dialect earlier due to lower hint
    org.hibernate.orm.deprecation : HHH90000025: PostgreSQLDialect does not need to be specified explicitly using 'hibernate.dialect' (remove the property setting and it will be selected by default)
    however buildpacks needs it for some reason, after restored in application properties I created an image with mvn spring-boot:build-image and run it 
    but it was main application only in one container without mailer and postgres so I have to get more knowledge in docker compose config

  
