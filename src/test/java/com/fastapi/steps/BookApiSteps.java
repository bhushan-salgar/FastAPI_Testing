package com.fastapi.steps;


import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.response.Response;
import org.json.JSONObject;
import com.fastapi.utilities.TestContext;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.*;


public class BookApiSteps {
	
	private static final String BASE_URI = "http://127.0.0.1:8000";
	private static final String BASE_PATH = "/books/";
	
	private Response response;
    
//    @When("I send GET request to {string}")
//    public void i_send_get_request(String endpoint) {
//    	//System.out.println("Sending request to: " + RestAssured.baseURI + endpoint);
//        response = given().baseUri(BASE_URI).when().get(endpoint);  // will prepend RestAssured.baseURI
//    }
	@When("I send GET request to {string}")
    public void i_send_get_request(String endpoint) {
        response = given()
            .baseUri(BASE_URI)
            .header("Authorization", "Bearer " + TestContext.bearerToken)
            .when()
            .get(endpoint);
    }
    
    @When("I sign up with email {string} and password {string}")
    public void signup(String email, String password) {
        response = sendJsonPost("/signup", createAuthPayload(email, password));
    }
    
    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        assertThat("Unexpected status code", response.statusCode(), equalTo(expectedStatusCode));
    }
    
 // --- Helper methods ---
    private JSONObject createAuthPayload(String email, String password) {
        JSONObject payload = new JSONObject();
        payload.put("email", email);
        payload.put("password", password);
        return payload;
    }

    private Response sendJsonPost(String endpoint, JSONObject payload) {
        return given()
        	.baseUri(BASE_URI)
            .contentType("application/json")
            .body(payload.toString())
            .post(endpoint);
    }
    
    
    
    @When("I log in with email {string} and password {string}")
    public void login(String email, String password) {
        response = sendJsonPost("/login", createAuthPayload(email, password));
        // Store token globally if needed:
        if (response.statusCode() == 200) {
            String token = response.jsonPath().getString("access_token");
            TestContext.bearerToken = token;
        }
    }
    
    @Then("I should get a bearer token")
    public void iShouldGetBearerToken() {
        assertThat("Expected 200 OK", response.statusCode(), is(200));
        String token = response.jsonPath().getString("access_token");
        assertThat("Access token should not be null", token, notNullValue());
    }
    
    
//    @When("I add a book with name {string}, author {string}, year {int}, and summary {string}")
//    public void add_book(String name, String author, Integer year, String summary) {
//        JSONObject payload = createBookPayload(name, author, year, summary);
//        response = sendAuthorizedPost(BASE_PATH, payload);
//        assertNotNull(response.jsonPath().get("id"), "Book creation failed; no ID returned.");
//        TestContext.createdBookId = response.jsonPath().getInt("id");
//    }
    
    @When("I add a book with name {string}, author {string}, published_year {int}, and book_summary {string}")
    public void i_add_a_book_with_name_author_published_year_and_book_summary(String name, String author, Integer published_year, String book_summary) {
        JSONObject payload = createBookPayload(name, author, published_year, book_summary);
        response = sendAuthorizedPost("/books/", payload); // Use your actual endpoint

        if (response.statusCode() == 200) {
            Integer bookId = response.jsonPath().getInt("id");
            assertNotNull(bookId, "Book creation succeeded, but no ID returned.");
            TestContext.createdBookId = bookId;
        } else {
            System.out.println("Failed to add book. Status: " + response.statusCode());
        }
    }
    
 // 🔧 Helper methods

    private JSONObject createBookPayload(String name, String author, int year, String summary) {
        JSONObject payload = new JSONObject();
        payload.put("name", name);
        payload.put("author", author);
        payload.put("published_year", year);
        payload.put("book_summary", summary);
        return payload;
    }

    private Response sendAuthorizedPost(String endpoint, JSONObject payload) {
        return given()
        	.baseUri(BASE_URI)	
            .header("Authorization", "Bearer " + TestContext.bearerToken)
            .contentType("application/json")
            .body(payload.toString())
            .post(endpoint);
    }
    
    
//    @When("I send GET request to {string}")
//    public void i_send_get_request_to(String endpoint) {
//        response = given().baseUri(BASE_URI).when().get(endpoint);
//    }
    
    @Then("the response should contain a list of books")
    public void the_response_should_contain_a_list_of_books() {
        List<Map<String, Object>> books = response.jsonPath().getList("$");
        assertNotNull(books, "Response should not be null");
        assertFalse(books.isEmpty(), "Book list should not be empty");
        assertTrue(books.get(0).containsKey("name"), "Each book should have a 'name' field");
    }
    
    
    @When("a book with ID exists")
    public void a_book_with_id_exists() {
        // Optionally verify the book exists or create it if needed
        response = given()
            .baseUri(BASE_URI)
            .header("Authorization", "Bearer " + TestContext.bearerToken)
            .when()
            .get("/books/" + TestContext.createdBookId);

        if (response.statusCode() == 404) {
            // Book doesn't exist, so create one
            JSONObject payload = createBookPayload("Book" + TestContext.createdBookId, "Author" + TestContext.createdBookId, 2023, "Auto-created for test");
            response = sendAuthorizedPost("/books/", payload);
            TestContext.createdBookId = response.jsonPath().getInt("id");
            assertThat("Book should be created successfully", response.statusCode(), equalTo(200));
        }
//        } else {
//            TestContext.createdBookId = TestContext.createdBookId;
//        }
    }
    
    
    @When("I send GET request to book")
    public void i_send_get_request_book() {
        response = given()
            .baseUri(BASE_URI)
            .header("Authorization", "Bearer " + TestContext.bearerToken)
            .when()
            .get("/books/" + TestContext.createdBookId);
    }

    // ==================== WHEN ====================

    @When("I update the book with name {string}, author {string}, published_year {int}, and book_summary {string}")
    public void i_update_the_book(String name, String author, Integer publishedYear, String summary) {
        JSONObject payload = createBookPayload(name, author, publishedYear, summary);

        response = given()
            .baseUri(BASE_URI)
            .header("Authorization", "Bearer " + TestContext.bearerToken)
            .contentType("application/json")
            .body(payload.toString())
            .when()
            .put("/books/" + TestContext.createdBookId);
    }


    @When("I send DELETE request")
    public void i_send_delete_request() {
        response = given()
            .baseUri(BASE_URI)
            .header("Authorization", "Bearer " + TestContext.bearerToken)
            .when()
            .delete("/books/" + TestContext.createdBookId);
    }
    
    @When("I send DELETE Invalid request to {string}")
    public void i_send_delete_invalid_request(String endpoint) {
        response = given()
            .baseUri(BASE_URI)
            .header("Authorization", "Bearer " + TestContext.bearerToken)
            .when()
            .delete(endpoint);
    }

    
    
    
    
    
}