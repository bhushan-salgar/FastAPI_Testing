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

### Running the Application

1. Start the FastAPI server:

    ```bash
    uvicorn main:app --reload
    ```

2. The API will be available at `http://127.0.0.1:8000`


## Run Tests
```bash
mvn clean test

## Genrate Report
allure serve target/allure-results


