package io.ylab.intensive.lesson04.filesort;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

public class FileSortImpl implements FileSorter {

    private DataSource dataSource;

    public FileSortImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public File sort(File data) {
        List<Long> numbers;
        File sorted = null;
        try {
            numbers = readNumbers(data);
            saveAll(numbers);
            numbers = getNumbers();
            sorted = createFile(numbers, "sorted.txt");
        } catch (Exception e) {
            Logger.getLogger(FileSortImpl.class.getName()).log(Level.SEVERE, e.getMessage());
        } finally {
            return sorted;
        }
    }

    private List<Long> readNumbers(File data) throws FileNotFoundException, IOException {
        try (Reader reader = new FileReader(data); BufferedReader br = new BufferedReader(reader)) {
            List<Long> numbers = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null && !line.isBlank()) {
                Long l = Long.valueOf(line);
                numbers.add(l);
            }
            return numbers;
        }
    }

    private int[] saveAll(List<Long> numbers) throws SQLException {
        try (Connection conn = dataSource.getConnection(); 
                PreparedStatement ps = conn.prepareStatement("INSERT INTO numbers VALUES (?)");) {

            for (Long l: numbers) {
                ps.setLong(1, l);
                ps.addBatch();
            }
            int[] ids = ps.executeBatch();
            
            return ids;
        }
    }

    private List<Long> getNumbers() throws SQLException {
        List<Long> list;
        String query = "SELECT val FROM public.numbers ORDER BY val DESC";
        try (Connection conn = dataSource.getConnection(); 
                Statement stmt = conn.createStatement()) {

            list = new ArrayList<>();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Long number = rs.getLong("val");
                list.add(number);
            }
        }

        return list;
    }

    private File createFile(List<Long> numbers, String name) throws FileNotFoundException {
        File file = new File(name);
        try (PrintWriter pw = new PrintWriter(file)) {
            for (Long l: numbers) {
                pw.println(l);
            }
            pw.flush();
        }

        return file;
    }

}
