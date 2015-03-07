package com.styx.db.sql;

import com.styx.db.sql.data.Data;
import com.styx.db.sql.data.DataType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ralitski
 */
public class Result {
    
    private Table owner;
    private Query source;
    private ResultSet set;
    
    private int nextIndex;
    private Map<String, Data> nextResults;
    
    Result(Table owner, Query query, ResultSet set) throws SQLException {
        this.owner = owner;
        this.source = query;
        this.set = set;
    }
    
    public void close() {
        try {
            this.set.close();
        } catch (SQLException ex) {
            Logger.getLogger(Result.class.getName()).log(Level.SEVERE, "Result could not close internal ResultSet", ex);
        }
    }
    
    public boolean next() {
        boolean next;
        try {
            next = set.next();
            if(next) prepareNext();
        } catch (SQLException ex) {
            Logger.getLogger(Result.class.getName()).log(Level.SEVERE, "Result could not prepare next set", ex);
            return false;
        }
        return next;
    }
    
    void prepareNext() throws SQLException {
        nextResults = new HashMap<>();
        Map<DataType, String> fields = owner.getFieldMap();
        for(DataType type : source.getFieldsToGet()) {
            String field = fields.get(type);
            Object data = type.get(set, field);
            nextResults.put(field, new Data(type, data));
        }
    }
    
    public <T> T get(DataType<T> type) {
        return (T)get(owner.getFieldMap().get(type)).getData();
    }
    
    //should use above method instead
    public Data get(String field) {
        return nextResults != null ? nextResults.get(field) : null;
    }
    
//    public <T> T getAs(String field) {
//        try {
//            return (T) get(field);
//        } catch (Exception e) {
//            //class cast exception, or null pointer
//            return null;
//        }
//    }
}
