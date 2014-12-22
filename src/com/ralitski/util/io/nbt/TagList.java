package com.ralitski.util.io.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ralitski
 */
public class TagList<T extends DataBase<?>> extends DataBase<List<T>> {
    
    private Class<T> type;
    private List<T> value;
    
    public TagList(String name) {
        super(name);
    }
    
    public TagList(String name, Class<T> type) {
    	this(name, type, new ArrayList<T>());
    }
    
    public TagList(String name, Class<T> type, List<T> value) {
        super(name);
        this.type = type;
        this.value = value;
    }

    @Override
    public List<T> value() {
        return this.value;
    }

	public Collection<String> toStringCollection(int depth) {
		List<String> ret = new ArrayList<String>();
		ret.add(construct(depth) + this.name + ":");
		for(DataBase<?> b : this.value) {
//			ret.add(construct(depth) + b.name + ": ");
			for(String s : b.toStringCollection(depth + 1)) {
				ret.add(s);
			}
		}
		return ret;
	}
    
    public Class<? extends DataBase<?>> getType() {
        return this.type;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(this.type.getName());
        for(DataBase<?> b : this.value) {
            out.writeBoolean(true);
            out.writeUTF(b.name());
            b.write(out);
        }
        out.writeBoolean(false);
    }

    @SuppressWarnings("unchecked")
	@Override
    public void read(DataInput in) throws IOException {
        try {
            this.type = (Class<T>)Class.forName(in.readUTF());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TagList.class.getName()).log(Level.SEVERE, "TagList could not parse a class type", ex);
            return;
        } catch(ClassCastException ex) {
            Logger.getLogger(TagList.class.getName()).log(Level.SEVERE, "TagList tried to load invalid class type", ex);
            return;
        }
        this.value = new ArrayList<T>();
        if(in.readBoolean()) {
            do {
                DataBase b = DataBase.newTag(type, in.readUTF());
                b.read(in);
                this.value.add((T)b);
            } while (in.readBoolean());
        }
    }

}
