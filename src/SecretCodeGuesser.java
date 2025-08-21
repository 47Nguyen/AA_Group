import java.lang.reflect.Array;
import java.util.Arrays;

public class SecretCodeGuesser {
    public static void main(String[] args) {
        start();
    }
    public static void start(){
        SecretCode secret = new SecretCode();
        long startTime = System.currentTimeMillis();
        int totalGuess = 0; //lower the better

        // Find the length
        int length = -1;

        for (int len = 1; len <= 18; len++) {
            char[] attempt = new char[len];
            Arrays.fill(attempt, 'B');

            int result = secret.guess(new String(attempt));
            totalGuess++;

            if (result != -2) { // not a "wrong length" response
                length = len;
                break;
            }
        }

        if (length == -1) {
            System.out.println("Failed to determine secret code length.");
            return;
        }
        // Discover code
        char[] found = new char[length];
        Arrays.fill(found, 'B'); // initial guess
        int currentScore = secret.guess(new String(found));
        totalGuess++;

        for (int i = 0; i < length; i++) {
            for (int ord = 0; ord < 6; ord++) {
                char originalChar = found[i]; // remember old char
                char newChar = charOf(ord);

                if (originalChar == newChar) continue; // skip duplicate trial

                found[i] = newChar;
                int score = secret.guess(new String(found));
                totalGuess++;

                if (score > currentScore) {
                    // improvement → lock the new char
                    currentScore = score;
                    break;
                } else {
                    // no improvement → revert
                    found[i] = originalChar;
                }
            }
        }

        // Show results
        long endTime = System.currentTimeMillis();
        String secretCode = new String(found);

        System.out.println("Secret code found: " + secretCode);
        System.out.println("Total guesses: " + totalGuess);
        System.out.println("Time taken: " + (endTime - startTime) + " ms");

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
