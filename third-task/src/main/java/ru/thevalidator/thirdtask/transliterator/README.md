# Transliterator
Правила транслитерации приведены в таблице ниже
                    
|----|----|----|----|
|:-------------:|:-------------:|:-------------:|:-------------:|
| **А** : A | **И** : I | **С** : S | **Ы** : Y |
| **Б** : B | **Й** : I | **Т** : T | **Ь** : - |
| **В** : V | **К** : K| **У** : U | **Ъ** : IE |
| **Г** : G | **Л** : L | **Ф** : F | **Э** : E |
| **Д** : D | **М** : M | **Х** : KH | **Ю** : IU |
| **Е** : E | **Н** : N | **Ц** : TS | **Я** : IA |
| **Ё** : E | **О** : O | **Ч** : CH ||
| **Ж** : ZH | **П** : P | **Ш** : SH ||
| **З** : Z | **Р** : R | **Щ** : SHCH ||
                


Необходимо реализовать интерфейс Transliterator
```java
public interface Transliterator {
	String transliterate(String source);
}
```
Метод transliterate должен выполнять транслитерацию входной строки в выходную, то есть заменять каждый символ кириллицы на соответствующую группу символов латиницы. Каждый символ кириллицы, имеющийся во входной строке входит в нее в верхнем регистре.
```java
public class TransliteratorTest {
	public static void main(String[] args) {
		Transliterator transliterator = new TransliteratorImpl();
		String res = transliterator
		.transliterate("HELLO! ПРИВЕТ! Go, boy!");
		System.out.println(res);
	}
}
```
результат: `> HELLO! PRIVET! Go, boy!`
