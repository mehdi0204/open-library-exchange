package fr.bookswap.auth;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class AuthResourceTest {

    @Test
    public void testRegisterSuccess() {
        given()
            .contentType(ContentType.JSON)
            .body("""
                {
                  "username": "newuser",
                  "email": "newuser@test.fr",
                  "password": "Password1!"
                }
                """)
        .when()
            .post("/api/auth/register")
        .then()
            .statusCode(201)
            .body("accessToken", notNullValue())
            .body("tokenType", equalTo("Bearer"));
    }

    @Test
    public void testRegisterValidationError() {
        given()
            .contentType(ContentType.JSON)
            .body("""
                {
                  "username": "",
                  "email": "invalid-email",
                  "password": "short"
                }
                """)
        .when()
            .post("/api/auth/register")
        .then()
            .statusCode(400)
            .body("error", equalTo("VALIDATION_ERROR"));
    }

    @Test
    public void testLoginSuccess() {
        // D'abord on s'inscrit
        given()
            .contentType(ContentType.JSON)
            .body("""
                {
                  "username": "logintest",
                  "email": "logintest@test.fr",
                  "password": "Password1!"
                }
                """)
        .when()
            .post("/api/auth/register");

        // Puis on se connecte
        given()
            .contentType(ContentType.JSON)
            .body("""
                {
                  "username": "logintest",
                  "password": "Password1!"
                }
                """)
        .when()
            .post("/api/auth/login")
        .then()
            .statusCode(200)
            .body("accessToken", notNullValue());
    }

    @Test
    public void testLoginWrongPassword() {
        given()
            .contentType(ContentType.JSON)
            .body("""
                {
                  "username": "logintest",
                  "password": "WrongPassword!"
                }
                """)
        .when()
            .post("/api/auth/login")
        .then()
            .statusCode(400);
    }

    @Test
    public void testMeWithoutToken() {
        given()
        .when()
            .get("/api/auth/me")
        .then()
            .statusCode(401);
    }
}
