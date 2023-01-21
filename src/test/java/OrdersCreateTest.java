import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrdersCreateTest {
    private OrderClient orderClient;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private String rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    private int track;


    public OrdersCreateTest(String firstName, String lastName, String address, String metroStation, String phone, String rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public  static Object [][] setOrderData () {
         return new Object[][]  {
                {"Иван", "Иванов", "Московская д.33", "5", "+79001234567", "2", "2023-06-06", "Очень жду ", new String[]{"BLACK"}},
                {"Иван", "Иванов", "Московская д.33", "5", "+79001234567", "2", "2023-06-06", "Очень жду ", new String[]{"GREY"}},
                {"Иван", "Иванов", "Московская д.33", "5", "+79001234567", "2", "2023-06-06", "Очень жду ", new String[]{"BLACK", "GREY"}},
                {"Иван", "Иванов", "Московская д.33", "5", "+79001234567", "2", "2023-06-06", "Очень жду ", new String[]{}}
        };
    }

    @Before
    public void setUp() {
        orderClient =new OrderClient();
    }

    @After
    public void cleanUp(){
        orderClient.cancel(track);
    }

    @Test
    @DisplayName("Order Created")
    public void orderCreated(){
        OrderCreated orderCreated = new OrderCreated(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        ValidatableResponse response = orderClient.create(orderCreated);
        track = response.extract().path("track");
        int statusCode = response.extract().statusCode();
        assertEquals(SC_CREATED, statusCode);
        response.assertThat().body("track", notNullValue());
    }
    }

