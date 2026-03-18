package exercises.e06;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Aufgabe: Refaktoriere von expliziter Parameterübergabe zu ScopedValues.
 *          Die Konfiguration soll implizit verfügbar sein,
 *          ohne sie durch alle Methoden durchreichen zu müssen.
 *
 * Bonus: Kombiniere ScopedValues mit verschachtelten Scopes.
 *        Zeige, wie ein ScopedValue in einem inneren Scope überschrieben (rebound) werden kann,
 *        und nach dem inneren Scope den alten Wert wieder hat.
 *
 * Hinweise:
 * - ScopedValue.where(KEY, value).call(() -> ...) für Rückgabewerte
 * - ScopedValues sind immutable innerhalb eines Scopes
 */
@DisplayName("Übung 6.2: Config Processing mit ScopedValues")
class E6_2_ConfigProcessorTest {

    @Test
    void verarbeitetMitConfig() {
        var config = new AppConfig("production", true, "de-CH");
        String result = ConfigProcessor.processWithConfig(config);
        assertEquals("[production/de-CH] transformed-validated-data", result);
    }

    @Test
    void verarbeitetOhneDebug() {
        var config = new AppConfig("staging", false, "en-US");
        String result = ConfigProcessor.processWithConfig(config);
        assertEquals("[staging/en-US] transformed-validated-data", result);
    }
}
