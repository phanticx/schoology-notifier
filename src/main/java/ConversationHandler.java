import java.util.ArrayList;

public class ConversationHandler {
    private static ArrayList<Conversation> conversations = new ArrayList<Conversation>();

    static class Conversation {
        long telegramID;
        int responseLength;
        String responseType;
        String[] args;
        int argsSize;

        Conversation(long telegramID, int responseLength, String responseType, String[] args) {
            this.telegramID = telegramID;
            this.responseLength = responseLength;
            this.responseType = responseType;
            this.args = args;
            this.argsSize = 0;
        }

        void addArg(String arg) {
            args[argsSize] = arg;
        }
    }

    public static boolean userInConversation(long telegramID) {
        for (Conversation c : conversations) {
            if (c.telegramID == telegramID) {
                return true;
            }
        }
        return false;
    }

    public static String makeConversation(long telegramID, String message) {
        for (int i = 0; i < conversations.size(); i++) {
            if (conversations.get(i).telegramID == telegramID) {
                switch(conversations.get(i).responseType) {
                    case "init":
                    case "initialize":
                        return "oewpkjpoewkfwgwe";

                }
            }
        }
        return null;
    }


    public static void addConversation(long telegramID, int responseLength, String responseType, String[] args) {
        conversations.add(new Conversation(telegramID, responseLength, responseType, args));
    }

    public static void removeConversation(long telegramID) {
        for (int i = 0; i < conversations.size(); i++) {
            if (conversations.get(i).telegramID == telegramID) {
                conversations.remove(i);
            }
        }
    }

    public static String getConversationResponseType(long telegramID) {
        for (int i = 0; i < conversations.size(); i++) {
            if (conversations.get(i).telegramID == telegramID) {
                return conversations.get(i).responseType;
            }
        }
        return null;
    }

    public static int getConversationResponseLength(long telegramID) {
        for (int i = 0; i < conversations.size(); i++) {
            if (conversations.get(i).telegramID == telegramID) {
                return conversations.get(i).responseLength;
            }
        }
        return 0;
    }

    public static String[] getConversationArgs(long telegramID) {
        for (int i = 0; i < conversations.size(); i++) {
            if (conversations.get(i).telegramID == telegramID) {
                return conversations.get(i).args;
            }
        }
        return null;
    }

    public static int getConversationArgsLength(long telegramID) {
        for (int i = 0; i < conversations.size(); i++) {
            if (conversations.get(i).telegramID == telegramID) {
                return conversations.get(i).argsSize;
            }
        }
        return -1;
    }

    public static void addConversationArg(long telegramID, String arg) {
        for (int i = 0; i < conversations.size(); i++) {
            if (conversations.get(i).telegramID == telegramID) {
                conversations.get(i).args[conversations.get(i).argsSize] = arg;
                conversations.get(i).argsSize++;
            }
        }
    }
}


