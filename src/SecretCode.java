public class SecretCode {
	private final String correctCode;
	private long counter;

	public SecretCode() {
		// for the real test, your program will not know this
//    correctCode = "B"; // Best Case
//    correctCode = "BACXIUBACXIUBA"; // Average Case
		correctCode = "UUUUUUUUUUUUUUUUUU"; // Worse Case

		counter = 0;
	}

	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < 1_000;i++){
			SecretCodeGuesser2.start();
		}


		//Length = 18
		// n = 10,000,000 time = 11,249ms
		// n = 20,000,000 time = 21,773ms

		// Length = 100
		// n 20,000,000 = 22,353 ms
		// n 10,000,000 = 11,026 ms
		// n = 5,000,000 , time = 5643 ms
		// n = 500,000, times = 676 ms
		// n = 5,000, time = 35 ms

		long t2 = System.currentTimeMillis();
		System.out.println("Time taken: " + (t2 - t1) + " ms");
	}

	// Returns
	// -2 : if length of guessedCode is wrong
	// -1 : if guessedCode contains invalid characters
	// >=0 : number of correct characters in correct positions
	public int guess(String guessedCode) {
		counter++;
		// validation
		for (int i = 0; i < guessedCode.length(); i++) {
			char c = guessedCode.charAt(i);
			if (c != 'B' && c != 'A' && c != 'C' && c != 'X' && c != 'I' && c != 'U') {
				return -1;
			}
		}

		if (guessedCode.length() != correctCode.length()) {
			return -2;
		}

		int matched = 0;
		for (int i = 0; i < correctCode.length(); i++) {
			if (guessedCode.charAt(i) == correctCode.charAt(i)) {
				matched++;
			}
		}

		if (matched == correctCode.length()) {
			System.out.println("Number of guesses: " + counter);
		}
		return matched;
	}
}
