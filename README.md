# BFHL REST API

Spring Boot REST API for placement assignment — classifies array elements into numbers, alphabets, and special characters




## Project Structure

```
bfhl-api/
├── pom.xml
├── README.md
└── src/
    ├── main/java/com/bfhl/
    │   ├── BfhlApplication.java
    │   ├── controller/BfhlController.java
    │   ├── dto/BfhlRequest.java
    │   ├── dto/BfhlResponse.java
    │   ├── service/BfhlService.java          ← Interface
    │   ├── service/impl/BfhlServiceImpl.java ← Logic
    │   └── exception/GlobalExceptionHandler.java
    ├── main/resources/application.properties
    └── test/java/com/bfhl/
        ├── controller/BfhlControllerTest.java
        └── service/BfhlServiceTest.java
```

---



---
