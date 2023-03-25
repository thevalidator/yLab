package io.ylab.intensive.lesson04.persistentmap;

import static io.ylab.intensive.lesson04.persistentmap.sql.Query.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
        return get(key) != null;
    }

    @Override
    public List<String> getKeys() throws SQLException {
        List<String> keys = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); 
                PreparedStatement ps = conn.prepareStatement(GET_KEYS);) {

            ps.setString(1, name);
            ResultSet rs =  ps.executeQuery();
            
            while (rs.next()) {
                keys.add(rs.getString("KEY"));
            }

        }
        return keys;
    }

    @Override
    public String get(String key) throws SQLException {
        try (Connection conn = dataSource.getConnection(); 
                PreparedStatement ps = conn.prepareStatement(GET);) {

            ps.setString(1, name);
            ps.setString(2, key);
            ResultSet rs =  ps.executeQuery();
            
            if (rs.next()) {
                return rs.getString("value");
            }

        }
        return null;
    }

    @Override
    public void remove(String key) throws SQLException {
        try (Connection conn = dataSource.getConnection(); 
                PreparedStatement ps = conn.prepareStatement(REMOVE);) {

            ps.setString(1, name);
            ps.setString(2, key);
            ps.executeUpdate();

        }
    }

    @Override
    public void put(String key, String value) throws SQLException {
        try (Connection conn = dataSource.getConnection(); 
                PreparedStatement rps = conn.prepareStatement(REMOVE); 
                PreparedStatement pps = conn.prepareStatement(PUT);) {

            rps.setString(1, name);
            rps.setString(2, key);
            rps.executeUpdate();

            pps.setString(1, name);
            pps.setString(2, key);
            pps.setString(3, value);
            pps.executeUpdate();

        }
    }

    @Override
    public void clear() throws SQLException {
        try (Connection conn = dataSource.getConnection(); 
                 Statement st = conn.createStatement();) {
            st.execute(CLEAR);
        }
    }
}
