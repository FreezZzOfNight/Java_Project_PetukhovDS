package org.example;

import com.opencsv.exceptions.CsvException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. Инициализация компонентов
            CsvParser csvParser = new CsvParser();
            DatabaseManager dbManager = new DatabaseManager();
            Queries queries = new Queries();

            // 2. Загрузка данных
            dbManager.createTable();
            List<Country> countries = csvParser.parseCSV("psps.csv");
            dbManager.saveCountries(countries);

            // 3. Выполнение запросов
            System.out.println("=== Результаты ===");

            // Задание 1: Данные экономики и график
            List<CountryEconomy> economyData = queries.getEconomyData();
            System.out.println("\n1. Топ-10 стран по экономике:");
            economyData.stream()
                    .limit(10)
                    .forEach(e -> System.out.printf("%-25s: %.3f\n", e.getCountry(), e.getEconomy()));

            generateEconomyChart(economyData, "economy_chart.png");

            // Задание 2: Макс. экономика в регионах
            String maxEconomy = queries.getCountryWithMaxEconomy(
                    "Latin America and Caribbean",
                    "Eastern Asia"
            );
            System.out.println("\n2. Страна с самой высокой экономикой: " + maxEconomy);

            // Задание 3: Самая средняя страна
            String avgCountry = queries.getMostAverageCountry(
                    "Western Europe",
                    "North America"
            );
            System.out.println("\n3. Страна с самыми средними показателями: " + avgCountry);

        } catch (IOException | CsvException | SQLException e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void generateEconomyChart(List<CountryEconomy> data, String filename) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Берем топ-15 стран для графика
        int count = Math.min(data.size(), 15);
        for (int i = 0; i < count; i++) {
            CountryEconomy item = data.get(i);
            dataset.addValue(item.getEconomy(), "GDP per Capita", item.getCountry());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Топ-15 стран по показателю экономики",  // Заголовок
                "Страны",                               // Ось X
                "GDP per Capita",                       // Ось Y
                dataset,                                // Данные
                PlotOrientation.VERTICAL,               // Ориентация
                true,                                   // Легенда
                true,                                   // Подсказки
                false                                   // URL
        );

        // Настройка внешнего вида
        chart.getCategoryPlot().getDomainAxis().setMaximumCategoryLabelLines(2);

        // Сохраняем в файл
        ChartUtils.saveChartAsPNG(new File(filename), chart, 1200, 600);
        System.out.println("\nГрафик сохранен в файл: " + filename);
    }
}