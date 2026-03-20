# Java 25 Workshop – Setup-Anleitung


---

## 1. JDK 25 installieren

Für den Workshop wird **JDK 25** (Early Access) benötigt, da wir Preview-Features verwenden.

**Möglichkeiten zur Installation:**
- **Über IntelliJ**: `File → Project Structure → Project → SDK → Add SDK → Download JDK` → Version 25 auswählen (einfachster Weg)
- **Manuell**: [Oracle JDK 25 EA](https://jdk.java.net/25/) herunterladen und installieren
- **Via SDKMAN** (Linux/macOS): `sdk install java 25.ea-open`

**Installation prüfen:**

```bash
java --version
```

Die Ausgabe sollte `25` enthalten (z.B. `openjdk 25-ea ...`).

---

## 2. IDE einrichten

Es kann grundsätzlich jede Java-IDE verwendet werden. Hier die gängigsten Optionen:

### IntelliJ IDEA (empfohlen)

Version **2025.1 oder neuer** – bietet die beste Unterstützung für JDK 25 und Preview-Features.

1. **JDK konfigurieren**: `File → Project Structure → Project → SDK` → JDK 25 auswählen (oder direkt hier herunterladen, siehe Schritt 1)
2. **Language Level**: `File → Project Structure → Project → Language Level` → `25 (Preview)` auswählen
3. **Maven**: IntelliJ sollte die `pom.xml` automatisch importieren – prüfe, dass das Projekt als Maven-Projekt erkannt wird

### Eclipse

Version **2025-03 oder neuer** mit installiertem **Java 25 Support** Plugin (über den Eclipse Marketplace verfügbar). JDK 25 unter `Window → Preferences → Java → Installed JREs` hinzufügen und die Compiler-Compliance auf 25 mit aktiviertem Preview setzen.

### VS Code

Mit dem **Extension Pack for Java** (von Microsoft). In den Workspace-Settings sicherstellen, dass `java.configuration.runtimes` auf JDK 25 zeigt.

> **Tipp**: Wer unsicher ist, greift am besten zu IntelliJ – dort ist die Preview-Feature-Unterstützung am ausgereiftesten.

---

## 3. Maven

Das Projekt verwendet **Maven** als Build-Tool. Maven 3.9+ ist empfohlen.

**Installation prüfen:**

```bash
mvn --version
```

> IntelliJ bringt einen integrierten Maven mit – eine separate Installation ist optional, aber hilfreich für die Kommandozeile.

---

## 4. Repository klonen und Build testen

```bash
git clone https://github.com/PsyLama13/Java25Workshop.git
cd Java25Workshop
mvn compile
```

Der Build muss **erfolgreich** durchlaufen. Falls Fehler auftreten, prüfe:
- Ist JDK 25 als `JAVA_HOME` gesetzt?
- Wird die richtige Java-Version verwendet? (`mvn --version` zeigt die verwendete Java-Version an)

---

## 5. Tests ausführen

```bash
mvn test
```

Die Tests werden am Anfang **fehlschlagen** – das ist gewollt! Die Übungen bestehen darin, den Code so umzuschreiben, dass die Tests grün werden.

---

## Checkliste

- [ ] JDK 25 installiert (`java --version` zeigt 25)
- [ ] IDE installiert (IntelliJ, Eclipse oder VS Code)
- [ ] Repository geklont
- [ ] `mvn compile` läuft erfolgreich durch
- [ ] Projekt in der IDE geöffnet und als Maven-Projekt erkannt

---

## Workshop-Themen (Übersicht)

1. Records & Sealed Classes
2. Switch Expressions & Pattern Matching
3. Record Patterns & Unnamed Variables
4. Flexible Constructor Bodies *(Preview)*
5. Virtual Threads & Structured Concurrency
6. Scoped Values *(Preview)*
7. Sequenced Collections & Stream Gatherers

---

Bei Fragen oder Problemen mit dem Setup meldet euch gerne vorab, damit wir am Workshop-Tag direkt loslegen können.

Freundliche Grüsse

Martin & Yannick
