package com.ralitski.util;

import java.lang.reflect.Field;

//TODO: extend ValueList
public class ReflectingValueList {
	
	private Object host;
	private String[] fieldNameCache;

	public ReflectingValueList(Object host) {
		this.host = host;
		this.makeFieldNameCache();
	}
	
	private void makeFieldNameCache() {
        Field[] fields = this.host.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for(int i = 0; i < fields.length; i++) fieldNames[i] = fields[i].getName();
        this.fieldNameCache = fieldNames;
	}
	
	public Class<?> classTypeOf(String key) {
        try {
			return this.host.getClass().getField(key).getType();
		} catch (Exception e) {
	        return null;
		}
	}

    /*
     * retrieves the type of tag stored under a given name. @param key: tag to
     * look up @return: tag type of value (if found)
     */
    public String dataTypeOf(String key) {
    	Class<?> c = classTypeOf(key);
    	return c != null ? c.getSimpleName() : "";
    }
    
    public String[] keys() {
        return this.fieldNameCache;
    }
    
    public Object get(String field) {
    	try {
			return this.host.getClass().getField(field).get(this.host);
		} catch (Exception e) {
			return null;
		}
    }
    
    //returns true if successful
    public boolean set(String field, Object value) {
		try {
			Field f = this.host.getClass().getField(field);
			f.set(host, value);
		} catch (Exception e) {
			return false;
		}
		return true;
    }
}
