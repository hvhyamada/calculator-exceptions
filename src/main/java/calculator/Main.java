package calculator;

import java.util.Scanner;

/**
 * Головний клас: взаємодія з користувачем через консоль.
 */
public class Main {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Калькулятор ===");
        System.out.println("Операції: +  -  *  /  sqrt (корінь береться з першого числа)");
        System.out.println("Для виходу введіть 'exit'.");

        while (true) {
            System.out.println();
            System.out.print("Введіть перше число: ");
            if (!scanner.hasNextLine()) {
                break;
            }
            String first = scanner.nextLine();
            if (isExit(first)) {
                break;
            }

            System.out.print("Введіть друге число: ");
            String second = scanner.hasNextLine() ? scanner.nextLine() : "";
            if (isExit(second)) {
                break;
            }

            System.out.print("Введіть операцію (+, -, *, /, sqrt): ");
            String operation = scanner.hasNextLine() ? scanner.nextLine() : "";
            if (isExit(operation)) {
                break;
            }

            try {
                double a = InputParser.parseNumber(first);
                double b = InputParser.parseNumber(second);
                double result = calculator.calculate(operation, a, b);
                System.out.println("Результат: " + format(result));
            } catch (ArithmeticException e) {
                System.out.println("Арифметична помилка: " + e.getMessage());
            } catch (InvalidInputException e) {
                System.out.println("Некоректні вхідні дані: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Непередбачена помилка: " + e.getMessage());
            } finally {
                System.out.println("Обробку запиту завершено.");
            }
        }

        System.out.println("Роботу програми завершено.");
        scanner.close();
    }

    private static boolean isExit(String input) {
        return input != null && input.trim().equalsIgnoreCase("exit");
    }

    private static String format(double value) {
        if (value == Math.rint(value) && Math.abs(value) < 1e15) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }
}
