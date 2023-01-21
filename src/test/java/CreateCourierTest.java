import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCourierTest {
    private Courier courier;
    private CourierClient courierClient;


    private Courier courierWithoutPassword;
    private Courier courierWithoutLogin;
    private Courier sameCourier;

    private int id;

    @Before
    public void setUp() {
        courier = CourierGenerator.getDefaultCourier();
        courierClient = new CourierClient();
        sameCourier = new Courier(courier.getLogin(), courier.getPassword(), courier.getFirstName());
        courierWithoutPassword = CourierGenerator.getDefaultCourierWithoutPass();
        courierWithoutLogin = CourierGenerator.getDefaultCourierWithoutLogin();
    }

    @After
    public void cleanUp(){
       courierClient.delete(id);
    }

    @Test
    @DisplayName("Courier Can Be Created")
    public void courierCanBeCreated(){
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCradentials.from(courier));
        id = loginResponse.extract().path("id");
        int statusCode = response.extract().statusCode();
        assertEquals(SC_CREATED, statusCode);
    }

    @Test
    @DisplayName("Courier Created Message")
    public void courierCreatedMessage(){
        ValidatableResponse response = courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCradentials.from(courier));
        id = loginResponse.extract().path("id");
        boolean messageResponse = response.extract().path("ok");
        assertTrue(messageResponse);
    }

    @Test
    @DisplayName("Same Courier Created")
    public void sameCourierCreated(){
        courierClient.create(courier);
        ValidatableResponse response = courierClient.create(sameCourier);
        ValidatableResponse loginResponse = courierClient.login(CourierCradentials.from(courier));
        id = loginResponse.extract().path("id");
        int statusCode = response.extract().statusCode();
        assertEquals(SC_CONFLICT, statusCode);
    }

    @Test
    @DisplayName("Same Courier Created Message")
    public void sameCourierCreatedMessage(){
        courierClient.create(courier);
        ValidatableResponse response = courierClient.create(sameCourier);
        ValidatableResponse loginResponse = courierClient.login(CourierCradentials.from(courier));
        id = loginResponse.extract().path("id");
        String messageResponse = response.extract().path("message");
        assertEquals("Этот логин уже используется. Попробуйте другой.", messageResponse);
    }

    @Test
    @DisplayName("Courier Created Without Login")
    public void courierWithoutLogin(){
        ValidatableResponse response = courierClient.create(courierWithoutLogin);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);

    }

    @Test
    @DisplayName("Courier Created Without Login Message")
    public void courierWithoutLoginMessage(){
        ValidatableResponse response = courierClient.create(courierWithoutLogin);
        String messageResponse = response.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", messageResponse);
    }

    @Test
    @DisplayName("Courier Created Without Pass")
    public void courierWithoutPass(){
        ValidatableResponse response = courierClient.create(courierWithoutPassword);
        int statusCode = response.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);

    }

    @Test
    @DisplayName("Courier Created Without Pass Message")
    public void courierWithoutPassMessage(){
        ValidatableResponse response = courierClient.create(courierWithoutPassword);
        String messageResponse = response.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", messageResponse);
    }
}
