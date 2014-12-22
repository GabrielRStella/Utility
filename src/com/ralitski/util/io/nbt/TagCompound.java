package com.ralitski.util.io.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TagCompound extends DataBase<Map<String, DataBase<?>>> implements Iterable<DataBase<?>> {

    public Map<String, DataBase<?>> value = new HashMap<String, DataBase<?>>();

    public TagCompound(String s) {
        super(s);
    }

    @Override
    public Map<String, DataBase<?>> value() {
        return this.value;
    }

    @Override
    public Collection<String> toStringCollection(int depth) {
        List<String> ret = new ArrayList<String>();
        ret.add(construct(depth) + this.name);
        for (String s : value.keySet()) {
            DataBase<?> b = value.get(s);
            //ret.add(construct(depth + 1) + s + ":");
            ret.addAll(b.toStringCollection(depth + 1));
        }
        return ret;
    }
    
    /*
     * TODO: loading breaks if the tag compound is empty. why? :^(
     */

    @Override
    public void read(DataInput in) throws IOException {
        DataBase<?> temp;
        while ((temp = DataBase.readTag(in)) != null) {
            if (temp instanceof TagEnd) {
                break;
            }
            this.value.put(temp.name(), temp);
        }
    }

    @Override
    public void write(DataOutput out) throws IOException {
        for (DataBase<?> base : this.value.values()) {
            DataBase.writeTag(base, out);
        }
        DataBase.writeTag(new TagEnd(), out);
    }

    /*
     * adds a tag to the compound's storage map. @param base: tag to be stored
     */
    public void addValue(DataBase<?> base) {
        this.value.put(base.name(), base);
    }

    /*
     * tells whether the compound has a value under a given name @param key:
     * name of value @return: whether the tag has a value saved for given name
     */
    public boolean hasValue(String key) {
        return this.value.containsKey(key);
    }

    /*
     * returns the tag object stored in the given name @param key: name of tag
     * @return: tag
     */
    public DataBase<?> getTag(String key) {
        return this.value.get(key);
    }

    /*
     * removes a tag from the compound's storage map. @param key: the stored
     * name of the tag to remove
     */
    public void removeValue(String key) {
        this.value.remove(key);
    }

    /*
     * clears the stored value map.
     */
    public void clearValues() {
        this.value.clear();
    }

    /*
     * retrieves the type of tag stored under a given name. @param key: tag to
     * look up @return: tag type of value (if found)
     */
    public String tagTypeOf(String key) {
        DataBase<?> base = this.value.get(key);
        return DataBase.tagName(base);
    }

    /*
     * returns the value of the tag with the specified name if stored in the
     * compound's map. typically used if the compound has stored a type of
     * NBTBASE not in the default set. @param key: name of tag to be returned
     * @return: value stored with given name
     */
    public Object getValue(String key) {
        DataBase<?> base = this.value.get(key);
        return base == null ? null : base.value();
    }

    /*
     * returns an array containing all of the values in the compound tag's
     * storage map. @return: array of all values
     */
    public String[] values() {
        Set<String> set = this.value.keySet();
        if (set == null) {
            return null;
        }
        return set.toArray(new String[set.size()]);
    }

    public DataBase<?>[] valueArray() {
        Collection<DataBase<?>> col = this.value.values();
        return col.toArray(new DataBase[col.size()]);
    }

    /*
     * returns byte value stored by given name, or 0 if key not found @param
     * key: name which will be looked up @return: byte value found, or 0 if not
     * found
     */
    public byte getByte(String key) {
        Object obj = this.getValue(key);
        if (obj != null && obj instanceof Byte) {
            return (Byte) obj;
        }
        return 0;
    }

    /*
     * returns short value stored by given name, or 0 if key not found @param
     * key: name which will be looked up @return: short value found, or 0 if not
     * found
     */
    public short getShort(String key) {
        Object obj = this.getValue(key);
        if (obj != null && obj instanceof Short) {
            return (Short) obj;
        }
        return 0;
    }

    /*
     * returns integer value stored by given name, or 0 if key not found @param
     * key: name which will be looked up @return: integer value found, or 0 if
     * not found
     */
    public int getInteger(String key) {
        Object obj = this.getValue(key);
        if (obj != null && obj instanceof Integer) {
            return (Integer) obj;
        }
        return 0;
    }

    /*
     * returns long value stored by given name, or 0 if key not found @param
     * key: name which will be looked up @return: long value found, or 0 if not
     * found
     */
    public long getLong(String key) {
        Object obj = this.getValue(key);
        if (obj != null && obj instanceof Long) {
            return (Long) obj;
        }
        return 0;
    }

    /*
     * returns double value stored by given name, or 0 if key not found @param
     * key: name which will be looked up @return: double value found, or 0 if
     * not found
     */
    public double getDouble(String key) {
        Object obj = this.getValue(key);
        if (obj != null && obj instanceof Double) {
            return (Double) obj;
        }
        return 0;
    }

    /*
     * returns float value stored by given name, or 0 if key not found @param
     * key: name which will be looked up @return: float value found, or 0 if not
     * found
     */
    public float getFloat(String key) {
        Object obj = this.getValue(key);
        if (obj != null && obj instanceof Float) {
            return (Float) obj;
        }
        return 0;
    }

    /*
     * returns string value stored by given name, or "" if key not found @param
     * key: name which will be looked up @return: string value found, or "" if
     * not found
     */
    public String getString(String key) {
        Object obj = this.getValue(key);
        if (obj != null && obj instanceof String) {
            return (String) obj;
        }
        return "";
    }

    public boolean getBoolean(String key) {
        Object obj = this.getValue(key);
        if (obj != null && obj instanceof Boolean) {
            return (Boolean) obj;
        }
        return false;
    }

    /*
     * stores a byte value under the given name. @param key: name byte will be
     * stored under @param v: byte to be stored
     */
    public void setByte(String key, byte v) {
        this.addValue(new TagByte(key, v));
    }

    /*
     * stores a short value under the given name. @param key: name short will be
     * stored under @param v: short to be stored
     */
    public void setShort(String key, short v) {
        this.addValue(new TagShort(key, v));
    }

    /*
     * stores an integer value under the given name. @param key: name integer
     * will be stored under @param v: integer to be stored
     */
    public void setInteger(String key, int v) {
        this.addValue(new TagInteger(key, v));
    }

    /*
     * stores a long value under the given name. @param key: name long will be
     * stored under @param v: long to be stored
     */
    public void setLong(String key, long v) {
        this.addValue(new TagLong(key, v));
    }

    /*
     * stores a double value under the given name. @param key: name double will
     * be stored under @param v: double to be stored
     */
    public void setDouble(String key, double v) {
        this.addValue(new TagDouble(key, v));
    }

    /*
     * stores a float value under the given name. @param key: name float will be
     * stored under @param v: float to be stored
     */
    public void setFloat(String key, float v) {
        this.addValue(new TagFloat(key, v));
    }

    /*
     * stores a string value under the given name. @param key: name string will
     * be stored under @param v: string to be stored
     */
    public void setString(String key, String v) {
        this.addValue(new TagString(key, v));
    }

    public void setBoolean(String key, boolean v) {
        this.addValue(new TagBoolean(key, v));
    }

    @Override
    public Iterator<DataBase<?>> iterator() {
        return this.value.values().iterator();
    }
}
