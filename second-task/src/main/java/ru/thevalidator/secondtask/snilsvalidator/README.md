# Snils Validator
Номер СНИЛС состоит из 11 цифра, и валидация номера СНИЛС выполняется по следующим правилам: валидация и проверка контрольного числа СНИЛС.

Реализовать интерфейс SnilsValidator
```java
public interface SnilsValidator {
	/**
	* Проверяет, что в строке содержится валидный номер СНИЛС
	* @param snils снилс
	* @return результат проверки
	*/
	boolean validate(String snils);
}
```
Который возвращает true если номер СНИЛС валидный, false - в противном случае. Можно считать, что номер передается в виде строки, содержащей исключительно цифры от 0 до 9.
```java
int x = Character.digit(‘7’, 10);// конвертация символа в число. x == 7
boolean isDigit = Character.isDigit(‘7’); // true
```
Пример:
```java
System.out.println(new SnilsValidatorImpl().validate("01468870570")); //false
System.out.println(new SnilsValidatorImpl().validate("90114404441")); //true
```
Обратить внимание, что переданная строка может быть произвольной. Метод должен возвращать true тогда и только тогда, когда в строке валидный СНИЛС
