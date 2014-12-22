package com.ralitski.util.math;

import com.ralitski.util.ArrayUtils;
import com.ralitski.util.StringUtils;
import com.ralitski.util.math.var.VariableSet;

/**
 * Represents a generic math matrix, storing a 2-dimensional array of Expression
 * objects. Matrices take an array of [][] to mean [column index][row index].
 * 
 * @author ralitski
 */
public class Matrix {
    
    /*
     * TODO:
     * addition and multiplication and stuff
     */
    
    public static Matrix identity(int size) {
        float[][] expr = new float[size][size];
        for(int i = 0; i < size; i++) {
            expr[i][i] = 1;
        }
        return new Matrix(expr);
    }
    
    /**
     * creates a matrix in which each index has a value of zero.
     *
     * @param size the size of the matrix
     * @return an empty matrix
     */
    public static Matrix empty(int size) {
        return empty(size, size);
    }
    
    /**
     * creates a matrix in which each index has a value of zero.
     *
     * @param rows the number of rows in this matrix
     * @param columns the number of columns in this matrix
     * @return a matrix of the specified dimensions in which every index has a
     * value of zero
     */
    public static Matrix empty(int rows, int columns) {
        return new Matrix(new float[rows][columns]);
    }
    
    public static Matrix multiply(Matrix matrix1, Matrix matrix2) {
        float[][] newTable = multiplyExpr(matrix1, matrix2);
        return newTable != null ? new Matrix(newTable) : null;
    }
    
    private static float[][] multiplyExpr(Matrix matrix1, Matrix matrix2) {
        if(matrix1 == null) {
            return matrix2 != null ? matrix2.table : new float[0][0];
        } else if(matrix2 == null) {
            return matrix1 != null ? matrix1.table : new float[0][0];
        } else if(matrix1.columns == matrix2.rows) {
            float[][] table = new float[matrix1.rows][matrix2.columns];
            int rows = table.length;
            int columns = table.length > 0 ? table[0].length : (rows = 0);
            for(int column = 0; column < columns; column++) {
                for (int row = 0; row < rows; row++) {
                    float total = 0;
                    for(int column1 = 0; column1 < matrix1.columns; column1++) {
                        total += matrix1.table[row][column1] * matrix2.table[column1][column];
                    }
                    table[row][column] = total;
                }
            }
            return table;
        }
        return null;
    }
    
    //==========================================================================
    //-----TRANSFORMATION MATRICES----------------------------------------------
    //==========================================================================
    
    public static Matrix transformTranslate(float... translations) {
        int tableSize = translations.length + 1;
        Matrix matrix = identity(tableSize);
        int column = tableSize - 1;
        for(int i = 0; i < translations.length; i++) {
        	matrix.table[i][column] = translations[i];
        }
        return matrix;
    }
    
    public static Matrix transformScale(float... scales) {
        int tableSize = scales.length + 1;
        Matrix matrix = empty(tableSize);
        for(int i = 0; i < scales.length; i++) {
            matrix.table[i][i] = scales[i];
        }
        matrix.table[scales.length][scales.length] = 1;
        return matrix;
    }
    
    public static Matrix transformTranslate(Matrix old, float... translations) {
        return multiply(transformTranslate(translations), old);
    }
    
    public static Matrix transformScale(Matrix old, float... scales) {
        return multiply(transformScale(scales), old);
    }
    
    //==========================================================================
    //-----OBJECT---------------------------------------------------------------
    //==========================================================================
    
