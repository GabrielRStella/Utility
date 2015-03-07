package com.styx.db.sql.data;

import com.styx.util.ArrayUtils;
import com.styx.util.Stringifier;
import java.sql.*;

/**
 *
 * @author ralitski
 */
public class DataTypes {

    public static class DataTypeBoolean extends DataType<Boolean> {

        @Override
        public String getTableDeclarationString() {
            return "BOOLEAN";
        }

        @Override
        public Boolean get(ResultSet results, String field) throws SQLException {
            return results.getBoolean(field);
        }
    }

    public static class DataTypeInteger extends DataType<Integer> {

        private IntType type;

        public DataTypeInteger(IntType type) {
            this.type = type;
        }

        @Override
        public String getTableDeclarationString() {
            return type.name();
        }

        @Override
        public Integer get(ResultSet results, String field) throws SQLException {
            return results.getInt(field);
        }
    }

    public static enum IntType {

        TINYINT,
        SMALLINT,
        MEDIUMINT,
        INT
    }

    public static class DataTypeLong extends DataType<Long> {

        @Override
        public String getTableDeclarationString() {
            return "BIGINT";
        }

        @Override
        public Long get(ResultSet results, String field) throws SQLException {
            return results.getLong(field);
        }
    }

    public static class DataTypeDecimal extends DataType<Float> {

        private int digits;
        private int decimals;
        private DecimalType type;

        /**
         * creates a floating-point decimal type.
         */
        public DataTypeDecimal() {
            this.type = DecimalType.FLOATING;
        }

        /**
         * creates a fixed-point decimal type.
         *
         * @param digits the number of significant digits for this type
         * @param decimals the number of places allowed after a decimal
         */
        public DataTypeDecimal(int digits, int decimals) {
            this.digits = digits;
            this.decimals = decimals;
            this.type = DecimalType.FIXED;
        }

        @Override
        public String getTableDeclarationString() {
            return this.type == DecimalType.FIXED ? "DECIMAL(" + digits + "," + decimals + ")" : "FLOAT";
        }

        @Override
        public Float get(ResultSet results, String field) throws SQLException {
            return results.getFloat(field);
        }
    }

    public static enum DecimalType {

        FIXED,
        FLOATING
    }

    public static class DataTypeDouble extends DataType<Double> {

        @Override
        public String getTableDeclarationString() {
            return "DOUBLE";
        }

        @Override
        public Double get(ResultSet results, String field) throws SQLException {
            return results.getDouble(field);
        }
    }

    public static class DataTypeChar extends DataType<String> {

//    public static final int MAX_SIZE = 8000;
        public static final int MAX_SIZE = 255;
        private int size;

        public DataTypeChar(int size) {
            this.size = size;
        }

        @Override
        public String getTableDeclarationString() {
            return "CHAR(" + size + ")";
        }

        @Override
        protected String formatDataForSQL(String value) {
            return "'" + value + "'";
        }

        @Override
        public String get(ResultSet results, String field) throws SQLException {
            return results.getString(field);
        }
    }

    public static class DataTypeVarchar extends DataTypeChar {

        public DataTypeVarchar(int size) {
            super(size);
        }

        @Override
        public String getTableDeclarationString() {
            return "VAR" + super.getTableDeclarationString();
        }
    }

    public static class DataTypeText extends DataType<String> {

        private TextType type;

        public DataTypeText() {
            this(TextType.DEFAULT);
        }

        public DataTypeText(TextType type) {
            this.type = type;
        }

        @Override
        public String getTableDeclarationString() {
            return type.declare();
        }

        @Override
        public String get(ResultSet results, String field) throws SQLException {
            return results.getString(field);
        }
    }

    public static enum TextType {

        TINY("TINY"),
        DEFAULT(""),
        MEDIUM("MEDIUM"),
        LONG("LONG");
        private String declaration;

        TextType(String declaration) {
            this.declaration = declaration;
        }

        public String declare() {
            return declaration + "TEXT";
        }
    }

    public class DataTypeEnum<T extends Enum> extends DataType<T> {

        private Class<T> enumType;
        private EnumString<T> stringer;

        public DataTypeEnum(Class<T> enumType) {
            this.enumType = enumType;
            stringer = new EnumString<>();
        }

        @Override
        public T get(ResultSet results, String field) throws SQLException {
            return ArrayUtils.valueOf(enumType, stringer.unString(results.getString(field)));
        }

        @Override
        public String getTableDeclarationString() {
            return "VARCHAR(255)"; //max size, because whatever
        }

        @Override
        protected String formatDataForSQL(T value) {
            return stringer.toString(value);
        }

        private class EnumString<T extends Enum> implements Stringifier<T> {

            @Override
            public String toString(T t) {
                return "'" + t.name() + "'";
            }

            public String unString(String string) {
                return string.substring(1, string.length() - 1);
            }
        }
    }

    public static class DataTypeTime extends DataType<Time> {

        @Override
        public String getTableDeclarationString() {
            return "TIME";
        }

        @Override
        public Time get(ResultSet results, String field) throws SQLException {
            return results.getTime(field);
        }
    }

    public static class DataTypeTimestamp extends DataType<Timestamp> {

        @Override
        public String getTableDeclarationString() {
            return "TIMESTAMP";
        }

        @Override
        public Timestamp get(ResultSet results, String field) throws SQLException {
            return results.getTimestamp(field);
        }
    }

    public static class DataTypeDate extends DataType<Date> {

        @Override
        public String getTableDeclarationString() {
            return "DATE";
        }

        @Override
        public Date get(ResultSet results, String field) throws SQLException {
            return results.getDate(field);
        }
    }
}