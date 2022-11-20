import org.checkerframework.checker.units.qual.C;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


public class SchoologyNotifier extends TelegramLongPollingBot {
    private final String username = "";
    private final String token = "";
    private Commands commands = new Commands();

    @Override
    public void onUpdateReceived(Update update) {
        String message_text = commands.check(update.getMessage().getText());

        // String message_text = update.getMessage().getText();
        long chat_id = update.getMessage().getChatId();

        sendMsg(chat_id, message_text);
    }

    public synchronized void sendMsg(long chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        System.out.println("Sending message: \""+ s +"\"");
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
