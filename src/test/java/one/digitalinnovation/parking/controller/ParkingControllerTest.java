package one.digitalinnovation.parking.controller;

import io.restassured.RestAssured;
import one.digitalinnovation.parking.controller.dto.ParkingCreateDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

// Disponibiliza uma porta aleatória para testes
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ParkingControllerTest extends AbstractContainerBase {

    @LocalServerPort
    private int randomPort;


    @BeforeEach
    public void setUpTest() {
        RestAssured.port = randomPort;
    }

    // Implementando testes do findAll()
    @Test
    void whenFindAllThenCheckResult() {
        RestAssured.given()
                .auth().basic("user", "Dio@123456")
                .when()
                .get("/parking")
                .then()
                //.extract().response().body().prettyPrint();
                .statusCode(HttpStatus.OK.value());

    }


    @Test
    void whenCreateThenCheckIsCreated() {
        var createDTO = new ParkingCreateDTO();
        createDTO.setColor("AMARELO");
        createDTO.setLicense("SSP-3333");
        createDTO.setModel("BRASILIA");
        createDTO.setState("SP");

        RestAssured.given()
                .when()
                .auth().basic("user", "Dio@123456")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(createDTO)
                .post("/parking")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("license", Matchers.equalTo("SSP-3333"))
                .body("color", Matchers.equalTo("AMARELO"));

    }
}