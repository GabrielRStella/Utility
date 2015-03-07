package com.styx.db.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import org.bukkit.plugin.Plugin;

/**
 * @author fatlitski
 */
public abstract class Database {
    
    private Plugin plugin;
    private String database;
    
    public Database(Plugin plugin, String database) {
        this.plugin = plugin;
        this.database = database;
    }
    
    public Plugin getPlugin() {
        return plugin;
    }
    
    public String getDatabase() {
        return database;
    }
    
    public abstract Connection openConnection();
    
    public abstract void closeConnection();
    
    public abstract boolean checkConnection();
    
    public abstract ResultSet querySQL(String query);
    
    public abstract void updateSQL(String update);
}