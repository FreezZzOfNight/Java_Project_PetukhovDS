
# Java Project – Анализ показателей стран

## 📌 Описание проекта

Данный проект представляет собой анализ данных из CSV-файла, содержащего показатели стран мира по таким метрикам, как ВВП на душу населения, здоровье, свобода, доверие, щедрость и т.д.  

Цель — реализовать работу с данными в формате CSV, сохранить их в базу данных SQLite, выполнить SQL-запросы и визуализировать числовую информацию на графиках.

## 🗂 Структура проекта

- `psps.csv` — исходный CSV-файл с данными.
- `Country.java` — модель для хранения данных одной страны.
- `CsvParser.java` — класс для чтения и парсинга CSV-файла.
- `DatabaseManager.java` — создание таблицы в SQLite и сохранение данных.
- `Queries.java` — выполнение SQL-запросов.
- `Main.java` — основной запуск приложения, построение графика.
- `pom.xml` — зависимости Maven (OpenCSV, JFreeChart, SQLite).

## 🔄 Последовательность выполнения проекта

### 1. Парсинг данных из CSV

Файл `psps.csv` был распарсен с помощью библиотеки **OpenCSV**. Каждая строка представляла собой показатели одной страны, которые были преобразованы в объект `Country`.

```java
CsvParser parser = new CsvParser();
List<Country> countries = parser.parseCSV("psps.csv");
```

---

### 2. Создание базы данных SQLite

С помощью JDBC и SQL-запроса была создана таблица `countries` в БД `happiness.db`, соответствующая 3-й нормальной форме.

```sql
CREATE TABLE IF NOT EXISTS countries (
    country TEXT PRIMARY KEY,
    region TEXT,
    ...
);
```

---

### 3. Сохранение данных в БД

Данные из объектов `Country` были сохранены в SQLite через `PreparedStatement`.

```java
DatabaseManager db = new DatabaseManager();
db.createTable();
db.saveCountries(countries);
```

---

### 4. SQL-запросы

#### 🔹 1. Построение графика по экономике

Вывод топ-10 стран по показателю `economy (GDP per Capita)`:

```sql
SELECT country, economy FROM countries ORDER BY economy DESC;
```

Визуализация через **JFreeChart**:

![График экономики](/screenshots/economy_chart.png)

---

#### 🔹 2. Страна с самой высокой экономикой среди "Latin America and Caribbean" и "Eastern Asia"

```sql
SELECT country, MAX(economy) FROM countries
WHERE region IN ('Latin America and Caribbean', 'Eastern Asia')
GROUP BY region;
```

📌 **Результат** (пример):  
`Hong Kong S.A.R., China (Economy: 1.340)`

---

#### 🔹 3. Самая «средняя» страна по Western Europe и North America

```sql
SELECT country, ...
ORDER BY diff LIMIT 1;
```

📌 **Результат** (пример):  
`Germany`

---

## 📈 Скриншоты

### 🔸 График по экономике
![График экономики](/screenshots/economy_chart.png)

### 🔸 Результаты в консоли
![Результаты консоли](/screenshots/console.jpg)

📍 Все скриншоты результатов работы находятся в директории `/screenshots/`:
- `economy_chart.png` — график по экономике.
- `console.jpg` — вывод консоли.

---

## 🛠️ Зависимости (pom.xml)

- `opencsv` — парсинг CSV
- `jfreechart` и `jcommon` — построение графиков
- `sqlite-jdbc` — работа с базой данных
- `slf4j`, `logback-classic` — логирование

---

## 🚀 Запуск проекта

1. Убедитесь, что файл `psps.csv` находится в корне проекта.
2. Выполните `mvn compile`
3. Запустите `Main.java`
4. Результаты появятся в консоли и сохранятся как `happiness.db` и `economy_chart.png`

---

## ✅ Выполненные требования задания

- ✔ CSV → Объекты
- ✔ SQLite → Таблицы и сохранение
- ✔ SQL-запросы по заданию
- ✔ Вывод в консоль
- ✔ Построение диаграммы
- ✔ Репозиторий на GitHub
- ✔ Скриншоты и описание в README
