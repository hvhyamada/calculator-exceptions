package calculator;

/**
 * Калькулятор з базовими арифметичними операціями.
 * Кожен метод приймає параметри типу double і повертає результат типу double.
 */
public class Calculator {

    /** Додавання. */
    public double add(double a, double b) throws InvalidInputException {
        validate(a, "a");
        validate(b, "b");
        return a + b;
    }

    /** Віднімання. */
    public double subtract(double a, double b) throws InvalidInputException {
        validate(a, "a");
        validate(b, "b");
        return a - b;
    }

    /** Множення. */
    public double multiply(double a, double b) throws InvalidInputException {
        validate(a, "a");
        validate(b, "b");
        return a * b;
    }

    /**
     * Ділення.
     *
     * @throws ArithmeticException якщо дільник дорівнює нулю
     */
    public double divide(double a, double b) throws InvalidInputException {
        validate(a, "a");
        validate(b, "b");
        if (b == 0.0) {
            throw new ArithmeticException("Ділення на нуль неможливе");
        }
        return a / b;
    }

    /**
     * Квадратний корінь.
     *
     * @throws InvalidInputException якщо число від'ємне
     */
    public double sqrt(double a) throws InvalidInputException {
        validate(a, "a");
        if (a < 0) {
            throw new InvalidInputException(
                    "Неможливо обчислити квадратний корінь з від'ємного числа: " + a);
        }
        return Math.sqrt(a);
    }

    /**
     * Виконує операцію за її символом: +, -, *, /, sqrt.
     * Для sqrt використовується лише перше число.
     */
    public double calculate(String operation, double a, double b) throws InvalidInputException {
        if (operation == null) {
            throw new InvalidInputException("Операцію не вказано");
        }
        return switch (operation.trim().toLowerCase()) {
            case "+" -> add(a, b);
            case "-" -> subtract(a, b);
            case "*" -> multiply(a, b);
            case "/" -> divide(a, b);
            case "sqrt" -> sqrt(a);
            default -> throw new InvalidInputException("Невідома операція: '" + operation.trim()
                    + "'. Доступні: +, -, *, /, sqrt");
        };
    }

    /** Перевірка, що число є скінченним (не NaN і не нескінченність). */
    private void validate(double value, String name) throws InvalidInputException {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new InvalidInputException(
                    "Параметр " + name + " має бути скінченним числом, отримано: " + value);
        }
    }
}
