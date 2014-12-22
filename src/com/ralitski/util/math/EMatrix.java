package com.ralitski.util.math;

import com.ralitski.util.ArrayUtils;
import com.ralitski.util.StringUtils;
import com.ralitski.util.math.expression.Expression;
import com.ralitski.util.math.expression.Expressions;
import com.ralitski.util.math.geom.d2.func.CosFunction;
import com.ralitski.util.math.geom.d2.func.SinFunction;
import com.ralitski.util.math.var.VariableSet;

/**
 * Represents a generic math matrix, storing a 2-dimensional array of Expression
 * objects. Matrices take an array of [][] to mean [column index][row index].
 * 
 * @author ralitski
 */
public class EMatrix {
    
    /*
     * TODO:
     * addition and multiplication and stuff
     */
    
    public static EMatrix identity(int size) {
        Expression[][] expr = new Expression[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                float value = i == j ? 1 : 0;
                expr[i][j] = Expressions.valueOf(value);
            }
        }
        return new EMatrix(expr);
    }
    
    /**
     * creates a matrix in which each index has a value of zero.
     *
     * @param size the size of the matrix
     * @return an empty matrix
     */
    public static EMatrix empty(int size) {
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
    public static EMatrix empty(int rows, int columns) {
        Expression[][] expr = new Expression[rows][columns];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                expr[i][j] = Expressions.EMPTY_EXPRESSION_0;
            }
        }
        return new EMatrix(expr);
    }
    
    public static EMatrix multiply(EMatrix matrix1, EMatrix matrix2) {
        Expression[][] newTable = multiplyExpr(matrix1, matrix2);
        return newTable != null ? new EMatrix(newTable) : null;
    }
    
    private static Expression[][] multiplyExpr(EMatrix matrix1, EMatrix matrix2) {
        if(matrix1 == null) {
            return matrix2 != null ? matrix2.table : new Expression[0][0];
        } else if(matrix2 == null) {
            return matrix1 != null ? matrix1.table : new Expression[0][0];
        } else if(matrix1.columns == matrix2.rows) {
            Expression[][] table = new Expression[matrix1.rows][matrix2.columns];
            int rows = table.length;
            int columns = table.length > 0 ? table[0].length : (rows = 0);
            for(int column = 0; column < columns; column++) {
                for (int row = 0; row < rows; row++) {
                    Expression[] expr = new Expression[matrix1.columns];
                    for(int column1 = 0; column1 < expr.length; column1++) {
                        expr[column1] = Expressions.multiply(matrix1.table[row][column1], matrix2.table[column1][column]);
                    }
                    table[row][column] = Expressions.add(expr);
                }
            }
            return table;
        }
        return null;
    }
    
    //==========================================================================
    //-----TRANSFORMATION MATRICES----------------------------------------------
    //==========================================================================
    
    public static EMatrix transformTranslate(float... translations) {
        int tableSize = translations.length + 1;
        EMatrix matrix = identity(tableSize);
        for(int i = 0; i < translations.length; i++) {
            matrix.setValue(i + 1, tableSize, translations[i]);
        }
        return matrix;
    }
    
    public static EMatrix transformScale(float... scales) {
        int tableSize = scales.length + 1;
        EMatrix matrix = empty(tableSize);
        for(int i = 0; i < scales.length; i++) {
            matrix.table[i][i] = Expressions.valueOf(scales[i]);
        }
        matrix.table[scales.length][scales.length] = Expressions.valueOf(1);
        return matrix;
    }
    
    public static EMatrix transformTranslate(EMatrix old, float... translations) {
        return multiply(transformTranslate(translations), old);
    }
    
    public static EMatrix transformScale(EMatrix old, float... scales) {
        return multiply(transformScale(scales), old);
    }
    
    //==========================================================================
    //-----OBJECT---------------------------------------------------------------
    //==========================================================================
    
    private int rows;
    private int columns;
    private Expression[][] table;
    
    public EMatrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.table = new Expression[rows][columns];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                table[i][j] = Expressions.EMPTY_EXPRESSION_0;
            }
        }
    }
    
    public EMatrix(Expression[][] table) {
        this.rows = table.length;
        this.columns = table.length > 0 ? table[0].length : (rows = 0);
        this.table = table;
//        for(int i = 0; i < rows; i++) {
//            for(int j = 0; j < columns; j++) {
//                if(table[i][j] == null) table[i][j] = Expressions.EMPTY_EXPRESSION_0;
//            }
//        }
    }
    
    public EMatrix(float[][] table) {
        this.rows = table.length;
        this.columns = table.length > 0 ? table[0].length : (rows = 0);
        this.table = new Expression[rows][columns];
        for(int column = 0; column < columns; column++) {
            for(int row = 0; row < rows; row++) {
                this.table[row][column] = Expressions.valueOf(table[row][column]);
            }
        }
    }
    
    public EMatrix(Object[][] table) {
        this.rows = table.length;
        this.columns = table.length > 0 ? table[0].length : (rows = 0);
        this.table = new Expression[rows][columns];
        for(int column = 0; column < columns; column++) {
            for(int row = 0; row < rows; row++) {
                Object o = table[row][column];
                Expression expr;
                if(o != null) {
                    expr = Expressions.valueOf(o);
                } else {
                    expr = Expressions.EMPTY_EXPRESSION_0;
                }
                this.table[row][column] = expr;
            }
        }
    }
    
    //==========================================================================
    //-----DETERMINANT----------------------------------------------------------
    //==========================================================================
    
    public Expression determinant() {
        if(isSquare()) {
            if(this.rows == 0) {
                return Expressions.EMPTY_EXPRESSION_0;
            } else if(this.rows == 1) {
                return table[0][0];
            } else if(this.rows == 2) {
                //AC - BD
                Expression expr = Expressions.subtract(
                        Expressions.multiply(table[0][0], table[1][1]),
                        Expressions.multiply(table[0][1], table[1][0])
                        );
                return expr;
            } else {
                boolean sign = false;
                Expression[] subMatrixDeterminants = new Expression[columns];
                for(int column = 0; column < columns; column++) {
                    sign = !sign;
                    Expression[][] subMatrixTable = new Expression[rows - 1][columns - 1];
                    int currentColumnSubIndex = 0;
                    int limit = 0;
                    for(int currentColumnIndex = 0; limit++ < columns - 1; currentColumnIndex++) {
                        if(currentColumnIndex == column) currentColumnIndex++;
                        for(int currentRowIndex = 1; currentRowIndex < rows; currentRowIndex++) {
                            Expression e = table[currentRowIndex][currentColumnIndex];
                            subMatrixTable[currentRowIndex - 1][currentColumnSubIndex] = e;
                        }
                        currentColumnSubIndex++;
                    }
                    EMatrix subMatrix = new EMatrix(subMatrixTable);
                    Expression e = Expressions.multiply(subMatrix.determinant(), getValue(1, column + 1));
                    if(!sign) e = Expressions.negative(e);
                    subMatrixDeterminants[column] = e;
                }
                return Expressions.add(subMatrixDeterminants);
            }
        } else {
            return null;
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
    
    public Expression getValue(int row, int column) {
        return table[row - 1][column - 1];
    }
    
    public void setValue(int row, int column, float value) {
        setValue(row, column, Expressions.valueOf(value));
    }
    
    public void setValue(int row, int column, Expression expr) {
        table[row - 1][column - 1] = expr;
    }
    
    public void setValue(int row, int column, Object expr) {
        table[row - 1][column - 1] = Expressions.valueOf(expr);
    }
    
    public boolean add(EMatrix matrix) {
        if(matrix.columns == columns && matrix.rows == this.rows) {
            for(int column = 0; column < columns; column++) {
                for(int row = 0; row < rows; row++) {
                    Expression expr = Expressions.add(table[row][column], matrix.table[row][column]);
                    table[row][column] = expr.simplify(VariableSet.EMPTY_SET);
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
                Expression expr = Expressions.multiply(Expressions.valueOf(scalar), table[row][column]);
                table[row][column] = expr.simplify(VariableSet.EMPTY_SET);
            }
        }
    }
    
    public boolean multiply(EMatrix other) {
        Expression[][] newTable = multiplyExpr(this, other);
        if(newTable != null) {
            this.rows = newTable.length;
            this.columns = newTable.length > 0 ? newTable[0].length : (rows = 0);
            this.table = newTable;
            return true;
        }
        return false;
    }
    
    public EMatrix transpose() {
        return new EMatrix(ArrayUtils.flip(table));
    }
    
    //==========================================================================
    //-----SUB MATRIX-----------------------------------------------------------
    //==========================================================================
    
    public EMatrix subMatrixRow(int row) {
        row--;
        Expression[][] subMatrixTable = new Expression[columns][rows - 1];
        int limit = 0;
        for (int currentColumnIndex = 0; limit++ < columns; currentColumnIndex++) {
            int currentRowSubIndex = 0;
            for (int currentRowIndex = 0; currentRowIndex < rows; currentRowIndex++) {
                if(currentRowIndex == row) currentRowIndex++;
                Expression e = table[currentColumnIndex][currentRowIndex];
                subMatrixTable[currentColumnIndex][currentRowSubIndex] = e;
                currentRowSubIndex++;
            }
        }
        return new EMatrix(subMatrixTable);
    }
    
    public EMatrix subMatrixColumn(int column) {
        column--;
        Expression[][] subMatrixTable = new Expression[columns - 1][rows];
        int limit = 0;
        int currentColumnSubIndex = 0;
        for (int currentColumnIndex = 0; limit++ < columns - 1; currentColumnIndex++) {
            if (currentColumnIndex == column) {
                currentColumnIndex++;
            }
            for (int currentRowIndex = 0; currentRowIndex < rows; currentRowIndex++) {
                Expression e = table[currentColumnIndex][currentRowIndex];
                subMatrixTable[currentColumnSubIndex][currentRowIndex] = e;
            }
            currentColumnSubIndex++;
        }
        return new EMatrix(subMatrixTable);
    }
    
    public EMatrix subMatrix(int row, int column) {
        row--;
        column--;
        Expression[][] subMatrixTable = new Expression[columns - 1][rows - 1];
        int limit = 0;
        int currentColumnSubIndex = 0;
        for (int currentColumnIndex = 0; limit++ < columns - 1; currentColumnIndex++) {
            if (currentColumnIndex == column) {
                currentColumnIndex++;
            }
            int currentRowSubIndex = 0;
            for (int currentRowIndex = 0; currentRowIndex < rows; currentRowIndex++) {
                if(currentRowIndex == row) currentRowIndex++;
                Expression e = table[currentColumnIndex][currentRowIndex];
                subMatrixTable[currentColumnSubIndex][currentRowSubIndex] = e;
                currentRowSubIndex++;
            }
            currentColumnSubIndex++;
        }
        return new EMatrix(subMatrixTable);
    }
    
    //==========================================================================
    //-----EXPRESSIONS----------------------------------------------------------
    //==========================================================================
    
    public void simplify() {
        simplify(VariableSet.EMPTY_SET);
    }
    
    public void simplify(VariableSet set) {
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                table[row][column] = table[row][column].simplify(set);
            }
        }
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
    			String s = table[i][j].toString();
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
