package com.ralitski.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * a quick info thing so we don't have to deal with the dumb config stuff.
 * 
 * @author ralitski
 */
public class Info {
    
    private static final String parse = ":";
    
    //guess what this is
    private static Map<String, Info> infos;
    
    static {
        infos = new HashMap<String, Info>();
    }
    
    /**
     * the name of this server, according to bungee
     *
     * @return the name of this server that can be used to send players to this
     * server with bungee. this name is used by the lobby system.
     */
    public static String getBungeeServer() {
        String s = getInfo().data.get("bungee");
        return s == null ? "" : s;
    }
    
    /**
     * set the bungee server.
     * @param s the new bungee server name
     */
    public static void setBungeeServer(String s) {
        getInfo().data.put("bungee", s);
    }
    
    /**
     * saves all Info instances
     */
    public static void saveAll() {
        for(Info i : infos.values()) {
            i.save();
        }
    }
    
    /**
     * get the main info instance
     * @return ...the main info instance
     */
    public static Info getInfo() {
        //data data data
        Info i = infos.get("data");
        if(i == null) infos.put("data", new Info("data"));
        return infos.get("data");
    }
    
    /**
     * get any info instance by name
     * @param name the name of the info to be returned
     * @return the info by that name, or a new one if not found
     */
    public static Info getInfo(String name) {
        Info i = infos.get(name);
        if(i == null) i = newInfo(name);
        return i;
    }
    
    /**
     * creates a new info instance
     * @param name the name of the info to be created
     * @return the new info
     */
    private static Info newInfo(String name) {
        Info i = new Info(name);
        infos.put(name, i);
        i.load();
        return i;
    }
    
    //this info's name, stored in the static info map
    private String name;
    //the string used to parse data from the file
    //the data map
    private Map<String, String> data;
    
    //private to be sure that getInfo(String name) is used and infos are stored
    private Info(String name) {
        this.name = name;
        data = new HashMap<String, String>();
    }
    
    /**
     * gets the file where this info will store data.
     * the file is in the CoreLib plugin data folder, called <data name>.txt
     * @return the file where this info instance will save or load data
     */
    private File getFile() {
        File f = new File("./data/" + this.name + ".txt");
        try {
            f.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Info.class.getName()).log(Level.SEVERE, "Could not create new file for data set " + this.name, ex);
        }
        return f;
    }
    
    /**
     * loads data from the file
     * data in this map not in the file is not overwritten
     */
    public void load() {
        try {
            BufferedReader r = new BufferedReader(new FileReader(getFile()));
            String line;
            while((line = r.readLine()) != null) {
                String[] input = line.split(parse, 2);
                if(input.length == 2) {
                    data.put(input[0], input[1]);
                }
            }
            r.close();
        } catch (IOException ex) {
            Logger.getLogger(Info.class.getName()).log(Level.SEVERE, "Could not load data set of name " + this.name, ex);
        }
    }
    
    /**
     * saves info to the file
     */
    public void save() {
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(getFile()));
            if(w != null) {
                for(String key : data.keySet()) {
                    w.write(key + parse + data.get(key));
                    w.newLine();
                }
            }
            w.close();
        } catch (IOException ex) {
            Logger.getLogger(Info.class.getName()).log(Level.SEVERE, "Could not save data set of name " + this.name, ex);
        }
    }
    
    /**
     * the set of keys in this info instance.
     * @return a set of available data in this info
     */
    public Set<String> get() {
        return this.data.keySet();
    }
    
    /**
     * gets a piece of data, exactly as stored in the data map
     * @param key the name of the piece of data
     * @return the value stored in the map, or null if not found
     */
    public String getString(String key) {
        return get(key);
    }
    
    /**
     * gets a piece of data, exactly as stored in the data map
     * @param key the name of the piece of data
     * @return the value stored in the map, or null if not found
     */
    public String get(String key) {
        return data.get(key);
    }
    
    /**
     * gets a piece of data, exactly as stored in the data map, or if the key is not found adds the given default argument.
     * @param key the name of the piece of data
     * @param def the default value for this key
     * @return the value stored in the map, or the default parameter if not found
     */
    public String getString(String key, String def) {
        return get(key, def);
    }
    
    /**
     * gets a piece of data, exactly as stored in the data map, or if the key is not found adds the given default argument.
     * @param key the name of the piece of data
     * @param def the default value for this key
     * @return the value stored in the map, or the default parameter if not found
     */
    public String get(String key, String def) {
        String value = data.get(key);
        if(value == null) {
            value = def;
            data.put(key, def);
        }
        return value;
    }
    
    /**
     * sets a value under a given key.
     * @param key the under which to store the value
     * @param value the new value under that key
     */
    public void set(String key, String value) {
        data.put(key, value);
    }
    
    /**
     * gets a value from the data map casted to an integer.
     * @param key the key to look up the integer in
     * @return the integer value, or 0 if the value is not found or is not a number.
     */
    public int getInt(String key) {
        int i = 0;
        try {
            i = Integer.parseInt(get(key));
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "Info was unable to parse integer from input file", ex);
        }
        return i;
    }
    
    /**
     * gets a value from the data map casted to an integer, or if not available, sets the given default parameter as the value.
     * @param key the key to look up the integer in
     * @param def the default value for this key
     * @return the integer value, or 0 if the value is not found or is not a number.
     */
    public int getInt(String key, int def) {
        if(data.get(key) == null) {
            data.put(key, ""+def);
            return def;
        }
        return getInt(key);
    }
    
    /**
     * sets a value in the map to a given integer.
     * @param key the key to store this value under
     * @param value the value to be stored under the given key
     */
    public void setInt(String key, int value) {
        this.data.put(key, ""+value);
    }
    
    /**
     * returns a value from the data map casted as a boolean. that is, this
     * method will return true if the value in the data map under the given key
     * is "true", otherwise false.
     * @param key the key to look for in the data map
     * @return true if the value under the given key is "true"
     */
    public boolean getBoolean(String key) {
        try {
            return Boolean.parseBoolean(get(key));
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, "Info was unable to parse boolean from input file", ex);
        }
        return false;
    }
    
    /**
     * returns a value from the data map casted as a boolean. that is, this
     * method will return true if the value in the data map under the given key
     * is "true", false if it equals "false", or the passed default argument if
     * neither of those is true.
     * @param key the key under which to look for a boolean value
     * @param def the default value
     * @return the stored value, or the given default value
     */
    public boolean getBoolean(String key, boolean def) {
        String get = data.get(key);
        if (get == null) {
        } else if(get.equalsIgnoreCase("true")) {
            return true;
        } else if(get.equalsIgnoreCase("false")) {
            return false;
        }
        data.put(key, "" + def);
        return def;
    }
    
    /**
     * sets a boolean value in the map; that is, stores either "true" or "false"
     * based on the passed value.
     * @param key the key under which to store the boolean value as a string
     * @param value the boolean value to be stored
     */
    public void setBoolean(String key, boolean value) {
        this.data.put(key, ""+value);
    }
}
