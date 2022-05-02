package netology.test;

import io.restassured.response.ResponseBody;
import netology.data.ApiUtils;
import netology.data.Card;
import netology.data.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTest {
    private ApiUtils apiUtils = new ApiUtils();

    @Test
    void shouldTransfer() {
        User user = new User("vasya", "qwerty123");
        apiUtils.login(user);
        String token = apiUtils.verification(user);
        ArrayList<Card> cardsBeforeTransfer = apiUtils.getCards(token);
        int amount = 200;
        apiUtils.transfer(token, "5559 0000 0000 0001", "5559 0000 0000 0002", amount);
        ArrayList<Card> cardsAfterTransfer = apiUtils.getCards(token);
        assertEquals(cardsAfterTransfer.get(1).getBalance(), cardsBeforeTransfer.get(1).getBalance() - amount);
    }

    @Test
    void shouldNotTransferNegativeAmount() {
        User user = new User("vasya", "qwerty123");
        apiUtils.login(user);
        String token = apiUtils.verification(user);
        ArrayList<Card> cardsBeforeTransfer = apiUtils.getCards(token);
        int amount = -200;
        apiUtils.transfer(token, "5559 0000 0000 0001", "5559 0000 0000 0002", amount);
        ArrayList<Card> cardsAfterTransfer = apiUtils.getCards(token);
        assertEquals(cardsAfterTransfer.get(1).getBalance(), cardsBeforeTransfer.get(1).getBalance() + amount);
    }
}
