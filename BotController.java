import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BotController {
    private Map<String, IBot> activeBots = new HashMap<>();
    private BotFactory botFactory = new BotFactory();

    // Liste aller Bots mit Status
    public List<String> listBots() {
        List<String> result = new ArrayList<>();
        for (String name : botFactory.getAvailableBotNames()) {
            String status = activeBots.containsKey(name) ? "enabled" : "available";
            result.add(name + " (" + status + ")");
        }
        return result;
    }

    // Bot aktivieren (neue Instanz aus Factory)
    public String activateBot(String name) {
        if (!activeBots.containsKey(name)) {
            IBot bot = botFactory.createBot(name);
            if (bot != null) {
                activeBots.put(name, bot);
                bot.setStatus(true);
                return "bot @" + name + " activated";
            }
            return "Bot not found.";
        }
        return "Bot already active.";
    }

    // Bot deaktivieren (Referenz löschen)
    public String deactivateBot(String name) {
        IBot bot = activeBots.remove(name);
        if (bot != null) {
            bot.setStatus(false);
            return "bot @" + name + " deactivated";
        }
        return "Bot not active.";
    }

    // Bot aufrufen (nur wenn aktiv)
    public String callBot(String name, String message) {
        IBot bot = activeBots.get(name);
        if (bot != null && bot.getStatus().equals("Active")) {
            return bot.digestMessage(message);
        } else {
            return "Bot not found or inactive.";
        }
    }
}