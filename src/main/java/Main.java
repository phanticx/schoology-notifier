import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Arrays;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        final Schoology schoology = new Schoology();
        // User user = schoology.initializeUser(userID, userDomain, userAPIKey, userAPISecret);
        // System.out.println(user.getCourses()[0].getAssignments()[user.getCourses()[0].getAssignments().length - 1].getDue());
        // System.out.println(user.getCourses()[0].getAssignments()[user.getCourses()[0].getAssignments().length - 1].isSubmitted());
       try {
            TelegramBotsApi telegramBotsAPI = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsAPI.registerBot(new SchoologyNotifier());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}