import java.util.Arrays;

public class SecretCodeGuesser {
    public static void start() {
        SecretCode secret = new SecretCode();

        // Discover the length of the secret code - Time Complexity O(n^2)
        int length = -1;
        char[] found = null;
        int currentScore = -1;

        for (int len = 1; len <= 18; len++) { //Upper limit is 18
            char[] attempt = new char[len];
            Arrays.fill(attempt, 'B');
            int result = secret.guess(new String(attempt));

            if (result != -2) { // Found the len of the code
                length = len;
                found = attempt; // keep the successful attempt
                currentScore = result;  // reuse score
                break;
            }
        }

        if (length == -1) {
            System.out.println("Length invalid.");
            return;
        }

        // Search for secret code O(n).
        for (int i = 0; i < length; i++) { // Loop through i position of secret code
            for (int ord = 0; ord < 6; ord++) { //Try all the char set we know B,A,C,X,I,U
                char originalChar = found[i]; // remember old char
                char newChar = charOf(ord); // Pick char from the charOf functions

                if (originalChar == newChar) continue; // Skip if the char is the same
                found[i] = newChar; // Update the char at position i
                int score = secret.guess(new String(found)); // Check with the secret code

                if (score > currentScore) { // If score is greater than current score
                    currentScore = score; //Keep current score and move to next position [i+1]
                    break;
                } else {
                    found[i] = originalChar; // If score is lower, go back to original char
                }
            }
        }

    }


    static char charOf(int order) {
        if (order == 0) {
            return 'B';
        } else if (order == 1) {
            return 'A';
        } else if (order == 2) {
            return 'C';
        } else if (order == 3) {
            return 'X';
        } else if (order == 4) {
            return 'I';
        }
        return 'U';
    }

}
