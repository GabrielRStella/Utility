package com.styx.db.sql;

import com.styx.db.sql.Database;
import com.styx.db.sql.AbstractDatabase;
import com.styx.db.sql.MySQL;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.plugin.Plugin;

/**
 * @author Foodyling
 */
public class SQLite extends AbstractDatabase {
    
    /*
     * TODO: SQLite takes a file path for its database.
     * we should probably do something like "<plugin data folder>/<database>.db"
     */

    public SQLite(Plugin plugin, String database) {
        super(plugin, database);
    }
    
    @Override
    public Connection openConnection() {
        if(!checkConnection()) {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:" + getDatabase());
            } catch (SQLException exception) {
                getPlugin().getLogger().log(Level.SEVERE, "Could not connect to SQLite database", exception);
                DatabaseHandler.disable();
            }
        }
        return connection;
    }
}