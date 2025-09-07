import java.util.Scanner;

public class ChatApp {
    private static final String EXIT = "exit";
    private static final String LIST_BOTS = "list bots";
    private static final String ACTIVATE_BOT = "activate bot";
    private static final String DEACTIVATE_BOT = "deactivate bot";
    private static final String CALL_BOT = "call bot";

    BotController controller = new BotController();
    GUI gui = new TUI();

    public void run() {
        while (true) {
            gui.displayMessage("> ");
            UserInput input = gui.getUserInput();
            String output;
            switch (input.command()) {
                case EXIT:
                    gui.displayMessage("Exiting...");
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
                default:
                    output = "Unknown command.";
                    break;
            }
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
}
