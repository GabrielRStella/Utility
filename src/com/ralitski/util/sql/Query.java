package com.styx.db.sql;

import com.styx.db.sql.data.Data;
import com.styx.db.sql.data.DataType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ralitski
 */
public class Query {
    
    private boolean doAllFields;
    
    private List<Data> specifications;
    private List<DataType> fieldsToGet;
    
    public Query() {
        specifications = new ArrayList<>();
        fieldsToGet = new ArrayList<>();
    }
    
    public Query addFieldToGet(DataType type) {
        fieldsToGet.add(type);
        return this;
    }
    
    public List<DataType> getFieldsToGet() {
        return fieldsToGet;
    }
    
    public Query setDoAllfields(boolean doAllFields) {
        this.doAllFields = doAllFields;
        return this;
    }
    
    public boolean doAllFields() {
        return doAllFields;
    }
    
    public <T> Query addSpecification(DataType<T> type, T data) {
        return addSpecification(new Data<>(type, data));
    }
    
    public Query addSpecification(Data data) {
        this.specifications.add(data);
        return this;
    }
    
    List<Data> getSpecifications() {
        return this.specifications;
    }
}
