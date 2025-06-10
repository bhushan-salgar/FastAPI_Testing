package com.fastapi.base;

import io.cucumber.java.Before;
import io.restassured.RestAssured;

public class Hooks {

    @Before
    public void init() {
        RestAssured.baseURI = "http://127.0.0.1:8000";
        System.out.println("Base URI set to: " + RestAssured.baseURI);
    }
}