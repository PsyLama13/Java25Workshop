package exercises.e06;

public class ConfigProcessor {

    public static String processWithConfig(AppConfig config) {
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
}
