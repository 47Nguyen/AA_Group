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
        int length = 0;
        int prevScore = 0;

        while (true) {
            length++;
            char[] attempt = new char[length];
            Arrays.fill(attempt, 'B');

            int score = secret.guess(new String(attempt));
            totalGuess++;

            if (score == prevScore) {
                length--;
                break;
            }
            prevScore = score;

            if (length >= 18) break; // safety cap
        }

        // Discover code
        char[] found = new char[length];
        Arrays.fill(found, 'B');
        int currentScore = prevScore;

        for (int i = 0; i < length; i++) {
            for (int ord = 0; ord < 6; ord++) {
                char c = charOf(ord);
                found[i] = c;

                int score = secret.guess(new String(found));
                totalGuess++;

                if (score > currentScore) {
                    currentScore = score;
                    break;
                }
            }
        }
        long endTime = System.currentTimeMillis();
        String secretCode = new String(found);


        System.out.println("Secret code found: " + secretCode);
        System.out.println("Total guesses: " + totalGuess);
        System.out.println("Time taken: " + (endTime - startTime) + " ms");

    }


    static int order(char c) {
        if (c == 'B') {
            return 0;
        } else if (c == 'A') {
            return 1;
        } else if (c == 'C') {
            return 2;
        } else if (c == 'X') {
            return 3;
        } else if (c == 'I') {
            return 4;
        }
        return 5;
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
