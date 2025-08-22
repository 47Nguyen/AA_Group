import java.util.Arrays;

public class SecretCodeGuesser {
    public static void main(String[] args) {
        for (int i = 0; i < 10;i++){
            start();
        }
    }
    public static void start() {
        SecretCode secret = new SecretCode();
        double startTime = System.currentTimeMillis();

        //1.  Discover the length of the secret code - Time Complexity O(n^2)
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

        //2. Determine the secret code
        // Letter count

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
