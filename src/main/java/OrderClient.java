import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    private final String pathOrder = "/api/v1/orders";

    private final String pathCancel = "/api/v1/orders/cancel";

    private final String pathGet = "/api/v1/orders";

    public ValidatableResponse create (OrderCreated orderCreated) {
        return given()
                .spec(getSpec())
                .body(orderCreated)
                .when()
                .post(pathOrder)
                .then();
    }

    public ValidatableResponse cancel (int track) {
        return given()
                .spec(getSpec())
                .body(track)
                .when()
                .put(pathCancel)
                .then();
    }

    public ValidatableResponse get () {
        return given()
                .spec(getSpec())
                .when()
                .get(pathGet)
                .then();
    }
}
