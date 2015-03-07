package com.styx.db.sql;

import com.styx.db.sql.data.Data;
import com.styx.db.sql.data.DataType;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ralitski
 */
public abstract class Table {
    
    private final Database owner;
    private final String name;
    private final String primaryKey;
    private Map<DataType, String> fieldMap;
    private boolean fieldMapGenerated;
    
    private boolean threaded;
    private final Object lock;
    
    protected Table(Database owner, String name) {
        this(owner, name, null, false);
    }
    
    protected Table(Database owner, String name, String primaryKey) {
        this(owner, name, primaryKey, false);
    }
    
    protected Table(Database owner, String name, boolean threaded) {
        this(owner, name, null, threaded);
    }
    
    protected Table(Database owner, String name, String primaryKey, boolean threaded) {
        this.owner = owner;
        this.name = name;
        this.primaryKey = primaryKey;
        this.threaded = threaded;
        if (threaded) {
            lock = new Object();
        } else {
            lock = null;
        }
    }
    
    private void generateFieldMap() {
        if(!this.fieldMapGenerated) {
            fieldMap = new HashMap<>();
            Field[] fieldArray = getClass().getDeclaredFields();
            for (Field f : fieldArray) {
                if (DataType.class.isAssignableFrom(f.getType()) && Modifier.isFinal(f.getModifiers())) {
                    try {
                        f.setAccessible(true);
                        DataType fieldType = (DataType) f.get(this);
                        String fieldName = f.getName();
                        System.out.println("registering table field: " + fieldName);
                        fieldMap.put(fieldType, fieldName);
                    } catch (Exception ex) {
                        Logger.getLogger(Table.class.getName()).log(Level.SEVERE, "SQLTable failed to add a field to its map", ex);
                    }
                }
            }
        }
        fieldMapGenerated = true;
    }
    
    public Map<DataType, String> getFieldMap() {
        generateFieldMap();
        return fieldMap;
    }
    
    public DataType[] getDataTypes() {
        Set<DataType> set = getFieldMap().keySet();
        return set.toArray(new DataType[set.size()]);
    }
    
    public String[] getFields() {
        Collection<String> col = getFieldMap().values();
        return col.toArray(new String[col.size()]);
    }
    
    /*
     * registers this table in its database
     */
    
    public void create() {
        StringBuilder update = new StringBuilder("CREATE TABLE IF NOT EXISTS `" + name + "` (");
        
        Map<DataType, String> fields = getFieldMap();
        int count = 0;
        for(DataType type : fields.keySet()) {
            String add = fields.get(type) + " " + type.getTableDeclarationString();
            if(count++ > 0) update.append(", ");
            update.append(add);
        }
        
        if(primaryKey != null) {
            update.append(", PRIMARY KEY(`").append(primaryKey).append("`)");
        }
        
        update.append(")");
        String toSQL = update.toString();
        log(toSQL);
        this.doUpdate(toSQL);
    }
    
    /*
     * Database queries and such
     */
    
