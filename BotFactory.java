import java.util.Arrays;
import java.util.List;

public class BotFactory {
    // Liste der verfügbaren Bot-Namen
    public List<String> getAvailableBotNames() {
        return Arrays.asList("weatherbot", "wikibot");
        // Weitere Bots hier ergänzen
    }

    // Erstellt eine neue Bot-Instanz anhand des Namens
    public IBot createBot(String name) {
        switch (name) {
            case "weatherbot":
                return new WeatherBot("WeatherBot");
            case "wikibot":
                return new WikipediaBot("WikiBot");
            // Weitere Bots hier ergänzen
            default:
                return null;
        }
    }
}