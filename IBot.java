public interface IBot {
    String getName();
    String digestMessage(String message);
    String getStatus();
    void setStatus(Boolean isActive);
    String getCredentialsAPI();
    void setCredentialsAPI(String credentialsAPI);
}
