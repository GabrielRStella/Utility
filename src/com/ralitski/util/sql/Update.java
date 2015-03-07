package com.styx.db.sql;

import com.styx.db.sql.data.Data;
import com.styx.db.sql.data.DataType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ralitski
 */
public class Update {
    
    private List<Data> specifications;
    private List<Data> fieldsToChange;
    
    public Update() {
        specifications = new ArrayList<>();
        fieldsToChange = new ArrayList<>();
    }
    
    public <T> Update addFieldToChange(DataType<T> type, T data) {
        return addFieldToChange(new Data<>(type, data));
    }
    
    public Update addFieldToChange(Data type) {
        fieldsToChange.add(type);
        return this;
    }
    
    public List<Data> getFieldsToChange() {
        return fieldsToChange;
    }
    
    public <T> Update addSpecification(DataType<T> type, T data) {
        return addSpecification(new Data<>(type, data));
    }
    
    public Update addSpecification(Data data) {
        this.specifications.add(data);
        return this;
    }
    
    List<Data> getSpecifications() {
        return this.specifications;
    }
}
