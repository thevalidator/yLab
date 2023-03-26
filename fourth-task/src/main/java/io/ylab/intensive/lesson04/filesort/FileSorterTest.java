package io.ylab.intensive.lesson04.filesort;

import java.io.File;
import java.sql.SQLException;
import javax.sql.DataSource;

import io.ylab.intensive.lesson04.DbUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileSorterTest {
  public static void main(String[] args) throws SQLException {
    DataSource dataSource = initDb();
    File data = new File("data.txt");
    FileSorter fileSorter = new FileSortImpl(dataSource);
    File res = fileSorter.sort(data);
    printNumbersFromFile(res);
  }
  
  public static DataSource initDb() throws SQLException {
    String createSortTable = "" 
                                 + "drop table if exists numbers;" 
                                 + "CREATE TABLE if not exists numbers (\n"
                                 + "\tval bigint\n"
                                 + ");";
    DataSource dataSource = DbUtil.buildDataSource();
    DbUtil.applyDdl(createSortTable, dataSource);
    return dataSource;
  }
  
  public static void printNumbersFromFile(File file) {
      try (Reader reader = new FileReader(file); 
              BufferedReader br = new BufferedReader(reader)) {
          
            String line;
            while ((line = br.readLine()) != null && !line.isBlank()) {
                System.out.println(line);
            }
            
        } catch (FileNotFoundException ex) {
          Logger.getLogger(FileSorterTest.class.getName()).log(Level.SEVERE, ex.getMessage());
      } catch (IOException ex) {
          Logger.getLogger(FileSorterTest.class.getName()).log(Level.SEVERE, ex.getMessage());
      }
  }
}
