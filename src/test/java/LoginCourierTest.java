import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

public class LoginCourierTest {

    private Courier courier;
    private CourierClient courierClient;

    private CourierCradentials courierCradentials;

    private CourierCradentials courierCredentialsWithoutPassword;
    private CourierCradentials courierCredentialsWithoutLogin;

    private CourierCradentials invalidCourierCredentials;
    private int id;


    @Before
    public void setUp() {
        courier = CourierGenerator.getDefaultCourier();
        courierClient = new CourierClient();
        courierCradentials = new CourierCradentials(courier.getLogin(), courier.getPassword());
        courierCredentialsWithoutPassword = new CourierCradentials(courier.getLogin(), "");
        courierCredentialsWithoutLogin = new CourierCradentials("", courier.getPassword());
        invalidCourierCredentials = InvalidCourierGenerator.getInvalidCourier();
    }

    @After
    public void cleanUp(){
        courierClient.delete(id);
    }

    @Test
    @DisplayName("Courier Can Be Login")
    public  void courierCanBeLogin(){
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCradentials.from(courier));
        id = loginResponse.extract().path("id");
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_OK, statusCode);
    }

    @Test
    @DisplayName("Courier Credential sWithout Password")
    public  void  courierCredentialsWithoutPassword(){
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(courierCredentialsWithoutPassword);
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
    }

    @Test
    @DisplayName("Courier Credentials Without Password Message")
    public  void  courierCredentialsWithoutPasswordMessage(){
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(courierCredentialsWithoutPassword);
        String messageResponse = loginResponse.extract().path("message");
        assertEquals("Недостаточно данных для входа", messageResponse);
    }

    @Test
    @DisplayName("Courier Credentials Without Login")
    public  void  courierCredentialsWithoutLogin(){
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(courierCredentialsWithoutLogin);
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_BAD_REQUEST, statusCode);
    }

    @Test
    @DisplayName("Courier Credentials Without Login Message")
    public  void  courierCredentialsWithoutLoginMessage(){
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(courierCredentialsWithoutLogin);
        String messageResponse = loginResponse.extract().path("message");
        assertEquals("Недостаточно данных для входа", messageResponse);
    }

    @Test
    @DisplayName("Invalid Courier Credentials")
    public  void  invalidCourierCredentials(){
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(invalidCourierCredentials);
        int statusCode = loginResponse.extract().statusCode();
        assertEquals(SC_NOT_FOUND, statusCode);
    }

    @Test
    @DisplayName("Invalid Courier Credentials Message")
    public  void  invalidCourierCredentialsMessage(){
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(invalidCourierCredentials);
        String messageResponse = loginResponse.extract().path("message");
        assertEquals("Учетная запись не найдена", messageResponse);
    }
}
