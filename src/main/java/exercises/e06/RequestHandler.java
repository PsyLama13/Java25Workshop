package exercises.e06;

public class RequestHandler {

    static final ThreadLocal<String> CURRENT_USER = new ThreadLocal<>();
    static final ThreadLocal<String> REQUEST_ID   = new ThreadLocal<>();

    public static void handleRequest(String user, String requestId) {
        CURRENT_USER.set(user);
        REQUEST_ID.set(requestId);
        try {
            processRequest();
        } finally {
            CURRENT_USER.remove();
            REQUEST_ID.remove();
        }
    }

    static void processRequest() {
        String user      = CURRENT_USER.get();
        String requestId = REQUEST_ID.get();
        System.out.println("[" + requestId + "] Verarbeite Request für " + user);
        saveToDatabase();
    }

    static void saveToDatabase() {
        String user      = CURRENT_USER.get();
        String requestId = REQUEST_ID.get();
        System.out.println("[" + requestId + "] Speichere Daten für " + user);
    }
}
