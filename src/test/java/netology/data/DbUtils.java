package netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;

public class DbUtils {
    public DbUtils() {}
    private static DataGenerator dataGenerator = new DataGenerator();

    @SneakyThrows
    public static String getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes WHERE created = (SELECT max(created) FROM auth_codes);";
        var runner = new QueryRunner();
        String verificationCode;

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            var code = runner.query(conn, codeSQL, new ScalarHandler<>());
            verificationCode = (String) code;
        }
        return verificationCode;
    }

    @SneakyThrows
    public static void addNewUserWithCardToDb() {
        User user = dataGenerator.generateUser();

//        user.getId(), user.getLogin(), user.getPassword(), user.getStatus()
        var addUserSQL = "INSERT INTO users (login, password) VALUES (?, ?);";
        var getUserId = "SELECT id FROM users WHERE login = ?;";
        var addCardSQL = "INSERT INTO cards (login, password) VALUES (?, ?);";
        var runner = new QueryRunner();
        String login;

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            var code = runner.query(conn, addUserSQL, new ScalarHandler<>());
        }

        Card card = dataGenerator.generateCard(userId);

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            var code = runner.query(conn, addCardSQL, new ScalarHandler<>());
            verificationCode = (String) code;
        }
    }
}
