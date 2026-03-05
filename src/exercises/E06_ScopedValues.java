package exercises;

/**
 * Übung 6: Scoped Values
 *
 * Aufgabe: Refaktoriere den Code von ThreadLocal zu ScopedValue.
 *          Eliminiere die Probleme von ThreadLocal (Memory Leaks, Mutierbarkeit).
 *
 * Hinweise:
 * - ScopedValue.newInstance() statt new ThreadLocal<>()
 * - ScopedValue.where(KEY, value).run(() -> ...) für den Scope
 * - ScopedValue.where(KEY, value).call(() -> ...) für Rückgabewerte
 * - ScopedValues sind immutable innerhalb eines Scopes
 * - Preview-Feature (--enable-preview)
 */
public class E06_ScopedValues {

    // ========================================================================
    // TODO 1: Refaktoriere von ThreadLocal zu ScopedValue.
    //         Eliminiere das manuelle remove() und die Mutierbarkeit.
    // ========================================================================

    // Alt: ThreadLocal für Request-Kontext
    static final ThreadLocal<String> CURRENT_USER = new ThreadLocal<>();
    static final ThreadLocal<String> REQUEST_ID   = new ThreadLocal<>();

    static void handleRequest(String user, String requestId) {
        CURRENT_USER.set(user);
        REQUEST_ID.set(requestId);
        try {
            processRequest();
        } finally {
            // Muss manuell aufgeräumt werden – vergisst man das, gibt's Memory Leaks!
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

    // ========================================================================
    // TODO 2: Refaktoriere von expliziter Parameterübergabe zu ScopedValues.
    //         Die Konfiguration soll implizit verfügbar sein,
    //         ohne sie durch alle Methoden durchreichen zu müssen.
    // ========================================================================

    record AppConfig(String environment, boolean debugMode, String locale) {}

    static String processWithConfig(AppConfig config) {
        String validated    = validateWithConfig(config);
        String transformed  = transformWithConfig(config, validated);
        return formatWithConfig(config, transformed);
    }

    static String validateWithConfig(AppConfig config) {
        if (config.debugMode()) {
            System.out.println("[DEBUG] Validierung in " + config.environment());
        }
        return "validated-data";
    }

    static String transformWithConfig(AppConfig config, String data) {
        if (config.debugMode()) {
            System.out.println("[DEBUG] Transformation in " + config.locale());
        }
        return "transformed-" + data;
    }

    static String formatWithConfig(AppConfig config, String data) {
        return String.format("[%s/%s] %s", config.environment(), config.locale(), data);
    }

    // ========================================================================
    // TODO 3 (Bonus): Kombiniere ScopedValues mit verschachtelten Scopes.
    //         Zeige, wie ein ScopedValue in einem inneren Scope überschrieben
    //         (rebound) werden kann, und nach dem inneren Scope den alten
    //         Wert wieder hat.
    //
    //         Implementiere eine Methode die:
    //         1. Einen äusseren Scope mit LogLevel "INFO" setzt
    //         2. Darin einen inneren Scope mit LogLevel "DEBUG" setzt
    //         3. Zeigt, dass nach dem inneren Scope wieder "INFO" gilt
    // ========================================================================

    // ========================================================================
    // Testcode
    // ========================================================================
    public static void main(String[] args) {
        System.out.println("=== Request Handling ===");
        handleRequest("Alice", "REQ-001");
        handleRequest("Bob",   "REQ-002");

        System.out.println();

        System.out.println("=== Config Processing ===");
        var config = new AppConfig("production", true, "de-CH");
        String result = processWithConfig(config);
        System.out.println("Ergebnis: " + result);
    }
}
