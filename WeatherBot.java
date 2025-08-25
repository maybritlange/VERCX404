public class WeatherBot implements IBot {
    private String name;
    private String status;
    private String credentialsAPI;

    public WeatherBot(String name) {
        this.name = name;
        this.status = "inactive";
        this.credentialsAPI = "";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String digestMessage(String message) {
        // Simulate processing the message and returning a weather response
        return "The weather in " + message + " is sunny with a high of 75°F.";
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(Boolean isActive) {
        this.status = isActive ? "active" : "inactive";
    }

    @Override
    public String getCredentialsAPI() {
        return credentialsAPI;
    }

    @Override
    public void setCredentialsAPI(String credentialsAPI) {
        this.credentialsAPI = credentialsAPI;
    }

    private String fetchWeatherData (String location) {
        // Placeholder for actual API call to fetch weather data
        return "Sunny, 75°F";
    }
    
}
