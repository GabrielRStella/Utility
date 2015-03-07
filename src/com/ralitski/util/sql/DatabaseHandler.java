package com.styx.db.sql;

import com.styx.Styx;
import com.styx.util.Info;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.plugin.Plugin;

/**
 * silly things.
 * 
 * @author ralitski
 */
public class DatabaseHandler {
    
    //TODO: also use SQLite
    
    private static Map<String, Database> databases = new HashMap<>();
    
    private static Info getInfo() {
        return Info.getInfo("sql");
    }
    
    static void disable() {
        getInfo().setBoolean("enabled", false);
    }
    
    public static boolean enabled() {
        DriverManager.getDrivers();
        return getInfo().getBoolean("enabled", true);
    }
    
    public static String getHost() {
        return getInfo().get("host", "localhost");
    }
    
    public static String getPort() {
        return getInfo().get("port", "3306");
    }
    
    public static String getUser() {
        return getInfo().get("user", "root");
    }
    
    public static String getPassword() {
        return getInfo().get("password", "root");
    }

    public static String getDatabase() {
        return getInfo().get("database", "styx");
    }
    
    public static Database getSQL() {
        return getSQL(getDatabase());
    }
    
    public static Database getSQL(String database) {
        return getInfo().getBoolean("ALLOW_MYSQL", true) ? getMySQL(database) : getSQLite(database);
    }
    
    public static Database getSQLite(String database) {
        return getSQLite(Styx.getInstance(), database);
    }
    
    public static Database getSQLite(Plugin plugin, String database) {
        if(!enabled()) return null;
        Database base = databases.get(database);
        if(base == null) {
            base = new SQLite(plugin, database);
            //TODO: how to check for SQLite failure?
            databases.put(database, base);
        }
        return base;
    }
    
    public static Database getMySQL() {
        return getMySQL(getDatabase());
    }
    
    public static Database getMySQL(String database) {
        return getMySQL(Styx.getInstance(), database);
    }
    
    public static Database getMySQL(Plugin plugin, String database) {
        return getMySQL(
                plugin,
                getHost(),
                getPort(),
                database,
                getUser(),
                getPassword()
                );
    }
    
    public static Database getMySQL(String hostname, String port, String database, String username, String password) {
        return getMySQL(Styx.getInstance(), hostname, port, database, username, password);
    }
    
    public static Database getMySQL(Plugin plugin, String hostname, String port, String database, String username, String password) {
        if(!enabled()) return null;
        Database sqlTemp = databases.get(database);
        if(sqlTemp != null) {
            return sqlTemp;
        } else {
            //Plugin plugin, String database, String hostname, String port, String username, String password
            MySQL sql = new MySQL(plugin, database, hostname, port, username, password);
            sql.create();
            //if SQL was disabled while creating the table
            if(!enabled()) return null;
            databases.put(database, sql);
            return sql;
        }
    }
}
