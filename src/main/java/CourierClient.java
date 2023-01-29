import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends Client {

    private  final String pathCreated = "/api/v1/courier";
    private final String pathLogin = "/api/v1/courier/login";

    private final String pathDelete = "/api/v1/courier/:id";



    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(pathCreated)
                .then();
    }


    public  ValidatableResponse login(CourierCradentials credentials) {
    return  given()
            .spec(getSpec())
            .body(credentials)
            .when()
            .post(pathLogin)
            .then();

    }

    public ValidatableResponse delete(int id) {
        return given()
                .spec(getSpec())
                .body(id)
                .when()
                .delete(pathDelete)
                .then();
    }

}
