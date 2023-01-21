import org.apache.commons.lang3.RandomStringUtils;

public class InvalidCourierGenerator {

    public static CourierCradentials getInvalidCourier() {
        String login = RandomStringUtils.randomAlphabetic(8);
        String password = RandomStringUtils.randomAlphabetic(8);
        return new CourierCradentials(login, password);
    }
}
