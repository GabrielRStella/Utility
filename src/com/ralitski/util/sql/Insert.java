package com.styx.db.sql;

import com.styx.db.sql.data.Data;
import com.styx.db.sql.data.DataType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ralitski
 */
public class Insert {
    
    private List<Data> data;
    
    public Insert() {
        data = new ArrayList<>();
    }
    
    public <T> Insert addData(DataType<T> type, T data) {
        return addData(new Data<>(type, data));
    }
    
    public Insert addData(Data data) {
        this.data.add(data);
        return this;
    }
    
    List<Data> getData() {
        return this.data;
    }
    
    public int size() {
        return data.size();
    }
}
