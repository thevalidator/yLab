package io.ylab.intensive.lesson04.persistentmap;

import java.sql.SQLException;
import javax.sql.DataSource;

import io.ylab.intensive.lesson04.DbUtil;

public class PersistenceMapTest {

    public static void main(String[] args) throws SQLException {
        
        DataSource dataSource = initDb();
        PersistentMap persistentMap = new PersistentMapImpl(dataSource);
        
        persistentMap.init(null);
        
        persistentMap.put(null, null);
        System.out.println(" SIZE AFTER ADDING (NULL, NULL): " + persistentMap.getKeys().size());
        System.out.println("             CONTAINS  NULL KEY: " + persistentMap.containsKey(null));
        System.out.println();
        
        persistentMap.put(null, "some value");
        System.out.println("SIZE AFTER ADDING (NULL, value): " + persistentMap.getKeys().size());
        System.out.println("             CONTAINS  NULL KEY: " + persistentMap.containsKey(null));
        System.out.println("                          VALUE: " + persistentMap.get(null));
        System.out.println();
        
        persistentMap.put("some key", "some value2");
        System.out.println(" SIZE AFTER ADDING (key, value): " + persistentMap.getKeys().size());
        System.out.println("              CONTAINS      KEY: " + persistentMap.containsKey("some key"));
        System.out.println("                          VALUE: " + persistentMap.get("some key"));
        System.out.println();
        
        persistentMap.remove(null);
        System.out.println("  SIZE  AFTER  REMOVE  NULL KEY: " + persistentMap.getKeys().size());
        System.out.println("             CONTAINS  NULL KEY: " + persistentMap.containsKey(null));
        System.out.println();
        
        System.out.println("===== NEW MAP CREATED ====");
        persistentMap.init("new map");
        
        persistentMap.put(null, "some value");
        System.out.println("SIZE AFTER ADDING (NULL, value): " + persistentMap.getKeys().size());
        System.out.println("             CONTAINS  NULL KEY: " + persistentMap.containsKey(null));
        System.out.println();
        
        persistentMap.clear();
        System.out.println("    SIZE     AFTER     CLEARING: " + persistentMap.getKeys().size());
        
    }

    public static DataSource initDb() throws SQLException {
        String createMapTable = ""
                + "drop table if exists persistent_map; "
                + "CREATE TABLE if not exists persistent_map (\n"
                + "   map_name varchar,\n"
                + "   KEY varchar,\n"
                + "   value varchar\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createMapTable, dataSource);
        return dataSource;
    }
}