    public Result query(Query query) {
        StringBuilder string = new StringBuilder("SELECT ");
        Map<DataType, String> fields = this.getFieldMap();
        int count = 0;
        
        if(query.doAllFields()) {
            string.append("*");
        } else {
            for(DataType type : query.getFieldsToGet()) {
                if(count++ > 0) {
                    string.append(", ");
                }
                string.append(fields.get(type));
            }
        }
        
        string.append(" FROM `").append(name).append("`");
        
        if(!query.getSpecifications().isEmpty()) {
            string.append(" WHERE ");
            count = 0;
            for (Data data : query.getSpecifications()) {
                if (count++ > 0) {
                    string.append(" AND ");
                }
                string.append(data.getQuery(fields.get(data.getType())));
            }
        }
        
        String toSQL = string.toString();
        log(toSQL);
        ResultSet results = owner.querySQL(toSQL);
        try {
            return new Result(this, query, results);
        } catch (SQLException ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void insert(Insert insert) {
        
        Map<DataType, String> fields = this.getFieldMap();
        Data[] datas = insert.getData().toArray(new Data[insert.getData().size()]);
        
        StringBuilder string = new StringBuilder("INSERT INTO `" + name + "` (");
        
        int count = 0;
        for(Data data : datas) {
            if(count++ > 0) string.append(", ");
            string.append(fields.get(data.getType()));
        }
        
        count = 0;
        string.append(") VALUES(");
        
        for(Data data : datas) {
            if(count++ > 0) string.append(", ");
            string.append(data.formatDataForSQL());
        }
        
        string.append(")");
        
        String toSQL = string.toString();
        log(toSQL);
        this.doUpdate(toSQL);
        
        //make sure the query is of proper size (has all the necessary data)
//        if(insert.size() == getFieldMap().size()) {
//            StringBuilder string = new StringBuilder("INSERT INTO `" + name + "` VALUES(");
//            
//            List<Data> data = insert.getData();
//            DataType[] types = this.getDataTypes();
//            Data[] datas = new Data[types.length];
//            
//            //ensures the data is in correct order to be entered into Database
//            while(!data.isEmpty()) {
//                int tableIndex = 0;
//                Iterator<Data> iter = data.iterator();
//                while(iter.hasNext()) {
//                    Data sData = iter.next();
//                    System.out.println("testing data " + sData.formatDataForSQL());
//                    for(int i = tableIndex; i < types.length; i++) {
//                        if(types[i] == sData.getType()) {
//                            System.out.println("setting data " + sData.formatDataForSQL() + " in index " + tableIndex);
//                            datas[tableIndex++] = sData;
//                            iter.remove();
//                            break;
//                        }
//                    }
//                }
//            }
//            
//            int count = 0;
//            for(Data sData : datas) {
//                if(count++ > 0) string.append(", ");
//                string.append(sData.formatDataForSQL());
//            }
//            
//            string.append(")");
//            
//            log(string.toString());
//            owner.updateSQL(string.toString());
    }
    
    public void update(Update update) {
        Map<DataType, String> fields = this.getFieldMap();
        int count = 0;
        StringBuilder string = new StringBuilder("UPDATE `" + name + "` SET ");
        
        for(Data data : update.getFieldsToChange()) {
            if(count++ > 0) {
                string.append(", ");
            }
            string.append(data.getQuery(fields.get(data.getType())));
        }
        
        count = 0;
        string.append(" WHERE ");
        for(Data data : update.getSpecifications()) {
            if(count++ > 0) {
                string.append(" AND ");
            }
            string.append(data.getQuery(fields.get(data.getType())));
        }
        
        String toSQL = string.toString();
        log(toSQL);
        this.doUpdate(toSQL);
    }
    
    public void delete(Delete delete) {
        StringBuilder string = new StringBuilder("DELETE FROM `" + name + "` WHERE ");
        Map<DataType, String> fields = this.getFieldMap();
        int count = 0;
        for(Data data : delete.getSpecifications()) {
            if(count++ > 0) string.append(" AND ");
            string.append(data.getQuery(fields.get(data.getType())));
        }
        
        String toSQL = string.toString();
        log(toSQL);
        this.doUpdate(toSQL);
    }
    
    //handles threading
    private void doUpdate(final String update) {
        if(this.threaded) {
            Runnable updateRunner = new Runnable() {
                @Override
                public void run() {
                    synchronized(lock) {
                        owner.updateSQL(update);
                    }
                }
            };
            Thread thread = new Thread(updateRunner);
            thread.start();
        } else {
            owner.updateSQL(update);
        }
    }
    
    private void log(String log) {
        //System.out.println(">>>" + log);
    }
    
    /*
     * just some helpful methods (for table subclasses),
     * so people know what to use
     */
    
    public Query newQuery() {
        return new Query();
    }
    
    public Insert newInsert() {
        return new Insert();
    }
    
    public Update newUpdate() {
        return new Update();
    }
    
    public Delete newDelete() {
        return new Delete();
    }
    
    public <T> Data<T> get(DataType<T> type, T t) {
        return new Data(type, t);
    }
}
