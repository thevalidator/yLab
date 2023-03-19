# Org Structure
Структура организации записана в виде строк в CSV файле. CSV файл - это простой текстовый файл, содержащий строки. Каждая строка представляет собой одну запись (объект). Поля объекта разделены специальным символом. Первая строка файла содержит поля имена полей, все дальнейшие сроки содержат непосредственно данные.

**Пример:**

------------


id;boss_id;name;position
1;;Иван Иванович;Генеральный директор
2;1;Крокодилова Людмила Петровна;Главный бухгалтер
3;2;Галочка;Бухгалтер
4;1;Сидоров Василий Васильевич;Исполнительный директор
5;1;Зайцев Валерий Петрович;Директор по ИТ
6;5;Петя;Программист

------------


Поле **id** обозначает уникальный идентификатор сотрудника, **boss_id** идентификатор начальника, **name** - имя сотрудника, **position** - должность. Таким образом, данные в файле описывают следующую иерархию сотрудников.

Необходимо написать программу, которая получает на вход CSV файл формата, описанного выше и формирует структуру объектов класса:

```java
public class Employee {
	private Long id;
	private Long bossId;
	private String name;
	private String position;
	private Employee boss;
	private List<Employee> subordinate = new ArrayList<>();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBossId() {
		return bossId;
	}
	public void setBossId(Long bossId) {
		this.bossId = bossId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Employee getBoss() {
		return boss;
	}
	public void setBoss(Employee boss) {
		this.boss = boss;
	}
	public List<Employee> getSubordinate() {
		return subordinate;
	}
}
```
Решение оформить в виде реализации следующего интерфейса:

```java
public interface OrgStructureParser {
	public Employee parseStructure(File csvFile) throws IOException;
}
```
Метод **parseStructure** должен считывать данные из файла и возвращать ссылку на Босса (Генерального директора) - сотрудника, атрибут **boss_id** которого не задан. Cчитать, что такой сотрудник в файле ровно один.

**P.S.** **subordinates** - список прямых подчиненных
