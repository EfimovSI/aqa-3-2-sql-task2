package netology.data;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataGenerator {

    private static final Faker faker = new Faker(new Locale("ru"));

    public User generateUser(String id) {
        String login = faker.name().username();
        return new User(id, login, "qwerty123", "active");
    }

    public Card generateCard(String userId) {
        String id = String.valueOf(Integer.parseInt(userId) + 5000);
        String number = String.valueOf(5559_0000_0000_0000L + Integer.parseInt(userId));
        return new Card(id, userId, number, 1000000);
    }
}
