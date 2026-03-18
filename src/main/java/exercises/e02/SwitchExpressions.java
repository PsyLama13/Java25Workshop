package exercises.e02;

public class SwitchExpressions {

    public static String dayType(Day day) {
        String result;
        switch (day) {
            case MONDAY: case TUESDAY: case WEDNESDAY: case THURSDAY: case FRIDAY:
                result = "Werktag"; break;
            case SATURDAY: case SUNDAY:
                result = "Wochenende"; break;
            default:
                result = "Unbekannt";
        }
        return result;
    }

    public static String seasonGreeting(Season season) {
        String result;
        switch (season) {
            case SPRING:
                System.out.println("Die Blumen blühen!"); result = "Frühlingsgefühle"; break;
            case SUMMER:
                System.out.println("Ab ins Schwimmbad!"); result = "Sommerfreude"; break;
            case AUTUMN:
                System.out.println("Die Blätter fallen."); result = "Herbststimmung"; break;
            case WINTER:
                System.out.println("Schnee!"); result = "Winterzauber"; break;
            default:
                result = "Unbekannt";
        }
        return result;
    }

    public static int daysInMonth(int month, boolean leapYear) {
        int days;
        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                days = 31; break;
            case 4: case 6: case 9: case 11:
                days = 30; break;
            case 2:
                if (leapYear) { days = 29; } else { days = 28; } break;
            default:
                throw new IllegalArgumentException("Ungültiger Monat: " + month);
        }
        return days;
    }
}
