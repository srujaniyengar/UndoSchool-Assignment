# Course Search App

## Requirements

- Java 17+
- Maven
- Docker & Docker Compose

## Usage

1. Start Elasticsearch:  
   `docker-compose up -d`
2. Run the app:  
   `mvn spring-boot:run`
3. Search courses:  
   `GET http://localhost:8080/api/search?q=math`