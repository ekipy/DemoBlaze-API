package demoblaze.util;

public class StepNameTracker {
    private static final ThreadLocal<String> stepName = new ThreadLocal<>();

    public static void set(String name) {
        stepName.set(name);
    }

    public static String get() {
        return stepName.get();
    }

    public static void clear() {
        stepName.remove();
    }
}
