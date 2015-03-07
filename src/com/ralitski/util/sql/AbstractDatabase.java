package com.styx.db.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.plugin.Plugin;

/**
 *
 * @author ralitski
 */
public abstract class AbstractDatabase extends Database {

    protected Connection connection;
    
    public AbstractDatabase(Plugin plugin, String database) {
        super(plugin, database);
    }
    
    @Override
    public boolean checkConnection() {
        boolean flag = connection != null;
        try {
            flag = flag && !this.connection.isClosed();
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
    }
    
    @Override
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException exception) {
                getPlugin().getLogger().log(Level.SEVERE, "Error closing the MySQL Connection!", exception);
            }
        }
    }
    
    @Override
    public ResultSet querySQL(String query) {
        Connection c = openConnection();
        if(c == null) return null;
        Statement s = null;
        try {
            s = c.createStatement();
        } catch (SQLException ex) {
            getPlugin().getLogger().log(Level.SEVERE, "Error Querying SQL", ex);
        }
        if(s == null) return null;
        ResultSet ret = null;
        try {
            ret = s.executeQuery(query);
        } catch (SQLException ex) {
            getPlugin().getLogger().log(Level.SEVERE, "Error Querying SQL", ex);
        }
        return ret;
    }

    @Override
    public void updateSQL(String update) {
        Connection c = openConnection();

        if(c != null) try {
            Statement s = c.createStatement();
            s.executeUpdate(update);
        } catch (SQLException exception) {
            getPlugin().getLogger().log(Level.SEVERE, "Error updating SQL!", exception);
        }
    }

}
