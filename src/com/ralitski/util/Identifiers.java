package com.ralitski.util;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 *
 * @author ralitski
 */
public class Identifiers {
    
    public static class HashcodeIdentifier implements Identifier<Object> {

        @Override
        public int id(Object o) {
            return o.hashCode();
        }
        
    }
    
    public static class ReflectingIdentifier implements Identifier<Object> {
        
        private static ReflectingIdentifier BASE = new ReflectingIdentifier();

        @Override
        public int id(Object o) {
            int value = 0;
            for(Field field : o.getClass().getDeclaredFields()) {
                try {
                    field.setAccessible(true);
                    Object obj = field.get(o);
                    if(useCode(obj)) {
                        value += obj instanceof Object[] ? Arrays.deepHashCode((Object[])obj) : obj.hashCode();
                    } else {
                        value += BASE.id(obj);
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            return value;
        }
        
        private static boolean useCode(Object obj) {
            return obj instanceof String
                    || obj instanceof Character
                    || obj instanceof Number
                    || obj instanceof Object[];
        }
        
    }
}
