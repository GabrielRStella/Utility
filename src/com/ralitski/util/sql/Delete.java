package com.styx.db.sql;

import com.styx.db.sql.data.Data;
import com.styx.db.sql.data.DataType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ralitski
 */
public class Delete {
    
    private List<Data> specifications;
    
    public Delete() {
        specifications = new ArrayList<>();
    }
    
    public <T> Delete addSpecification(DataType<T> type, T data) {
        return addSpecification(new Data<>(type, data));
    }
    
    public Delete addSpecification(Data data) {
        this.specifications.add(data);
        return this;
    }
    
    List<Data> getSpecifications() {
        return this.specifications;
    }
    
    public int size() {
        return specifications.size();
    }
}
