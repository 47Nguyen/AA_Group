import java.lang.reflect.Array;
import java.util.Arrays;

public class SecretCodeGuesser {
    public static void main(String[] args) {
        start();
    }
    public static void start() {
        SecretCode secret = new SecretCode();
        double startTime = System.currentTimeMillis();

        // Discover the length of the secret code
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

        // Discover code (optimize characters)
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

        // Print output
        double endTime = System.currentTimeMillis();
        String secretCode = new String(found); //Get the secret code

        System.out.println("Start time: " + startTime +" ms");
        System.out.println("Secret code found: " + secretCode);
        System.out.println("End time: " + startTime + " ms");
        System.out.println("Algorithm Total time: " + (endTime - startTime) + " ms");
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
