package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Queries {
    private static final String DB_URL = "jdbc:sqlite:happiness.db";

    // 1. График по показателю экономики (возвращает данные для построения)
    public List<CountryEconomy> getEconomyData() throws SQLException {
        String sql = "SELECT country, economy FROM countries ORDER BY economy DESC";
        List<CountryEconomy> result = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                result.add(new CountryEconomy(
                        rs.getString("country"),
                        rs.getDouble("economy")
                ));
            }
        }
        return result;
    }

    // 2. Страна с максимальной экономикой в указанных регионах
    public String getCountryWithMaxEconomy(String region1, String region2) throws SQLException {
        String sql = "SELECT country, MAX(economy) as max_economy FROM countries " +
                "WHERE region IN (?, ?) GROUP BY region";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, region1);
            pstmt.setString(2, region2);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("country") + " (Economy: " + rs.getDouble("max_economy") + ")";
            }
        }
        return "No data found";
    }

    // 3. Страна с самыми средними показателями
    public String getMostAverageCountry(String region1, String region2) throws SQLException {
        String sql = "SELECT country, " +
                "(ABS(economy - (SELECT AVG(economy) FROM countries WHERE region IN (?, ?))) + " +
                "ABS(family - (SELECT AVG(family) FROM countries WHERE region IN (?, ?))) + " +
                "ABS(health - (SELECT AVG(health) FROM countries WHERE region IN (?, ?)))) as diff " +
                "FROM countries WHERE region IN (?, ?) " +
                "ORDER BY diff LIMIT 1";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 1; i <= 8; i++) {
                pstmt.setString(i, (i % 2 == 1) ? region1 : region2);
            }

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("country");
            }
        }
        return "No data found";
    }
}

// Вспомогательный класс для хранения данных экономики
class CountryEconomy {
    private String country;
    private double economy;

    public CountryEconomy(String country, double economy) {
        this.country = country;
        this.economy = economy;
    }

    // Геттеры
    public String getCountry() { return country; }
    public double getEconomy() { return economy; }
}