    private int rows;
    private int columns;
    private float[][] table;
    
    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.table = new float[rows][columns];
    }
    
    public Matrix(float[][] table) {
        this.rows = table.length;
        this.columns = table.length > 0 ? table[0].length : (rows = 0);
        this.table = table;
    }
    
    //==========================================================================
    //-----DETERMINANT----------------------------------------------------------
    //==========================================================================
    
    public float determinant() {
        if(isSquare()) {
            if(this.rows == 0) {
                return 0;
            } else if(this.rows == 1) {
                return table[0][0];
            } else if(this.rows == 2) {
                //AC - BD
                return table[0][0] * table[1][1] - table[0][1] * table[1][0];
            } else {
                boolean sign = false;
                float subMatrixDeterminant = 0;
                for(int column = 0; column < columns; column++) {
                    sign = !sign;
                    float[][] subMatrixTable = new float[rows - 1][columns - 1];
                    int currentColumnSubIndex = 0;
                    int limit = 0;
                    for(int currentColumnIndex = 0; limit++ < columns - 1; currentColumnIndex++) {
                        if(currentColumnIndex == column) currentColumnIndex++;
                        for(int currentRowIndex = 1; currentRowIndex < rows; currentRowIndex++) {
                            subMatrixTable[currentRowIndex - 1][currentColumnSubIndex] = table[currentRowIndex][currentColumnIndex];
                        }
                        currentColumnSubIndex++;
                    }
                    Matrix subMatrix = new Matrix(subMatrixTable);
                    float f = subMatrix.determinant() * getValue(1, column + 1);
                    if(!sign) f = -f;
                    subMatrixDeterminant += f;
                }
                return subMatrixDeterminant;
            }
        } else {
            return Float.NaN;
        }
    }
    
    //==========================================================================
    //-----GET, SET, CHANGE-----------------------------------------------------
    //==========================================================================
    
    public boolean isSquare() {
        return rows == columns;
    }
    
    public int getRows() {
    	return rows;
    }
    
    public int getColumns() {
    	return columns;
    }
    
    public float getValue(int row, int column) {
        return table[row - 1][column - 1];
    }
    
    public void setValue(int row, int column, float expr) {
        table[row - 1][column - 1] = expr;
    }
    
    public boolean add(Matrix matrix) {
        if(matrix.columns == columns && matrix.rows == this.rows) {
            for(int column = 0; column < columns; column++) {
                for(int row = 0; row < rows; row++) {
                    table[row][column] = table[row][column] + matrix.table[row][column];
                }
            }
            return true;
        } else {
            return false;
        }
    }
    
    public void multiply(float scalar) {
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                table[row][column] *= scalar;
            }
        }
    }
    
    public boolean multiply(Matrix other) {
        float[][] newTable = multiplyExpr(this, other);
        if(newTable != null) {
            this.rows = newTable.length;
            this.columns = newTable.length > 0 ? newTable[0].length : (rows = 0);
            this.table = newTable;
            return true;
        }
        return false;
    }
    
    public Matrix transpose() {
        return new Matrix(ArrayUtils.flip(table));
    }
    
    //==========================================================================
    //-----SUB MATRIX-----------------------------------------------------------
    //==========================================================================
    
    public Matrix subMatrixRow(int row) {
        row--;
        float[][] subMatrixTable = new float[columns][rows - 1];
        int limit = 0;
        for (int currentColumnIndex = 0; limit++ < columns; currentColumnIndex++) {
            int currentRowSubIndex = 0;
            for (int currentRowIndex = 0; currentRowIndex < rows; currentRowIndex++) {
                if(currentRowIndex == row) currentRowIndex++;
                subMatrixTable[currentColumnIndex][currentRowSubIndex] = table[currentColumnIndex][currentRowIndex];
                currentRowSubIndex++;
            }
        }
        return new Matrix(subMatrixTable);
    }
    
    public Matrix subMatrixColumn(int column) {
        column--;
        float[][] subMatrixTable = new float[columns - 1][rows];
        int limit = 0;
        int currentColumnSubIndex = 0;
        for (int currentColumnIndex = 0; limit++ < columns - 1; currentColumnIndex++) {
            if (currentColumnIndex == column) {
                currentColumnIndex++;
            }
            for (int currentRowIndex = 0; currentRowIndex < rows; currentRowIndex++) {
                subMatrixTable[currentColumnSubIndex][currentRowIndex] = table[currentColumnIndex][currentRowIndex];
            }
            currentColumnSubIndex++;
        }
        return new Matrix(subMatrixTable);
    }
    
    public Matrix subMatrix(int row, int column) {
        row--;
        column--;
        float[][] subMatrixTable = new float[columns - 1][rows - 1];
        int limit = 0;
        int currentColumnSubIndex = 0;
        for (int currentColumnIndex = 0; limit++ < columns - 1; currentColumnIndex++) {
            if (currentColumnIndex == column) {
                currentColumnIndex++;
            }
            int currentRowSubIndex = 0;
            for (int currentRowIndex = 0; currentRowIndex < rows; currentRowIndex++) {
                if(currentRowIndex == row) currentRowIndex++;
                subMatrixTable[currentColumnSubIndex][currentRowSubIndex] = table[currentColumnIndex][currentRowIndex];
                currentRowSubIndex++;
            }
            currentColumnSubIndex++;
        }
        return new Matrix(subMatrixTable);
    }
    
    //==========================================================================
    //-----MISC-----------------------------------------------------------------
    //==========================================================================
    
    @Override
    public String toString() {
    	String[][] strings = new String[rows][columns];
    	int[] biggestInColumn = new int[columns];
    	for(int i = 0; i < rows; i++) {
    		for(int j = 0; j < columns; j++) {
    			String s = "" + table[i][j];
    			strings[i][j] = s;
    			biggestInColumn[j] = Math.max(biggestInColumn[j], s.length());
    		}
    	}
    	StringBuilder b = new StringBuilder();
    	for(int i = 0; i < rows; i++) {
    		b.append("[");
    		for(int j = 0; j < columns; j++) {
    			if(j > 0) {
    				b.append(", ");
    			}
    			String s = strings[i][j];
    			if(s.length() < biggestInColumn[j]) {
    				s = s + StringUtils.copy(' ', biggestInColumn[j] - s.length());
    			}
    			b.append(s);
    		}
    		b.append("]\n");
    	}
    	return b.toString();
    }
    
}
