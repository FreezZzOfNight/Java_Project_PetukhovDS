package org.example;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {
    public List<Country> parseCSV(String filePath) throws IOException, CsvException {
        List<Country> countries = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> lines = reader.readAll();
            lines.remove(0); // Удаляем заголовок

            for (String[] line : lines) {
                Country country = new Country();
                country.setCountry(line[0]);
                country.setRegion(line[1]);
                country.setHappinessRank(Integer.parseInt(line[2]));
                country.setHappinessScore(Double.parseDouble(line[3]));
                country.setStandardError(Double.parseDouble(line[4]));
                country.setEconomy(Double.parseDouble(line[5]));
                country.setFamily(Double.parseDouble(line[6]));
                country.setHealth(Double.parseDouble(line[7]));
                country.setFreedom(Double.parseDouble(line[8]));
                country.setTrust(Double.parseDouble(line[9]));
                country.setGenerosity(Double.parseDouble(line[10]));
                country.setDystopiaResidual(Double.parseDouble(line[11]));
                countries.add(country);
            }
        }
        return countries;
    }
}