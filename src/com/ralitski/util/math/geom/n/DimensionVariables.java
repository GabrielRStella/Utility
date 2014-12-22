package com.ralitski.util.math.geom.n;

/**
 *
 * @author ralitski
 */
public class DimensionVariables {
    
    private static final String DIMENSION = "d";
//    private static final String VECTOR = "v";
    
    public static int dimensionOf(String variable) {
        if(variable.startsWith(DIMENSION)) {
            try {
                return Integer.parseInt(variable.substring(1));
            } catch(NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }
    
    public static String variableOf(int dimension) {
//        System.out.println(dimension);
        return DIMENSION + "" + dimension;
    }
    
//    public static int vectorDimensionOf(String variable) {
//        if(variable.startsWith(VECTOR)) {
//            try {
//                return Integer.parseInt(variable.substring(1));
//            } catch(NumberFormatException e) {
//                return 0;
//            }
//        }
//        return 0;
//    }
//    
//    public static String vectorVariableOf(int dimension) {
//        return VECTOR + dimension;
//    }
}
