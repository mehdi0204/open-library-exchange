package fr.bookswap.exchange;

import fr.bookswap.security.JwtTestUtils;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ExchangeResourceTest {

    @Test
    public void testGetExchangesRequiresAuth() {
        given()
        .when()
            .get("/api/exchanges")
        .then()
            .statusCode(401);
    }

    @Test
    public void testGetExchangesAuthenticated() {
        given()
            .header("Authorization", "Bearer " + JwtTestUtils.userToken())
        .when()
            .get("/api/exchanges")
        .then()
            .statusCode(200)
            .body("$", instanceOf(java.util.List.class));
    }

    @Test
    public void testCreateExchangeOnOwnBook() {
        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + JwtTestUtils.userToken())
            .body("""
                {
                  "userBookId": 1,
                  "type": "EXCHANGE",
                  "message": "Test"
                }
                """)
        .when()
            .post("/api/exchanges")
        .then()
            .statusCode(400);
    }
}
