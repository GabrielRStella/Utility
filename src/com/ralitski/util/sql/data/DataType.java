package com.styx.db.sql.data;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ralitski
 */
public abstract class DataType<T> {
    
    /**
     * gets the string used to declare this data type in an SQL table, eg
     * "TINYINT" or "VARCHAR(50)"
     *
     * @return the string used to declare this data type in an SQL table
     */
    public abstract String getTableDeclarationString();
    
    public String getSQLQueryString(String field, T value) {
        return field + "=" + formatDataForSQL(value);
    }
    
    /**
     * gets the string used to query this data type in a table, using the given
     * data (eg, with tinyint, "fieldName=value" so the method would return
     * "value", whereas with VARCHAR it would be "fieldName='value'")
     *
     * @param value the value to be put in SQL
     * @return the formatted string, to have a name appended to it
     */
    protected String formatDataForSQL(T value) {
        return value.toString();
    }
    
    public abstract T get(ResultSet results, String field) throws SQLException;
}
