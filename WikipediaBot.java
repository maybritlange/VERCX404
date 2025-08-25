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
        // Simulate fetching a summary from Wikipedia
        return "Summary of '" + message + "' from Wikipedia.";
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

    private String fetchWikipediaSummary(String topic) {
        // Placeholder for actual API call to fetch Wikipedia summary
        return "This is a summary of " + topic + " from Wikipedia.";
    }
    
}
