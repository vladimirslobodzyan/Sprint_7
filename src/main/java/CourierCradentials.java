public class CourierCradentials {
    private String login;
    private String password;

    public CourierCradentials() {
    }

    public CourierCradentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCradentials from (Courier courier){
        return new CourierCradentials(courier.getLogin(), courier.getPassword());
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
