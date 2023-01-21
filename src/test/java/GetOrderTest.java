import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.notNullValue;


public class GetOrderTest {
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient =new OrderClient();
    }

    @Test
    @DisplayName("Get Order Test")
    public void getOrderTest(){
        ValidatableResponse  response = orderClient.get();
        response.assertThat().body("orders", notNullValue());
    }
}
