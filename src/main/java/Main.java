import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

    public static void main(String[] args) {
        try {
            TelegramBotsApi telegramBotsAPI = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsAPI.registerBot(new SchoologyNotifier());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


}