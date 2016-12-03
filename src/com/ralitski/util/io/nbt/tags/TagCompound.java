/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ralitski.util.io.nbt.tags;

import com.ralitski.util.io.nbt.TagBase;
import com.ralitski.util.io.nbt.TagManager;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ralitski
 */
public class TagCompound extends TagBase {
    
    private Map<String, TagBase> value;

    public TagCompound(String name) {
        super(name);
        value = new HashMap<>();
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void write(DataOutput out, TagManager manager) throws IOException {
        for(TagBase tag : value.values()) {
            manager.writeTag(tag, out);
        }
        manager.writeTag(new TagEnd(), out);
    }

    @Override
    public void read(DataInput in, TagManager manager) throws IOException {
        TagBase tag;
        while((tag = manager.readTag(in)) != null) {
            if(tag instanceof TagEnd) break;
            value.put(tag.getName(), tag);
        }
    }
    
    //manipulation
    
    public void clear() {
        value.clear();
    }
    
    public void addTag(TagBase tag) {
        if(tag instanceof TagEnd) throw new IllegalArgumentException("Can not add TagEnd to TagCompound");
        value.put(tag.getName(), tag);
    }
    
    public boolean hasTag(String name) {
        return value.containsKey(name);
    }
    
    public TagBase getTag(String name) {
        return value.get(name);
    }
    
    public Object getValue(String name) {
        TagBase tag = getTag(name);
        return tag == null ? null : tag.getValue();
    }
    
    public void removeTag(TagBase tag) {
        removeTag(tag.getName());
    }
    
    public void removeTag(String name) {
        value.remove(name);
    }
    
    public Class<? extends TagBase> getClassTypeOf(String name) {
        TagBase tag = getTag(name);
        return tag == null ? null : tag.getClass();
    }
    
    //other stuff...
    
    public boolean getBoolean(String name) {
        Object o = getValue(name);
        return o == null ? false : (boolean)o;
    }
    
    public void setBoolean(String name, boolean value) {
        addTag(new TagBoolean(name, value));
    }
    
    public byte getByte(String name) {
        Object o = getValue(name);
        return o == null ? 0 : (byte)o;
    }
    
    public void setByte(String name, byte value) {
        addTag(new TagByte(name, value));
    }
    
    public double getDouble(String name) {
        Object o = getValue(name);
        return o == null ? 0 : (double)o;
    }
    
    public void setDouble(String name, double value) {
        addTag(new TagDouble(name, value));
    }
    
    public float getFloat(String name) {
        Object o = getValue(name);
        return o == null ? 0 : (float)o;
    }
    
    public void setFloat(String name, float value) {
        addTag(new TagFloat(name, value));
    }
    
    public int getInt(String name) {
        Object o = getValue(name);
        return o == null ? 0 : (int)o;
    }
    
    public void setInt(String name, int value) {
        addTag(new TagInteger(name, value));
    }
    
    public List<TagBase> getList(String name) {
        Object o = getValue(name);
        return o == null ? null : (List<TagBase>)o;
    }
    
    public void setList(String name, List<TagBase> list) {
        addTag(new TagList(name, list));
    }
    
    public long getLong(String name) {
        Object o = getValue(name);
        return o == null ? 0 : (long)o;
    }
    
    public void setLong(String name, long value) {
        addTag(new TagLong(name, value));
    }
    
    public short getShort(String name) {
        Object o = getValue(name);
        return o == null ? 0 : (short)o;
    }
    
    public void setShort(String name, short value) {
        addTag(new TagShort(name, value));
    }
    
    public String getString(String name) {
        Object o = getValue(name);
        return o == null ? null : (String)o;
    }
    
    public void setString(String name, String value) {
        addTag(new TagString(name, value));
    }
}
