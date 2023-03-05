import org.checkerframework.checker.units.qual.C;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


public class SchoologyNotifier extends TelegramLongPollingBot {
    private final String username = "schoology-notifier-bot";
    private final String token = "";
    private static final Commands callCommand = new Commands();
    private String args[];
    private int argsSize;
    private boolean waitingforresponse = false;
    private int responseLength;
    private String responseType;

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        long chat_id = update.getMessage().getChatId();
        long user_id = update.getMessage().getFrom().getId();
        System.out.println(user_id);
        if (message.substring(0,1).equalsIgnoreCase("/")) {
            if (waitingforresponse) {
                sendMsg(chat_id, "Aborting /" + responseType + " because you used another command.");
                resetResponse();
            }
            String reply = callCommands(message, user_id);
            sendMsg(chat_id, reply);
        } else if (waitingforresponse && (responseType.equalsIgnoreCase("init"))) {
            try {
                argsSize++;
                args[argsSize - 1] = message;
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
            if (argsSize == responseLength) {
                sendMsg(chat_id, "All responses received. Now initializing...");
                try {
                    String reply = callCommand.initialize(args, user_id);
                    sendMsg(chat_id, reply);
                } catch (InvalidUserInputException e) {
                    e.printStackTrace();
                    sendMsg(chat_id, "One of your inputs were incorrect. Please try again.");
                    resetResponse();
                }
            } else {
                if (argsSize == 1)
                    sendMsg(chat_id, "Please input your Schoology domain.");
                if (argsSize == 2)
                    sendMsg(chat_id, "Please input your Schoology API Key.");
                if (argsSize == 3)
                    sendMsg(chat_id, "Please input your Schoology API Secret.");
            }
        } else { //default case
            sendMsg(chat_id, "Unknown command or input. Please try again.");
        }

        // String message_text = update.getMessage().getText();

    }


    public String callCommands(String message, long user_id) {
        String reply = "";
        switch(message.replace("/", "")) {
            case "help":
                System.out.println("Calling /help");
                reply = callCommand.help();
                break;
            case "init":
            case "initialize":
                System.out.println("Calling /initialize");
                if (!waitingforresponse) {
                    waitingforresponse = true;
                    responseLength = 4;
                    responseType = "init";
                    args = new String[responseLength];
                }
                reply = "Please input your Schoology user ID";
                break;
            case "overdue":
                System.out.println("Calling /overdue");
                reply = callCommand.overdue(user_id);
                break;
            case "settings":
                System.out.println("Calling /settings");
                reply = "geg3rt3r34yheue";
                break;
            case "temp":
                System.out.println("Calling /temp");
                reply = callCommand.temp(user_id);
                break;
            default:
                reply = "Unknown command. Please try again.";
                break;
        }
        return reply;
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

    public void resetResponse() {
        waitingforresponse = false;
        args = null;
        responseType = "";
        responseLength = 0;
        argsSize = 0;
    }

}
