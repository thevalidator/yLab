# FileSort
Даны следующие классы:
- Класс Generator, который генерирует файл с заданными количеством чисел типа long
```java
public class Generator {
	public File generate(String name, int count) throws IOException {
		Random random = new Random();
		File file = new File(name);
		try (PrintWriter pw = new PrintWriter(file)) {
			for (int i = 0; i < count; i++) {
				pw.println(random.nextLong());
			}
			pw.flush();
		}
		return file;
	}
}
```
- Класс Validator, который проверяет, что файл отсортирован по возрастанию
```java
public class Validator {
	private File file;
	public Validator(File file) {
		this.file = file;
	}
	public boolean isSorted() {
		try (Scanner scanner = new Scanner(new FileInputStream(file))) {
			long prev = Long.MIN_VALUE;
			while (scanner.hasNextLong()) {
				long current = scanner.nextLong();
				if (current < prev) {
					return false;
				} else {
					prev = current;
				}
			}
			return true	;
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
```
- Класс Sorter, который получает на вход файл с числами, и возвращает
отсортированный по возрастанию файл
```java
public class Sorter {
	public File sortFile(File dataFile) throws IOException {
		//code
	}
}
```
- И класс Test, который запускает генерацию файла, затем сортировку и проверку, что
файл отсортирован
```java
public class Test {
	public static void main(String[] args) throws IOException {
		File dataFile = new Generator().generate("data.txt", 375_000_000);
		System.out.println(new Validator(dataFile).isSorted()); // false
		File sortedFile = new Sorter().sortFile(dataFile);
		System.out.println(new Validator(sortedFile).isSorted()); // true
	}
}
```

**Задача** - реализовать метод Sorter.sortFile используя алгоритм внешней
сортировки слиянием.

Для демонстрации решения проверяющим можно использовать файл небольшого размера (до 100 элементов), однако, решение должно быть реализовано таким образом, чтобы поддерживать сортировку файлов произвольно большого размера. Для самопроверки можно сгенерировать файл из 375.000.000 записей, тогда объем файла для сортировки будет равен 7-8 Гб.
