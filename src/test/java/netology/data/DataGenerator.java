package netology.data;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataGenerator {

    private static final Faker faker = new Faker(new Locale("ru"));

    public User generateUser() {
        String login = faker.name().username();
        return new User(login, "qwerty123");
    }

    public Card generateCard(String userId) {
        String number = String.valueOf(5559_0000_0000_0000L + Integer.parseInt(userId));
        return new Card(userId, number, 1000000);
    }
}
