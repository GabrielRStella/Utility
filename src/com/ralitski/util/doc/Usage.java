package com.ralitski.util.doc;

/**
 * @author ralitski
 */
public @interface Usage {
    /**
     * For parts of the library meant as an open API.
     */
    public static final int EXTERNAL = 0;
    
    /**
     * For parts of the library that other parts of the library handle.
     */
    public static final int INTERNAL = 1;
    
    /**
     * For parts of the library that are dangerous for external use.
     */
    public static final int DANGEROUS = 2;

    /**
     *
     * @return The usage type of the specified member
     */
    public int value();
}