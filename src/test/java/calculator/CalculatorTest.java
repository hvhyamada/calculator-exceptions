package calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private static final double EPS = 1e-9;
    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Nested
    @DisplayName("Базові операції")
    class BasicOperations {

        @Test
        void add() throws InvalidInputException {
            assertEquals(5.5, calculator.add(2.5, 3), EPS);
            assertEquals(-1, calculator.add(2, -3), EPS);
        }

        @Test
        void subtract() throws InvalidInputException {
            assertEquals(7, calculator.subtract(10, 3), EPS);
            assertEquals(-0.5, calculator.subtract(1, 1.5), EPS);
        }

        @Test
        void multiply() throws InvalidInputException {
            assertEquals(12, calculator.multiply(3, 4), EPS);
            assertEquals(0, calculator.multiply(123.45, 0), EPS);
        }

        @Test
        void divide() throws InvalidInputException {
            assertEquals(2.5, calculator.divide(5, 2), EPS);
            assertEquals(-4, calculator.divide(8, -2), EPS);
        }

        @Test
        void sqrt() throws InvalidInputException {
            assertEquals(3, calculator.sqrt(9), EPS);
            assertEquals(0, calculator.sqrt(0), EPS);
        }

        @Test
        void calculateBySymbol() throws InvalidInputException {
            assertEquals(5, calculator.calculate("+", 2, 3), EPS);
            assertEquals(-1, calculator.calculate("-", 2, 3), EPS);
            assertEquals(6, calculator.calculate("*", 2, 3), EPS);
            assertEquals(2, calculator.calculate("/", 6, 3), EPS);
            assertEquals(4, calculator.calculate(" SQRT ", 16, 0), EPS);
        }
    }

    @Nested
    @DisplayName("Ділення на нуль")
    class DivisionByZero {

        @Test
        void divideByZeroThrowsArithmeticException() {
            ArithmeticException e = assertThrows(ArithmeticException.class,
                    () -> calculator.divide(10, 0));
            assertEquals("Ділення на нуль неможливе", e.getMessage());
        }

        @Test
        void divideByNegativeZeroThrowsArithmeticException() {
            assertThrows(ArithmeticException.class, () -> calculator.divide(10, -0.0));
        }

        @Test
        void divideZeroByZeroThrowsArithmeticException() {
            assertThrows(ArithmeticException.class, () -> calculator.calculate("/", 0, 0));
        }
    }

    @Nested
    @DisplayName("Невірні вхідні дані (рядки замість чисел)")
    class InvalidStrings {

        @ParameterizedTest
        @ValueSource(strings = {"abc", "12a", "один", "1..2", "--5"})
        void textInsteadOfNumberThrowsInvalidInputException(String input) {
            InvalidInputException e = assertThrows(InvalidInputException.class,
                    () -> InputParser.parseNumber(input));
            assertInstanceOf(NumberFormatException.class, e.getCause());
        }

        @ParameterizedTest
        @ValueSource(strings = {"", "   "})
        void emptyInputThrowsInvalidInputException(String input) {
            assertThrows(InvalidInputException.class, () -> InputParser.parseNumber(input));
        }

        @Test
        void nullInputThrowsInvalidInputException() {
            assertThrows(InvalidInputException.class, () -> InputParser.parseNumber(null));
        }

        @Test
        void validNumbersAreParsed() throws InvalidInputException {
            assertEquals(42, InputParser.parseNumber("42"), EPS);
            assertEquals(-3.5, InputParser.parseNumber(" -3.5 "), EPS);
            assertEquals(2.75, InputParser.parseNumber("2,75"), EPS);
        }
    }

    @Nested
    @DisplayName("Власне виключення InvalidInputException")
    class CustomException {

        @Test
        void sqrtOfNegativeThrowsInvalidInputException() {
            InvalidInputException e = assertThrows(InvalidInputException.class,
                    () -> calculator.sqrt(-4));
            assertTrue(e.getMessage().contains("від'ємного"));
        }

        @Test
        void nanArgumentThrowsInvalidInputException() {
            assertThrows(InvalidInputException.class, () -> calculator.add(Double.NaN, 1));
        }

        @Test
        void infiniteArgumentThrowsInvalidInputException() {
            assertThrows(InvalidInputException.class,
                    () -> calculator.multiply(Double.POSITIVE_INFINITY, 2));
        }

        @ParameterizedTest
        @ValueSource(strings = {"%", "^", "plus", ""})
        void unknownOperationThrowsInvalidInputException(String op) {
            assertThrows(InvalidInputException.class, () -> calculator.calculate(op, 1, 2));
        }

        @Test
        void nullOperationThrowsInvalidInputException() {
            assertThrows(InvalidInputException.class, () -> calculator.calculate(null, 1, 2));
        }
    }
}
