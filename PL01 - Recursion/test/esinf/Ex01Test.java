package esinf;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class Ex01Test {

    @Test
    void reverseString() {

        String input = "asdf";

        String expected = "fdsa";

        String result = Ex01.reverseString(input);

        assertEquals(expected, result);
    }

    @Test
    void product() {

        int m = 5;
        int n = 3;

        int expected = m * n;

        int result = Ex01.product(m, n);

        assertEquals(expected, result);
    }

    @Test
    void gratestCommonDivisor() {

        int numero1 = 48;
        int numero2 = 30;

        int expected = 6;

        int result = Ex01.greatestCommonDivisor(numero1, numero2);

        assertEquals(expected, result);
    }

    @Test
    void convertStringIntoIntegerTest() {

        String input = "123456";

        int expected = 123456;

        int result = Ex01.convertStringIntoInteger(input);

        assertEquals(expected, result);
    }

    @Test
    void isNumberPalindromeTest() {

        int input = 440044;

        boolean expected = true;

        boolean result = Ex01.isNumberPalindrome(input);

        assertEquals(expected, result);
        assertTrue(result);
    }

    @Test
    void isNumberPalindromeTest2() {

        int input = 123;

        boolean expected = false;

        boolean result = Ex01.isNumberPalindrome(input);

        assertEquals(expected, result);
        assertFalse(result);
    }

    @Test
    void testSum2D() {

        int[][] array = {
                {1, 2, 3},
                {4, 5, 6},
        };

        int expected = 21;

        int result = Ex01.sum2D(array, 0, 0);

        assertEquals(expected, result);
    }
}