package com.ralitski.util;

import java.lang.reflect.Array;
import java.util.regex.Pattern;

public final class StringUtils {
	
	public static boolean matchMulticase(String str, String match) {
		return Pattern.compile(str, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(match).find();
	}
	
//	public static boolean matchMulticaseWord(String str, String match) {
//		return Pattern.compile("\\s" + str + "\\s", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(" " + match + " ").find();
//	}
	
	public static boolean containsRegex(String regex, String string) {
		return Pattern.compile(regex).matcher(string).find();
	}

    /**
     * compress an array of strings into a single string. see {@link #compress(java.lang.String[], java.lang.String, int, int)}
     *
     * @param args   the strings to be compressed
     * @param spacer the spacer string to be put between arguments
     * @return the compressed string
     */
    public static String compress(String[] args, String spacer) {
        return compress(args, spacer, 0, args.length);
    }

    /**
     * compress an array of strings into a single string.
     *
     * @param args   the strings to be compressed
     * @param spacer the spacer string to be put between arguments
     * @param start  the first index from the arguments array that will be used
     * @param end    the first index of the arguments array that will not be used;
     *               that is, the last portion of the returned string will be string located
     *               at args[end - 1]
     * @return the compressed string
     */
    public static String compress(String[] args, String spacer, int start, int end) {
        StringBuilder ret = new StringBuilder();
        for (int i = start; i < end; i++) {
            if (i > start) { ret.append(spacer); }
            ret.append(args[i]);
        }
        return ret.toString();
    }
    
    public static String copy(char c, int copies) {
    	char[] data = new char[copies];
    	for(int i = 0; i < copies; i++) {
    		data[i] = c;
    	}
    	return String.valueOf(data);
    }
    
    //parse enum values from string
    public static <T extends Enum<?>> T[] parseValues(Class<T> clazz, String value) {
        return parseValues(clazz, value, ",");
    }
    
    @SuppressWarnings("unchecked")
	public static <T extends Enum<?>> T[] parseValues(Class<T> clazz, String value, String split) {
        String[] parts = value.split(split);
        T[] ret = (T[])Array.newInstance(clazz, parts.length);
        for(int i = 0; i < ret.length; i++) {
            ret[i] = ArrayUtils.valueOf(clazz, parts[i]);
        }
        return ret;
    }
}
