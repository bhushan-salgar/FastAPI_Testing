# FastAPI Bookstore API Tests

This project tests CRUD APIs for a FastAPI-based bookstore application using:
- RestAssured
- TestNG
- Cucumber
- Allure Reports

## Features Covered
- Signup / Login
- Book Add / Update / Get / Delete
- Positive and negative scenarios
- Request chaining (token & book ID reuse)

## Run Tests
```bash
mvn clean test

## Genrate Report
allure serve target/allure-results


