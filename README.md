# Практична робота 3 — Calculator з обробкою виключень

## Структура
```
src/main/java/calculator/
  Calculator.java             — add, subtract, multiply, divide, sqrt (double, double) -> double
  InvalidInputException.java  — власне виключення
  InputParser.java            — перетворення рядка на число (текст -> InvalidInputException)
  Main.java                   — консольна взаємодія, try-catch-finally
src/test/java/calculator/
  CalculatorTest.java         — JUnit 5 тести (26 шт.)
pom.xml
```

## Обробка виключень
- `divide(a, 0)` → `ArithmeticException("Ділення на нуль неможливе")`
- `sqrt(від'ємне)` → `InvalidInputException`
- NaN / нескінченність у будь-якому методі → `InvalidInputException`
- текст замість числа (`"abc"`) → `InvalidInputException` (причина — `NumberFormatException`)
- невідома операція → `InvalidInputException`
- у `Main` кожен запит обгорнуто в `try-catch-finally`; у `finally` виводиться «Обробку запиту завершено.»

## Запуск
```
mvn test                                  # тести
mvn compile exec:java -Dexec.mainClass=calculator.Main
```
або в IntelliJ IDEA: відкрити `pom.xml` як проєкт, запустити `Main` / `CalculatorTest`.

## Результат тестів
26 tests found, 26 successful, 0 failed.

## Приклад роботи
```
Введіть перше число: 10   Введіть друге число: 4   Операція: +    -> Результат: 14
Введіть перше число: 7    Введіть друге число: 0   Операція: /    -> Арифметична помилка: Ділення на нуль неможливе
Введіть перше число: abc  Введіть друге число: 5   Операція: *    -> Некоректні вхідні дані: 'abc' не є числом
Введіть перше число: -9   Введіть друге число: 0   Операція: sqrt -> Некоректні вхідні дані: Неможливо обчислити квадратний корінь з від'ємного числа: -9.0
Введіть перше число: 2,5  Введіть друге число: 2   Операція: %    -> Некоректні вхідні дані: Невідома операція: '%'
Після кожного запиту: Обробку запиту завершено.
```
