public class Main {
        public static void main(String[] args) {
            TUI tui = new TUI();
            while (true) {
                String name = tui.getUserInput("Enter bot name (or 'exit' to quit)");
                if (name.equalsIgnoreCase("exit")) {
                    break;
                }
                if (!tui.validateInput(name, "^[a-zA-Z0-9_]+$")) {
                    tui.displayMessage("Invalid name. Please use only letters, numbers, and underscores.");
                    continue;
                }
                tui.displayMessage("Bot created with name: " + bot.getName());
            }
    }
}
