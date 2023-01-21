import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    public static Courier getDefaultCourier() {
        String login = RandomStringUtils.randomAlphabetic(8);
        String password = RandomStringUtils.randomAlphabetic(8);
        String firstName = RandomStringUtils.randomAlphabetic(8);
        return new Courier(login, password, firstName);
    }

    public static Courier getDefaultCourierWithoutLogin() {
        String password = RandomStringUtils.randomAlphabetic(8);
        String firstName = RandomStringUtils.randomAlphabetic(8);
        return new Courier(null, password, firstName);
    }

    public static Courier getDefaultCourierWithoutPass() {
        String login = RandomStringUtils.randomAlphabetic(8);
        String firstName = RandomStringUtils.randomAlphabetic(8);
        return new Courier(login, null, firstName);
    }


}
