package exercises.e01;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Aufgabe: Refaktoriere die Command-Klassenhierarchie zu einem sealed interface mit Records.
 *          Behalte die Methode execute() als Interface-Default oder in den Records.
 */
@DisplayName("Übung 1.5: Command als sealed interface mit Records")
class E1_5_CommandTest {

    @Test
    void saveCommandExecute() {
        var cmd = new SaveCommand("document.txt");
        assertEquals("Saving to document.txt", cmd.execute());
    }

    @Test
    void deleteCommandExecute() {
        var cmd = new DeleteCommand("temp.log");
        assertEquals("Deleting temp.log", cmd.execute());
    }

    @Test
    void undoCommandExecute() {
        var cmd = new UndoCommand();
        assertEquals("Undoing last action", cmd.execute());
    }
}
