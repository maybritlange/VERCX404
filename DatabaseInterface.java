public interface DatabaseInterface {
    public void connect();
    public void disconnect();
    public void persistChat(String botName, String status, String credentialsAPI);
    public String[][] loadChatData(String user);
}
