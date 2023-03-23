# RateLimitedPrinter
Реализовать класс RateLimiterPrinter. Класс имеет конструктор, в который передается interval и метод print(), в который передается строка. Класс функционирует по следующему принципу: на объекте класса вызывается метод print(). Далее идет проверка, когда был последний вывод в консоль. Если интервал (в миллисекундах) между последним состоявшимся выводом и текущим выводом больше значения interval, переданного в конструктор - то происходит вывод значения. Иначе - не происходит, и сообщение отбрасывается. То есть класс ограничивает частоту вывода в консоль. Другими словами, сообщение не будет выводится чаще чем 1 раз в interval милисекунд. Реализовать описанный класс.
```java
public class RateLimitedPrinter {
	public RateLimitedPrinter(int interval) {
	// code here
	}
	public void print(String message) {
	// code here
	}
}
```
Пример использования: Задается вывод не чаще 1 раза в секунду, далее
запускается цикл.
```java
public static void main(String[] args) {
	RateLimitedPrinter rateLimitedPrinter = new RateLimitedPrinter(1000);
	for (int i = 0; i < 1_000_000_000; i++) {
		rateLimitedPrinter.print(String.valueOf(i));
	}
	long currentTime = System.currentTimeMillis(); // возвращает текущее
	// время
}
```