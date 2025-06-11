# FastAPI_Testing – API Automation Framework

This project provides automated API testing for a **FastAPI-based Bookstore application**. It validates core CRUD operations using a modern test framework stack built with:
- RestAssured
- TestNG
- Cucumber
- Allure Reports

## Features Covered

**User Authentication**  
  - Signup  
  - Login 
  
  **Book Management**  
  - Add a new book  
  - Update book details  
  - Retrieve book by ID  
  - Retrieve all book
  - Delete book  
  
  **Request Chaining**  
  - Reuse tokens from login  
  - Use book IDs across tests

	
### Running the Application

1. Start the FastAPI server:

    ```bash
    uvicorn main:app --reload
    ```

2. The API will be available at `http://127.0.0.1:8000`

##

## Run Tests
		```bash
		mvn clean test

## Genrate Report

	allure serve target/allure-results

##Prerequisites for CICD:
1.Jenkins Installed

2.JDK and Maven Installed (on Jenkins server)

3.Git Repository (where your test automation framework is hosted)

4.Test Framework Ready using:

	Maven for dependency management

	TestNG for execution

	Cucumber for BDD

	RestAssured for API testing
	
##Jenkins Job Configuration:

Step 1: Install Required Jenkins Plugins
Git Plugin

Maven Integration Plugin

Allure Jenkins Plugin (optional for reports)

Step 2: Create a New Jenkins Job
Go to Jenkins Dashboard → New Item

Name it (e.g., FastAPI_Testing)

Choose Freestyle project or Pipeline (preferred)

Click OK

