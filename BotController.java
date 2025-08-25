import java.util.Map;

public class BotController {
    private Map<String, IBot> bots;

    public BotController(Map<String, IBot> bots) {
        this.bots = bots;
    }

    public String callBot(String name, String message) {
        IBot bot = bots.get(name);
        if (bot != null) {
            return bot.respond(message);
        } else {
            return "Bot not found.";
        }
    }
}
