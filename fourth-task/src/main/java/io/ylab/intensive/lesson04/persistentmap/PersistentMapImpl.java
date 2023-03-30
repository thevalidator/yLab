package io.ylab.intensive.lesson04.persistentmap;

import static io.ylab.intensive.lesson04.persistentmap.sql.Query.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

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
        String query = getKeysQuery(name == null);
        List<String> keys = new ArrayList<>();
        try (Connection conn = dataSource.getConnection(); 
                PreparedStatement ps = conn.prepareStatement(query);) {

            setValuesForStatement(ps, name);            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                keys.add(rs.getString("KEY"));
            }

        }
        return keys;
    }

    @Override
    public String get(String key) throws SQLException {
        String query = getQuery(name == null, key == null);
        try (Connection conn = dataSource.getConnection(); 
                PreparedStatement ps = conn.prepareStatement(query);) {

            setValuesForStatement(ps, name, key);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("value");
            }

        }
        return null;
    }

    @Override
    public void remove(String key) throws SQLException {
        String query = removeQuery(name == null, key == null);
        try (Connection conn = dataSource.getConnection(); 
                PreparedStatement ps = conn.prepareStatement(query);) {

            setValuesForStatement(ps, name, key);
            ps.executeUpdate();

        }
    }

    @Override
    public void put(String key, String value) throws SQLException {
        String removeQuery = removeQuery(name == null, key == null);
        String putQuery = putQuery();
        try (Connection conn = dataSource.getConnection(); 
                PreparedStatement rps = conn.prepareStatement(removeQuery); 
                PreparedStatement pps = conn.prepareStatement(putQuery);) {
            
            setValuesForStatement(rps, name, key);
            rps.executeUpdate();

            setValuesForStatement(pps, name, key, value);
            pps.executeUpdate();

        }
    }

    @Override
    public void clear() throws SQLException {
        try (Connection conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(clearQuery());) {
            ps.setString(1, name);
            ps.executeUpdate();
        }
    }

    private void setValuesForStatement(PreparedStatement rps, String name, String key) throws SQLException {
        if (name != null) {
            rps.setString(1, name);
            if (key != null) {
                rps.setString(2, key);
            }
        } else if (key != null) {
            rps.setString(1, key);
        }
    }

    private void setValuesForStatement(PreparedStatement ps, String name) throws SQLException {
        if (name != null) {
            ps.setString(1, name);
        }
    }

    private void setValuesForStatement(PreparedStatement pps, String name, String key, String value) throws SQLException {
        pps.setString(1, name);
        pps.setString(2, key);
        pps.setString(3, value);
    }
}
