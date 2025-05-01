package org.example;

import java.sql.*;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:happiness.db";

    public void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS countries (" +
                "country TEXT PRIMARY KEY," +
                "region TEXT," +
                "happiness_rank INTEGER," +
                "happiness_score REAL," +
                "standard_error REAL," +
                "economy REAL," +
                "family REAL," +
                "health REAL," +
                "freedom REAL," +
                "trust REAL," +
                "generosity REAL," +
                "dystopia_residual REAL" +
                ");";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public void saveCountries(List<Country> countries) throws SQLException {
        // Сначала очистим таблицу, чтобы избежать дубликатов
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM countries");
        }

        String sql = "INSERT INTO countries (country, region, happiness_rank, happiness_score, "
                + "standard_error, economy, family, health, freedom, trust, generosity, dystopia_residual) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Country country : countries) {
                pstmt.setString(1, country.getCountry());
                pstmt.setString(2, country.getRegion());
                pstmt.setInt(3, country.getHappinessRank());
                pstmt.setDouble(4, country.getHappinessScore());
                pstmt.setDouble(5, country.getStandardError());
                pstmt.setDouble(6, country.getEconomy());
                pstmt.setDouble(7, country.getFamily());
                pstmt.setDouble(8, country.getHealth());
                pstmt.setDouble(9, country.getFreedom());
                pstmt.setDouble(10, country.getTrust());
                pstmt.setDouble(11, country.getGenerosity());
                pstmt.setDouble(12, country.getDystopiaResidual());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }
}