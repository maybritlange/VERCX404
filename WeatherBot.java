import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class WeatherBot implements IBot {
    private String name;
    private Boolean isActive;
    private String credentialsAPI; 

    public WeatherBot(String name) {
        this.name = name;
        this.isActive = true;
        this.credentialsAPI = APIConfig.OPENWEATHER_API_KEY; 
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String digestMessage(String message) {
        // Nachricht wird als Stadtname interpretiert
        return fetchWeather(message.trim());
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

    private String fetchWeather(String city) {
    if (credentialsAPI == null || credentialsAPI.isEmpty()) {
        return "API-Key fehlt.";
    }

    try {
        // Stadtname für URL formatieren
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);

        String urlString = String.format(
            "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric&lang=de",
            encodedCity, credentialsAPI
        );

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();

        if (responseCode == 401) { // Unauthorized
            return "Ungültiger API-Key.";
        } else if (responseCode == 404) { // Not Found
            return "Stadt nicht gefunden: " + city;
        } else if (responseCode != 200) {
            return "Fehler beim Abrufen der Wetterdaten. HTTP-Code: " + responseCode;
        }

        // Antwort einlesen
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // JSON parsen
        JSONObject json = new JSONObject(response.toString());
        String description = json.getJSONArray("weather").getJSONObject(0).getString("description");
        double temp = json.getJSONObject("main").getDouble("temp");

        return String.format("Wetter in %s: %.1f°C, %s", city, temp, description);

    } catch (Exception e) {
        e.printStackTrace(); // Optional, um den Fehler genauer zu sehen
        return "Fehler beim Abrufen der Wetterdaten.";
    }
}
}