import java.util.Collection;
import java.util.List;

public class BotFactory {
    // Liste der verfügbaren Bot-Namen: Collection draus machen
    public Collection<String> getAvailableBotNames() {
        return List.of("weatherbot", "wikibot");
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