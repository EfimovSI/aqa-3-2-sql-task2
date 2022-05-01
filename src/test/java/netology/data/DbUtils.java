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
    public static String addNewUserWithCard(String userId) {
        User user = dataGenerator.generateUser(userId);
        Card card = dataGenerator.generateCard(userId);
//        user.getId(), user.getLogin(), user.getPassword(), user.getStatus()
        var addUserSQL = "INSERT INTO users (id, login, password, status) VALUES ();";
        var addCardSQL = ;
        var runner = new QueryRunner();
        String verificationCode;

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            var code = runner.query(conn, addUserSQL, new ScalarHandler<>());
            verificationCode = (String) code;
        }
        return verificationCode;
    }
}
