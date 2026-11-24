package calculator;

/**
 * Власне виключення, яке викидається при некоректних вхідних даних
 * (від'ємне число під квадратним коренем, NaN, нескінченність, текст замість числа тощо).
 */
public class InvalidInputException extends Exception {

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
