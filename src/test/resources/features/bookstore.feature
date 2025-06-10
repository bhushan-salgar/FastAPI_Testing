Feature: FastAPI Tests

  Scenario: Health Check

    When I send GET request to "/health"
    Then the response status code should be 200
    
    Scenario Outline: Signup with valid or already registered users
 	 When I sign up with email "<email>" and password "<password>"
  	Then the response status code should be <status>

  Examples:
    | email                      | password  | status |
    | bhushan.salgar1@gmail.com  | pass123   | 400    |
    | test.user2@example.com    | testpass1 | 400    |
     
    Scenario Outline: Login with valid users  
    When I log in with email "<email>" and password "<password>"
    Then I should get a bearer token
    Examples:
      | email                      | password  |
      | bhushan.salgar@gmail.com  | pass123   |
      | test.user1@example.com    | testpass1 | 
      
    Scenario: Login with invalid credentials
    When I log in with email "invalid.user@example.com" and password "wrongpass"
    Then the response status code should be 400   
    
    Scenario: Add a book
    When I add a book with name "Book1", author "Author1", published_year 2023, and book_summary "Test book"
    Then the response status code should be 200
    
    Scenario: Add a book with invalid data
    When I add a book with name "", author "", published_year 0, and book_summary ""
    Then the response status code should be 422
    
    Scenario: Get all books
    When I send GET request to "/books/"
    Then the response status code should be 200
    And the response should contain a list of books
    
    Scenario: Get a specific book by ID
    Given a book with ID exists
    When I send GET request to book
    Then the response status code should be 200

  Scenario: Update an existing book
    Given a book with ID exists
    When I update the book with name "Updated Book", author "Updated Author", published_year 2024, and book_summary "Updated summary"
    Then the response status code should be 200

  Scenario: Delete an existing book
    Given a book with ID exists
    When I send DELETE request
    Then the response status code should be 200

  Scenario: Delete a non-existent book
    When I send DELETE Invalid request to "/books/999"
    Then the response status code should be 404

  
    
      
    