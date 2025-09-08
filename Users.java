import java.util.HashMap;
import java.util.Map;

public class Users {
    // Vorab definierte Benutzer (username → password)
    private static final Map<String, String> userMap = new HashMap<>();

    static {
        userMap.put("kira", "Grinsekatze69");
        userMap.put("nils", "n1ls");
        userMap.put("katrin", "katrin123");
        userMap.put("admin", "admin");
        userMap.put("niclas", "FachwerkPenthouse2030");
    }

    // Prüft Login-Daten
    public boolean login(String username, String password) {
        return userMap.containsKey(username) && userMap.get(username).equals(password);
    }
}