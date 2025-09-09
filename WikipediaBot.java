import org.fastily.jwiki.core.Wiki;

public class WikipediaBot implements IBot {
    private String name;
    private Boolean isActive;
    private String credentialsAPI;

    public WikipediaBot(String name) {
        this.name = name;
        this.isActive = true;
        this.credentialsAPI = "";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String digestMessage(String message) {
        return ("Summary of '" + message + "' from Wikipedia:"
            + fetchWikipediaSummary(message));
    }

    @Override
    public String getStatus() {
        return isActive ? "Active" : "Inactive";
    }

    @Override
    public void setStatus(Boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String getCredentialsAPI() {
        return credentialsAPI;
    }

    @Override
    public void setCredentialsAPI(String credentialsAPI) {
        this.credentialsAPI = credentialsAPI;
    }

    private String fetchWikipediaSummary(String searchTerm) {
        Wiki wiki = new Wiki.Builder()
                        .withDomain("de.wikipedia.org")
                        .build();
        try {
            String summary = wiki.getTextExtract(searchTerm);
            return summary != null && !summary.isEmpty() ? summary : "Keine Zusammenfassung gefunden.";
        } catch (Exception e) {
            return "Fehler beim Abrufen der Zusammenfassung.";
        }
    }
}
