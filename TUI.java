import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TUI implements GUI {
    Scanner scanner;
    Pattern commandPattern = Pattern.compile("^(exit|list bots|activate bot|deactivate bot|call bot|load chat data)(.*)$");

    public TUI() {
        scanner = new Scanner(System.in);
    }

    public UserInput getUserInput() {
        String input = scanner.nextLine();
        Matcher matcher = commandPattern.matcher(input);
        if (matcher.matches()) {
            if (matcher.groupCount() == 2) {
                String command = matcher.group(1).trim();
                String argument = matcher.group(2).trim();
                return new UserInput(command, argument);
            } else {
                return new UserInput(matcher.group(1).trim(), "");
            }
        } else {
            displayMessage("Invalid command format.");
            return getUserInput();
        }
    }

    public String getLoginInput(){
        return scanner.nextLine();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void close() {
        scanner.close();
    }
}
