package com.styx.db.sql.data;

/**
 *
 * @author ralitski
 */
public class Data<T> {
    
    private DataType<T> type;
    private T data;
    
    public Data(DataType<T> type, T data) {
        this.type = type;
        this.data = data;
    }
    
    public DataType<T> getType() {
        return type;
    }
    
    public T getData() {
        return data;
    }
    
    //"field=data"
    public String getQuery(String field) {
        return type.getSQLQueryString(field, data);
    }
    
    //"data"
    public String formatDataForSQL() {
        return type.formatDataForSQL(data);
    }
}
