package netology.data;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

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

    public static void login(User user) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("login", user.getLogin());
        requestBody.put("password", user.getPassword());

        RequestSpecification request = RestAssured.given();
        request.spec(requestSpec);// указываем, какую спецификацию используем
        request.body(requestBody);  // передаём в теле объект, который будет преобразован в JSON
        Response response = request.post("/api/auth");

        assertThat(response.statusCode(), equalTo(200)); // промежуточная проверка
    }

    public static String verification(User user) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("login", user.getLogin());
        requestBody.put("code", DbUtils.getVerificationCode());

        RequestSpecification request = RestAssured.given();
        request.spec(requestSpec); // указываем, какую спецификацию используем
        request.body(requestBody);  // передаём в теле объект, который будет преобразован в JSON
        Response response = request.post("/api/auth/verification");

        assertThat(response.statusCode(), equalTo(200)); // промежуточная проверка

        String token = response.path("token");
        return token;
    }

    public static ResponseBody getCards(String token) {
        RequestSpecification request = RestAssured.given();
        request.spec(requestSpec); // указываем, какую спецификацию используем
        request.header("Authorization", "Bearer " + token);  // передаём в header'е объект, который будет преобразован в JSON

        Response response = request.get("/api/cards");

        assertThat(response.statusCode(), equalTo(200)); // промежуточная проверка

        var cards = response.body();
        return cards;
    }

    public static void transfer(String token, String from, String to, int amount) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("from", from);
        requestBody.put("to", to);
        requestBody.put("amount", amount);
        RequestSpecification request = RestAssured.given();
        request.spec(requestSpec); // указываем, какую спецификацию используем
        request.header("Authorization", "Bearer " + token);  // передаём в header'е объект, который будет преобразован в JSON
        request.body(requestBody); // передаём в теле объект, который будет преобразован в JSON

        Response response = request.post("/api/transfer");

        assertThat(response.statusCode(), equalTo(200)); // промежуточная проверка
    }
}
