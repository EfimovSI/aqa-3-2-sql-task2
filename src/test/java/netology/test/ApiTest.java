package netology.test;

import io.restassured.response.ResponseBody;
import netology.data.ApiUtils;
import netology.data.User;
import org.junit.jupiter.api.Test;

public class ApiTest {
    private ApiUtils apiUtils = new ApiUtils();

    @Test
    void shouldTransfer() {
        User user = new User("vasya", "qwerty123");
        apiUtils.login(user);
        String token = apiUtils.verification(user);
        apiUtils.transfer(token, "5559 0000 0000 0001", "5559 0000 0000 0002", 3000);
        ResponseBody cards = apiUtils.getCards(token);
    }
}
