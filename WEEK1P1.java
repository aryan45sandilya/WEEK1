import java.util.*;

public class WEEK1P1 {

    // username -> userId
    static HashMap<String, Integer> usernameMap = new HashMap<>();

    // username -> attempt frequency
    static HashMap<String, Integer> attemptFrequency = new HashMap<>();

    // Check if username is available
    public static boolean checkAvailability(String username) {

        // update attempt frequency
        attemptFrequency.put(username,
                attemptFrequency.getOrDefault(username, 0) + 1);

        if (usernameMap.containsKey(username)) {
            return false;
        }
        return true;
    }

    // Register new user
    public static void registerUser(String username, int userId) {

        if (!usernameMap.containsKey(username)) {
            usernameMap.put(username, userId);
            System.out.println("User registered: " + username);
        } else {
            System.out.println("Username already taken");
        }
    }

    // Suggest alternative usernames
    public static List<String> suggestAlternatives(String username) {

        List<String> suggestions = new ArrayList<>();

        for (int i = 1; i <= 3; i++) {
            String suggestion = username + i;

            if (!usernameMap.containsKey(suggestion)) {
                suggestions.add(suggestion);
            }
        }

        if (username.contains("_")) {
            String alt = username.replace("_", ".");
            if (!usernameMap.containsKey(alt)) {
                suggestions.add(alt);
            }
        }

        return suggestions;
    }

    // Find most attempted username
    public static String getMostAttempted() {

        String result = "";
        int max = 0;

        for (Map.Entry<String, Integer> entry : attemptFrequency.entrySet()) {

            if (entry.getValue() > max) {
                max = entry.getValue();
                result = entry.getKey();
            }
        }

        return result + " (" + max + " attempts)";
    }

    // Main method
    public static void main(String[] args) {

        // register existing user
        registerUser("john_doe", 101);

        // check availability
        System.out.println("john_doe available: " + checkAvailability("john_doe"));
        System.out.println("jane_smith available: " + checkAvailability("jane_smith"));

        // suggestions
        System.out.println("Suggestions: " + suggestAlternatives("john_doe"));

        // most attempted username
        System.out.println("Most attempted: " + getMostAttempted());
    }
}