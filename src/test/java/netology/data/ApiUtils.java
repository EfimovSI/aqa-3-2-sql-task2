package netology.data;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ApiUtils {
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public ApiUtils() {
    }

    @SneakyThrows
    public static void login(User user) {
        RequestSpecification request = RestAssured.given();
        request.spec(requestSpec);// указываем, какую спецификацию используем
        request.body("{\n" +
                "\"login\": \"" + user.getLogin() + "\",\n" +
                "\"password\": \"" + user.getPassword() + "\"\n" +
                "}");  // передаём в теле объект, который будет преобразован в JSON
        Response response = request.post("/api/auth");

        assertThat(response.statusCode(), equalTo(200)); // промежуточная проверка
    }

    public static String verification(User user) {
        RequestSpecification request = RestAssured.given();
        request.spec(requestSpec); // указываем, какую спецификацию используем
        request.body("{\n" +
                "\"login\": \"" + user.getLogin() + "\",\n" +
                "\"code\": \"" + DbUtils.getVerificationCode() + "\"\n" +
                "}");  // передаём в теле объект, который будет преобразован в JSON
        Response response = request.post("/api/auth/verification");

        assertThat(response.statusCode(), equalTo(200)); // промежуточная проверка

        String token = response.path("token");
        return token;
    }

    public static ArrayList<Card> getCards(String token) {
        RequestSpecification request = RestAssured.given();
        request.spec(requestSpec); // указываем, какую спецификацию используем
        request.header("Authorization", "Bearer " + token);  // передаём в header'е объект, который будет преобразован в JSON

        Response response = request.get("/api/cards");

        assertThat(response.statusCode(), equalTo(200)); // промежуточная проверка

        String id1 = response.path("[0].id");
        String number1 = response.path("[0].number");
        int balance1 = response.path("[0].balance");
        Card card1 = new Card(id1, number1, balance1);

        String id2 = response.path("[1].id");
        String number2 = response.path("[1].number");
        int balance2 = response.path("[1].balance");
        Card card2 = new Card(id2, number2, balance2);

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(card1);
        cards.add(card2);
        return cards;
    }

    public static void transfer(String token, String from, String to, int amount) {
        RequestSpecification request = RestAssured.given();
        request.spec(requestSpec); // указываем, какую спецификацию используем
        request.header("Authorization", "Bearer " + token);  // передаём в header'е объект, который будет преобразован в JSON
        request.body("{\n" +
                "\"from\": \"" + from + "\",\n" +
                "\"to\": \"" + to + "\",\n" +
                "\"amount\": \"" + amount + "\"\n" +
                "}"); // передаём в теле объект, который будет преобразован в JSON

        Response response = request.post("/api/transfer");

        assertThat(response.statusCode(), equalTo(200)); // промежуточная проверка
    }
}
