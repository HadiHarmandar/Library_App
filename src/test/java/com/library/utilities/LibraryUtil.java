package com.library.utilities;

import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class LibraryUtil {

    static Faker faker = new Faker();

    public static String getToken(String email, String password) {

        Map<String, String> credentials = new HashMap<>();
        credentials.put("email", email);
        credentials.put("password", password);

        JsonPath jsonPath = given()
                .accept(ContentType.JSON)
                .contentType(ContentType.URLENC)
                .formParams(credentials)
                .when()
                .post("/login")
                .then()
                .extract().jsonPath();

        return jsonPath.getString("token");
    }

    public static String getTokenByRole(String role) {
        Map<String, String> credentials = retrieveCredentials(role);
        return getToken(credentials.get("email"), credentials.get("password"));
    }

    public static Map<String, String> retrieveCredentials(String role) {

        String email = "";
        String password = "";

        Map<String, String> credentials = new HashMap<>();

        switch (role) {
            case "librarian":
                email = System.getenv("LIBRARIAN_USERNAME");
                password = System.getenv("LIBRARIAN_PASSWORD");
                break;
            case "student":
                email = System.getenv("STUDENT_USERNAME");
                password = System.getenv("STUDENT_PASSWORD");
                break;
            default:
                throw new RuntimeException("Invalid role: " + role);
        }
        credentials.put("email", email);
        credentials.put("password", password);
        return credentials;
    }

    public static Map<String, Object> createRandomBook() {
        Map<String, Object> book = new HashMap<>();

        book.put("name", "POST book123");
        book.put("isbn", faker.lordOfTheRings().character());
        book.put("year", faker.number().numberBetween(2019, 2024));
        book.put("author", faker.book().author());
        book.put("book_category_id", 1);
        book.put("description", faker.chuckNorris().fact());

        return book;
    }

    public static Map<String, Object> createRandomUser() {
        Map<String, Object> user = new HashMap<>();
        LocalDate startDate = LocalDate.of(2020, 10, 10);
        LocalDate endDate = LocalDate.of(2025, 10, 10);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        user.put("full_name", faker.name().fullName());
        user.put("email", faker.internet().emailAddress());
        user.put("password", faker.internet().password());
        user.put("user_group_id", 2);
        user.put("status", "ACTIVE");
        user.put("start_date", startDate.format(formatter));
        user.put("end_date", endDate.format(formatter));
        user.put("address", faker.address().fullAddress());

        return user;
    }
}
