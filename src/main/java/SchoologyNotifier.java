import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;


public class SchoologyNotifier extends TelegramLongPollingBot {
    private final String username = "schoology-notifier-bot";
    private final String token = "5647156605:AAFN_rIhzElnUj6HsM5nIoOnokdSUApuSYg";
    private static final Commands callCommand = new Commands();

    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        long chat_id = update.getMessage().getChatId();
        long user_id = update.getMessage().getFrom().getId();
        System.out.println("Message from Telegram ID: " + user_id);
        if (message.substring(0,1).equalsIgnoreCase("/")) {
            if (ConversationHandler.userInConversation(user_id)) {
                sendMsg(chat_id, "Aborting /" + ConversationHandler.getConversationResponseType(user_id) + " because you used another command.");
                ConversationHandler.removeConversation(user_id);
            }
            sendMsg(chat_id, callCommands(message, user_id));
        } else if (ConversationHandler.userInConversation(user_id) && (ConversationHandler.getConversationResponseType(user_id).equalsIgnoreCase("init"))) {
                ConversationHandler.addConversationArg(user_id, message);
            if (ConversationHandler.getConversationArgsLength(user_id) == ConversationHandler.getConversationResponseLength(user_id)) {
                sendMsg(chat_id, "All responses received. Now initializing...");
                try {
                    String reply = callCommand.initialize(ConversationHandler.getConversationArgs(user_id), user_id);
                    sendMsg(chat_id, reply);
                    ConversationHandler.removeConversation(user_id);
                } catch (InvalidUserInputException e) {
                    e.printStackTrace();
                    sendMsg(chat_id, "One or multiple of your inputs were invalid. Please try again.");
                    ConversationHandler.removeConversation(user_id);
                }
            } else {
                if (ConversationHandler.getConversationArgsLength(user_id) == 1)
                    sendMsg(chat_id, "Please input your Schoology domain.");
                if (ConversationHandler.getConversationArgsLength(user_id) == 2)
                    sendMsg(chat_id, "Please input your Schoology API Key.");
                if (ConversationHandler.getConversationArgsLength(user_id) == 3)
                    sendMsg(chat_id, "Please input your Schoology API Secret.");
            }
        } else { //default case
            sendMsg(chat_id, "Unknown command or input. Please try again.");
        }

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
                if (!ConversationHandler.userInConversation(user_id)) {
                    ConversationHandler.addConversation(user_id, 4, "init", new String[4]);
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
}
