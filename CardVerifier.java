public class CardVerifier {
	/** Return true if the card number is valid */
  public static boolean isValid(long number) {

		int size = getSize(number);

		if (size < 13 || size > 16) {
			// Credit card number is wrong size
			return false;
		}

		if (
			!prefixMatched(number, 4) // Not Visa
			&& !prefixMatched(number, 5) // Not Master
			&& !prefixMatched(number, 6) // Not Discover
			&& !prefixMatched(number, 37) // Not American Express
		) {
			// No valid prefix was used
			return false;
		}

		int evenDoubleSum = sumOfDoubleEvenPlace(number);
		int oddSum = sumOfOddPlace(number);

		int step4Sum = evenDoubleSum + oddSum;
		
		return step4Sum % 10 == 0;
	}

  /** Get the result from Step 2 */
  public static int sumOfDoubleEvenPlace(long number) {
		int sum = 0;

		number /= 10; // Integer division with 10 to move to first even digit

		while (number > 1) {
			int digit = (int) (number % 10); // Modulo 10 to get digit

			sum += getDigit(2 * digit);

			number /= 100; // Move to the left 2 digits
		}

		return sum;
	}

  /** Return this number if it is a single digit, otherwise,
   * return the sum of the two digits */
  public static int getDigit(int number) {
		if (number < 10) {
			return number;
		} else {
			int firstDigit = number % 10;
			int secondDigit = number / 10;

			return firstDigit + secondDigit;
		}
	}

  /** Return sum of odd-place digits in number */
  public static int sumOfOddPlace(long number) {
		int sum = 0;

		while (number >= 1) {
			int digit = (int) (number % 10);

			sum += digit;

			number /= 100; // Move left 2 digits
		}

		return sum;
	}

  /** Return true if the number d is a prefix for number */
  public static boolean prefixMatched(long number, int d) {
		int prefixSize = getSize(d);

		return getPrefix(number, prefixSize) == d;
	}

  /** Return the number of digits in d */
  public static int getSize(long d) {
		int numDigits = 0;

		while (d >= 1) {
			numDigits++;
			d /= 10;
		}

		return numDigits;
	}

  /** Return the first k number of digits from number. If the
   * number of digits in number is less than k, return number. */
  public static long getPrefix(long number, int k) {
		int size = getSize(number);
		long divisor = (long) Math.pow(10, size - k); // Number to divide by for digits.

		if (k >= size) {
			return number;
		} else {
			return number / divisor;
		}
	}

	public static void main(String[] args) {
		boolean valid = isValid(4388576018410707L); // Test example number

		if (valid) {
			System.out.println("Credit card is valid");
		} else {
			System.out.println("Credit card is NOT valid");
		}
	}
}