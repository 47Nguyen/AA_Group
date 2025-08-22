import java.util.Arrays;

public class SecretCodeGuesser {
    static char[] alphabet = {'B','A','C','X','I','U'};
    public static void start() {
        SecretCode secret = new SecretCode();
        long startTime = System.nanoTime();

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
            return ;
        }

        //Letter count
        int[] counts = new int[alphabet.length];
        for(int i = 0; i < alphabet.length;i++){
            char[] attempt = new char[length];
            Arrays.fill(attempt, alphabet[i]);
            counts[i] = secret.guess(new String(attempt));
        }

        // Baseline
        int baseIndex = argMax(counts);
        char baseChar = alphabet[baseIndex];
        char[] working = new char[length];
        Arrays.fill(working, baseChar);
        int baseScore = counts[baseIndex];

        int[] remaining = Arrays.copyOf(counts, counts.length); // quota of each letter left
        boolean[] fixed = new boolean[length];

        // Delta testing
        for (int i = 0; i < length; i++) {
            if (fixed[i]) continue;

            int[] order = sortedIndicesByRemainingDesc(remaining);
            boolean solved = false;

            for (int index : order) {
                char c = alphabet[index];
                if (c == working[i] || remaining[index] == 0) continue;

                char old = working[i];
                working[i] = c;
                int score = secret.guess(new String(working));
                int delta = score - baseScore;

                if (delta == 1) { // c is correct
                    baseScore = score;
                    fixed[i] = true;
                    remaining[index]--;
                    solved = true;
                    break;
                } else if (delta == -1) { // old was correct
                    working[i] = old;
                    fixed[i] = true;
                    remaining[indexOf(old)]--;
                    solved = true;
                    break;
                } else { // delta == 0 → wrong guess
                    working[i] = old; // revert
                }
            }

            if (!solved) {
                // fallback: lock whatever is there
                fixed[i] = true;
                remaining[indexOf(working[i])]--;
            }
        }
    }
    static int argMax(int[] a) {
        int best = 0;
        for (int i = 1; i < a.length; i++)
            if (a[i] > a[best]) best = i;
        return best;
    }

    static int indexOf(char c) {
        for (int i = 0; i < alphabet.length; i++) if (alphabet[i] == c) return i;
        return -1;
    }

    static int[] sortedIndicesByRemainingDesc(int[] remaining) {
        int[] idx = {0,1,2,3,4,5};
        for (int i = 0; i < idx.length; i++) {
            int best = i;
            for (int j = i+1; j < idx.length; j++) {
                if (remaining[idx[j]] > remaining[idx[best]]) best = j;
            }
            int tmp = idx[i]; idx[i] = idx[best]; idx[best] = tmp;
        }
        return idx;
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