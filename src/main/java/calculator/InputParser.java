package calculator;

/**
 * Перетворення введеного користувачем тексту на число.
 */
public final class InputParser {

    private InputParser() {
    }

    /**
     * Перетворює рядок на double. Приймає і кому, і крапку як десятковий роздільник.
     *
     * @throws InvalidInputException якщо рядок порожній або не є числом
     */
    public static double parseNumber(String text) throws InvalidInputException {
        if (text == null || text.isBlank()) {
            throw new InvalidInputException("Порожнє значення: потрібно ввести число");
        }
        String normalized = text.trim().replace(',', '.');
        try {
            return Double.parseDouble(normalized);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("'" + text.trim() + "' не є числом", e);
        }
    }
}
