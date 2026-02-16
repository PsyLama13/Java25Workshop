package exercises;

/**
 * Übung 4: Switch Expressions
 *
 * Aufgabe: Refaktoriere die alten switch-Statements zu modernen Switch Expressions
 *          mit Arrow-Syntax und yield.
 *
 * Hinweise:
 * - Switch Expression gibt einen Wert zurueck (kein break noetig)
 * - Arrow-Syntax (->) verhindert Fall-Through
 * - yield fuer mehrzeilige Bloecke
 * - Mehrere Cases mit Komma kombinierbar
 */
public class E04_SwitchExpressions {

    enum Day { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }

    enum Season { SPRING, SUMMER, AUTUMN, WINTER }

    // ========================================================================
    // TODO 1: Refaktoriere zu einer Switch Expression
    // ========================================================================
    static String dayType(Day day) {
        String result;
        switch (day) {
            case MONDAY:
            case TUESDAY:
            case WEDNESDAY:
            case THURSDAY:
            case FRIDAY:
                result = "Werktag";
                break;
            case SATURDAY:
            case SUNDAY:
                result = "Wochenende";
                break;
            default:
                result = "Unbekannt";
        }
        return result;
    }

    // ========================================================================
    // TODO 2: Refaktoriere zu einer Switch Expression mit yield
    //         fuer die mehrzeiligen Faelle
    // ========================================================================
    static String seasonGreeting(Season season) {
        String result;
        switch (season) {
            case SPRING:
                System.out.println("Die Blumen blühen!");
                result = "Frühlingsgefühle";
                break;
            case SUMMER:
                System.out.println("Ab ins Schwimmbad!");
                result = "Sommerfreude";
                break;
            case AUTUMN:
                System.out.println("Die Blätter fallen.");
                result = "Herbststimmung";
                break;
            case WINTER:
                System.out.println("Schnee!");
                result = "Winterzauber";
                break;
            default:
                result = "Unbekannt";
        }
        return result;
    }

    // ========================================================================
    // TODO 3: Refaktoriere zu einer Switch Expression
    // ========================================================================
    static int daysInMonth(int month, boolean leapYear) {
        int days;
        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                days = 31;
                break;
            case 4: case 6: case 9: case 11:
                days = 30;
                break;
            case 2:
                if (leapYear) {
                    days = 29;
                } else {
                    days = 28;
                }
                break;
            default:
                throw new IllegalArgumentException("Ungültiger Monat: " + month);
        }
        return days;
    }

    // ========================================================================
    // TODO 4: Refaktoriere zu einer Switch Expression.
    //         Berechne den Preis basierend auf Kundenkategorie.
    // ========================================================================
    static double calculateDiscount(String category, double price) {
        double discount;
        switch (category) {
            case "STANDARD":
                discount = 0;
                break;
            case "PREMIUM":
                discount = 0.1;
                break;
            case "VIP":
                discount = 0.2;
                break;
            case "EMPLOYEE":
                discount = 0.3;
                break;
            default:
                throw new IllegalArgumentException("Unbekannte Kategorie: " + category);
        }
        return price * (1 - discount);
    }

    // ========================================================================
    // Testcode
    // ========================================================================
    public static void main(String[] args) {
        // Test dayType
        System.out.println("Montag: " + dayType(Day.MONDAY));
        System.out.println("Samstag: " + dayType(Day.SATURDAY));

        System.out.println();

        // Test seasonGreeting
        System.out.println("Frühling: " + seasonGreeting(Season.SPRING));
        System.out.println("Winter: " + seasonGreeting(Season.WINTER));

        System.out.println();

        // Test daysInMonth
        System.out.println("Januar: " + daysInMonth(1, false) + " Tage");
        System.out.println("Februar (Schaltjahr): " + daysInMonth(2, true) + " Tage");
        System.out.println("April: " + daysInMonth(4, false) + " Tage");

        System.out.println();

        // Test calculateDiscount
        System.out.println("Standard 100: " + calculateDiscount("STANDARD", 100));
        System.out.println("VIP 100: " + calculateDiscount("VIP", 100));
        System.out.println("Employee 100: " + calculateDiscount("EMPLOYEE", 100));
    }
}
