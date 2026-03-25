package fr.bookswap.book;

import fr.bookswap.security.JwtTestUtils;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class BookResourceTest {

    @Test
    public void testListBooksPublic() {
        given()
        .when()
            .get("/api/books")
        .then()
            .statusCode(200)
            .body("$", instanceOf(java.util.List.class));
    }

    @Test
    public void testGetBookNotFound() {
        given()
        .when()
            .get("/api/books/99999")
        .then()
            .statusCode(404);
    }

    @Test
    public void testCreateBookRequiresAuth() {
        given()
            .contentType(ContentType.JSON)
            .body("""
                {
                  "isbn": "978-0-000-00000-0",
                  "title": "Test Book"
                }
                """)
        .when()
            .post("/api/books")
        .then()
            .statusCode(401);
    }

    @Test
    public void testCreateBookSuccess() {
        String token = JwtTestUtils.userToken();

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + token)
            .body("""
                {
                  "isbn": "978-0-111-11111-1",
                  "title": "Mon livre de test",
                  "description": "Description test",
                  "publicationYear": 2024
                }
                """)
        .when()
            .post("/api/books")
        .then()
            .statusCode(201)
            .body("title", equalTo("Mon livre de test"));
    }

    @Test
    public void testListAuthorsPublic() {
        given()
        .when()
            .get("/api/authors")
        .then()
            .statusCode(200);
    }

    @Test
    public void testDeleteBookRequiresAdmin() {
        given()
            .header("Authorization", "Bearer " + JwtTestUtils.userToken())
        .when()
            .delete("/api/books/1")
        .then()
            .statusCode(403);
    }
}
