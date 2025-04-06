package esinf;

public class Ex01 {

    // a)
    public static String reverseString(String string) {

        if (string.length() < 2) return string;

        char c = string.charAt(0);
        String rest = string.substring(1);
        String restReversed = reverseString(rest);

        return restReversed + c;
    }

    // b)
    public static int product(int m, int n) {

        if (n == 1) {

            return m;

        } else if (n == 0 || m == 0) {

            return 0;
        }
        return m + product(m, n - 1);
    }

    // c)
    public static int greatestCommonDivisor(int numero1, int numero2) {

        if (numero2 == 0) return numero1;

        return greatestCommonDivisor(numero2, numero1 % numero2);
    }

    // d)
    public static int convertStringIntoInteger(String string) {

        if (string.isEmpty()) return 0;

        return string.charAt(string.length() - 1) - '0' + convertStringIntoInteger(string.substring(0, string.length() - 1)) * 10;
    }

    // ===================================================================================

    // e)
    public static boolean isNumberPalindrome(int number) {

        if (number < 0) return false;

        return isNumberPalindromeHelper(number, 0, number);
    }

    private static boolean isNumberPalindromeHelper(int original, int reversed, int number) {

        if (number == 0) {
            return original == reversed;
        }

        reversed = reversed * 10 + (number % 10);

        return isNumberPalindromeHelper(original, reversed, number / 10);
    }

    // ===================================================================================

    // f)
    public static int sum2D(int[][] matrix, int row, int column) {

        if (row == matrix.length - 1 && column == matrix[row].length - 1) {
            return matrix[row][column];
        }

        if (column == matrix[row].length - 1) {
            return matrix[row][column] + sum2D(matrix, row + 1, 0);
        }

        return matrix[row][column] + sum2D(matrix, row, column + 1);
    }
}
