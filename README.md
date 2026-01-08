# Investment Banking Backend

A Spring Boot backend application for managing investment banking deals with secure authentication.

## Tech Stack
- Java 21
- Spring Boot
- Spring Security + JWT
- JPA + Hibernate
- MySQL
- Docker
- JaCoCo
- JUnit + Mockito

## Features
- User authentication & authorization
- Deal management
- Role-based access
- JWT security
- Dockerized deployment
- Test coverage with JaCoCo

## Run with Docker
```bash
docker build -t investment-banking-backend .
docker run -p 8080:8080 investment-banking-backend
