public interface DatabaseInterface {
    public String connect();
    public String disconnect();
    public String persistChat(String botName, String status, String credentialsAPI);
    public String[][] loadChatData(String user);
}