public class ChatApp {
    private static final String EXIT = "exit";
    private static final String LIST_BOTS = "list bots";
    private static final String ACTIVATE_BOT = "activate bot";
    private static final String DEACTIVATE_BOT = "deactivate bot";
    private static final String CALL_BOT = "call bot";
    private static final String LOAD_CHAT_DATA = "load chat data";


    BotController controller = new BotController();
    GUI gui = new TUI();
    DatabaseInterface db = new H2DatabaseConnector();

    public void run() {
        gui.displayMessage("Welcome to the Chat Application!");
        boolean login = false;
        String user="";
        while(!login) {
            gui.displayMessage("Please enter your username:");
            user = gui.getLoginInput();
            gui.displayMessage("Please enter your password:");
            String password = gui.getLoginInput();
            Users users = new Users();
            login = users.login(user, password);
            if (!login) {
                gui.displayMessage("Invalid username or password. Please try again.");
            } else {
                gui.displayMessage("Login successful! Welcome, " + user + "!");
            }
        }

        while (true) {
            gui.displayMessage("> ");
            UserInput input = gui.getUserInput();
            String output;
            switch (input.command()) {
                case EXIT:
                    gui.displayMessage("Exiting...");
                    db.disconnect();
                    return;
                case LIST_BOTS:
                    output = listBots();
                    break;
                case ACTIVATE_BOT:
                    output = activateBot(input.argument());
                    break;
                case DEACTIVATE_BOT:
                    output = deactivateBot(input.argument());
                    break;
                case CALL_BOT:
                    output = callBot(input.argument());
                    break;
                case LOAD_CHAT_DATA:
                    output = loadChatData(user);
                    break;
                default:
                    output = "Unknown command.";
                    break;
            }
            db.persistChat(user, input.command() + " " + input.argument(), output);    
            gui.displayMessage(output);
        }
    }

    private String listBots() {
        int i = 0;
        StringBuilder botList = new StringBuilder();
        botList.append("Available bots:\n");
        for (String botInfo : controller.listBots()) {
            i++;
            botList.append(i).append(". ").append(botInfo).append("\n");
        }
        return botList.toString();
    }

    private String activateBot(String input) {
        return(controller.activateBot(input));
    }

    private String deactivateBot(String input) {
        return(controller.deactivateBot(input));
    }

    private String callBot(String input) {
        String[] callParts = input.split(" ", 2);
        if (callParts.length == 2) {
            return(controller.callBot(callParts[0], callParts[1]));
        }
        return "Invalid command format.";
    }

    private String loadChatData(String user) {
        String[][] data = db.loadChatData(user);
        StringBuilder chat = new StringBuilder();
        for (String[] row : data) {
            if (row != null) {
                for (String field : row) {
                    chat.append(field + " | ");
                }
                chat.append("\n");
            }
        }
        return chat.toString();
    }
}
