package com.styx.db.sql;

import com.styx.db.sql.Database;
import com.styx.db.sql.AbstractDatabase;
import com.styx.doc.PendingAction;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.plugin.Plugin;

/**
 * original authors:
 * @author -_Husky_-
 * @author tips48
 * better author:
 * @author ralitski
 */
public class MySQL extends AbstractDatabase {
    
    /**
     * might need to move some of these (such as database) to {@link com.styx.db.sql.Database}
     */
    private String user;
    private String password;
    private String port;
    private String hostname;


    /**
     * Creates a new MySQL instance
     * 
     * @param plugin Plugin instance to use for logger / data folder
     * @param hostname Name of the host
     * @param port Port number to connect on
     * @param database Database name to edit
     * @param username Username the username to access the sql database
     * @param password Password the password to access the sql database
     */
    public MySQL(Plugin plugin, String database, String hostname, String port, String username, String password) {
        super(plugin, database);
        this.hostname = hostname;
        this.port = port;
        this.user = username;
        this.password = password;
    }
    
    @Override
    public Connection openConnection() {
        if(!checkConnection()) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port + "/" + this.getDatabase(), user, password);
            } catch (SQLException exception) {
                getPlugin().getLogger().log(Level.SEVERE, "Could not connect to MySQL server!", exception);
                DatabaseHandler.disable();
            }
        }
        return connection;
    }
    
    public void create() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://" + hostname + ":" + port, user, password);
            if(conn == null) return;
            Statement statement = conn.createStatement();
            if(statement == null) return;
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + getDatabase());
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}