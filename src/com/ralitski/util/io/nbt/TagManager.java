/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ralitski.util.io.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ralitski
 */
public class TagManager {
    
    public static final byte ID_RESERVED = -1;
    
    private Map<Object, Class<? extends TagBase>> types = new HashMap<>(); //String or Byte -> tag class
    private Map<Class<? extends TagBase>, Object> id = new HashMap<>(); //String or Byte -> tag class
    
    public TagBase newTag(Class<? extends TagBase> type, String name) {
        try {
            return (TagBase)type.getConstructor(new Class[]{String.class}).newInstance(new Object[]{name});
        } catch (Exception ex) {
            return null;
        }
    }
    
    public TagBase newTag(byte id, String name) {
        return newTag(types.get(id), name);
    }
    
    public TagBase newTag(String id, String name) {
        return newTag(types.get(id), name);
    }
    
    public void addTag(String id, Class<? extends TagBase> c) {
        types.put(id, c);
    }
    
    public void addTag(byte id, Class<? extends TagBase> c) {
        if(id != ID_RESERVED) types.put(id, c);
        else throw new IllegalArgumentException("Tag id can not be equal to the reserved byte " + ID_RESERVED);
    }
    
    public Object getId(TagBase tag) {
        return id.get(tag.getClass());
    }
    
    public void writeTag(TagBase tag, DataOutput out) throws IOException {
        Object id = this.id.get(tag.getClass());
        if(id instanceof Byte) {
            out.writeByte((Byte)id);
        } else if(id instanceof String) {
            out.writeByte(ID_RESERVED);
            out.writeUTF((String)id);
        } else {
            throw new IllegalStateException("Tag type found with invalid id: " + id);
        }
        out.writeUTF(tag.getName());
        tag.write(out, this);
    }
    
    public TagBase readTag(DataInput in) throws IOException {
        byte id_Byte = in.readByte();
        Object id = id_Byte;
        if(id_Byte == ID_RESERVED) {
            id = in.readUTF();
        }
        Class<? extends TagBase> type = types.get(id);
        String name = in.readUTF();
        TagBase tag = newTag(type, name);
        tag.read(in, this);
        return tag;
    }
    
    public void addDefaults() {
//        addTag(TagEnd.class, TagEnd.END, TagEnd.ENDBYTE, true);
//        addTag(TagCompound.class, "COMPOUND", 1, true);
//        addTag(TagByte.class, "BYTE", 2, true);
//        addTag(TagShort.class, "SHORT", 3, true);
//        addTag(TagInteger.class, "INT", 4, true);
//        addTag(TagLong.class, "LONG", 5, true);
//        addTag(TagDouble.class, "DOUBLE", 6, true);
//        addTag(TagFloat.class, "FLOAT", 7, true);
//        addTag(TagString.class, "STRING", 8, true);
//        addTag(TagByteArray.class, "BYTEARRAY", 9, true);
//        addTag(TagBoolean.class, "BOOLEAN", 10, true);
//        addTag(TagList.class, "LIST", 11, true);
    }
}
