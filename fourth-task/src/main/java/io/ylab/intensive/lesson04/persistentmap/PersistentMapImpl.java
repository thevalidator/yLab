package io.ylab.intensive.lesson04.persistentmap;

import io.ylab.intensive.lesson04.persistentmap.sql.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * Класс, методы которого надо реализовать 
 */
public class PersistentMapImpl implements PersistentMap {
  
  private final DataSource dataSource;
  private String name;

  public PersistentMapImpl(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  @Override
  public void init(String name) {
    this.name = name;
  }

  @Override
  public boolean containsKey(String key) throws SQLException {
    return false;
  }

  @Override
  public List<String> getKeys() throws SQLException {
    return null;
  }

  @Override
  public String get(String key) throws SQLException {
    return null;
  }

  @Override
  public void remove(String key) throws SQLException {

  }

  @Override
  public void put(String key, String value) throws SQLException {
      try (Connection conn = dataSource.getConnection(); 
                PreparedStatement ps = conn.prepareStatement(Query.PUT);) {
          
          ps.setString(1, name);
          ps.setString(2, key);
          ps.setString(3, value);
          ps.executeUpdate();

        }
  }

  @Override
  public void clear() throws SQLException {

  }
}
