package apitest;

import io.qameta.allure.Allure;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiTest {

    private static final String BASE_URL = "https://restful-booker.herokuapp.com";
    private static String token;
    private static int bookingId;
    private static final String createdFirstname = "fimo";
    private static final String createdLastname = "kalirul";
    private static final String updateLastname = "sakurasun";

    // Step 1: Get token
    @Test
    @Order(1)
    public void test1_getToken() {
        String body = "{ \"username\": \"admin\", \"password\": \"password123\" }";

        Response response = given()
            .baseUri(BASE_URL)
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/auth");

        response.then().statusCode(200).body("token", notNullValue());
        token = response.jsonPath().getString("token");
        System.out.println("Token: " + token);
        Allure.step("Print status code: " + response.getStatusCode());
        Allure.step("Print response body: " + response.getBody().asString());
        Allure.step("Print token: " + token);
        assertNotNull(token);
    }

    // Step 2: Create booking
    @Test
    @Order(2)
    public void test2_createBooking() {
        String body = "{\n" +
        "    \"firstname\": \"" + createdFirstname + "\", \n" +
        "    \"lastname\": \"" + createdLastname + "\",\n" +
        "    \"totalprice\": 150,\n" +
        "    \"depositpaid\": true,\n" +
        "    \"bookingdates\": {\n" +
        "        \"checkin\": \"2025-06-20\",\n" +
        "        \"checkout\": \"2025-06-30\"\n" +
        "    },\n" +
        "    \"additionalneeds\": \"Breakfast\"\n" +
        "}";

        Response response = given()
            .baseUri(BASE_URL)
            .contentType(ContentType.JSON)
            .body(body)
        .when()
            .post("/booking");

        response.then().statusCode(200).body("bookingid", notNullValue());
        bookingId = response.jsonPath().getInt("bookingid");
        System.out.println("Booking ID: " + bookingId);
        Allure.step("Print status code: " + response.getStatusCode());
        Allure.step("Print response body: " + response.getBody().asString());
        Allure.step("Print booking ID: " + bookingId);
        assertTrue(bookingId > 0);
    }

    // Step 3: Get booking by ID
    @Test
    @Order(3)
    public void test3_getBookingByName() {

        assertNotNull(createdFirstname);
        assertNotNull(createdLastname);

        Response response = given()
            .baseUri(BASE_URL)
            .header("Accept", "application/json")
            .queryParam("firstname", createdFirstname)
            .queryParam("lastname", createdLastname)
        .when()
            .get("/booking");

        response.then()
            .statusCode(200);

        List<Integer> iDs = response.jsonPath().getList("bookingid");
        assertFalse(null == iDs || iDs.isEmpty(), "Booking ID not found for the given firstname and lastname");

        int foundBookingId = iDs.get(0);

        Response bookingResponse = given()
            .baseUri(BASE_URL)
            .header("Accept", "application/json")
        .when()
            .get("/booking/" + foundBookingId);

            bookingResponse.then().statusCode(200)
            .body("firstname", equalTo(createdFirstname))
            .body("lastname", equalTo(createdLastname));

        Allure.step("Print status code: " + bookingResponse.getStatusCode());
        Allure.step("Print response body: " + bookingResponse.getBody().asString());
            
    }

    // Step 4: Update booking by ID
    @Test
    @Order(4)
    public void test4_updateBooking() {
        assertNotNull(token);

        String updatedBody = "{\n" +
        "    \"firstname\": \"fimo\",\n" +
        "    \"lastname\": \"" + updateLastname + "\",\n" +
        "    \"totalprice\": 200,\n" +
        "    \"depositpaid\": false,\n" +
        "    \"bookingdates\": {\n" +
        "        \"checkin\": \"2025-07-01\",\n" +
        "        \"checkout\": \"2025-07-10\"\n" +
        "    },\n" +
        "    \"additionalneeds\": \"Lunch\"\n" +
        "}";

        Response response = given()
            .baseUri(BASE_URL)
            .contentType(ContentType.JSON)
            .header("Cookie", "token=" + token)
            .body(updatedBody)
        .when()
            .put("/booking/" + bookingId);

        response.then().statusCode(200)
            .body("firstname", equalTo("fimo"))
            .body("lastname", equalTo("sakurasun"))
            .body("totalprice", equalTo(200));
        Allure.step("Print status code: " + response.getStatusCode());
        Allure.step("Print response body: " + response.getBody().asString());
    }

    // Step 5: Delete booking by ID
    @Test
    @Order(5)
    public void test5_deleteBooking() {
        assertNotNull(token);

        Response response = given()
            .baseUri(BASE_URL)
            .header("Cookie", "token=" + token)
        .when()
            .delete("/booking/" + bookingId);

        response.then().statusCode(201); // 201 = Deleted

        // Optional: Cek kalau booking sudah tidak ada
        given()
            .baseUri(BASE_URL)
        .when()
            .get("/booking/" + bookingId)
        .then()
            .statusCode(404);
        Allure.step("Print status code: " + response.getStatusCode());
        Allure.step("Print response body: " + response.getBody().asString());
    }
}