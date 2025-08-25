import java.util.Scanner;

public class TUI {
    Scanner scanner;

    public TUI() {
        scanner = new Scanner(System.in);
    }

    public String getUserInput(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public boolean validateInput(String input, String pattern) {
        return input.matches(pattern);
    }
}
