package com.ralitski.util.io.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public abstract class DataBase<T> {

    //name of the tag.
    public String name;
    //list of tag types which can be written and read
    public static Map<Byte, Class<? extends DataBase>> typeList = new HashMap<Byte, Class<? extends DataBase>>();
    //list of tag ID strings and bytes
    public static Map<String, Byte> idList = new HashMap<String, Byte>();
    //list of tag types to data sets
    public static Map<Class<? extends DataBase>, TagDataSet> dataList = new HashMap<Class<? extends DataBase>, TagDataSet>();

    public DataBase(String name) {
        this.name = name;
    }

    /*
     * returns the name of the tag. @return: name of the tag.
     */
    public String name() {
        return this.name;
    }

    /*
     * unimplemented generic subclass value method @return: generic value of
     * this tag
     */
    public abstract T value();

    /*
     * abstract data writing method for tag subclasses. @param out: output
     * stream.
     */
    public abstract void write(DataOutput out) throws IOException;

    /*
     * abstract data loading method for tag subclasses. @param in: input stream.
     */
    public abstract void read(DataInput in) throws IOException;

    public Collection<String> toStringCollection(int depth) {
        return Arrays.asList(construct(depth) + this.name + ": " + this.value().toString());
    }

    public String construct(int depth) {
        depth *= 2;
        StringBuilder ret = new StringBuilder(depth);
        for (int i = 0; i < depth; i++) {
            ret.append(" ");
        }
        return ret.toString();
    }

    /*
     * writes a given tag to a given DataOutput object. @param base: NBTBASE to
     * be written @param out: DataOutput given base will be written to.
     */
    public static void writeTag(DataBase base, DataOutput out) {
        try {
//            System.out.println("WRITE " + base.name + " [" + tagName(base) + "]");
            out.writeBoolean(tagLoad(base));
            if (tagLoad(base)) {
                out.writeByte(tagID(base));
            } else {
                out.writeUTF(tagName(base));
            }
            if (tagID(base) == TagEnd.ENDBYTE) {
                return;
            }
            out.writeUTF(base.name());
            base.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * loads a tag from a DataInput object. @param in: DataInput object tag will
     * be loaded from. @return: new NBTBASE object with loaded information.
     */
    public static DataBase readTag(DataInput in) {
        try {
            boolean flag = in.readBoolean();
            byte b = 0;
            if (flag) {
                b = in.readByte();
            } else {
                String tag = in.readUTF();
                b = idList.get(tag);
            }
            if (b == TagEnd.ENDBYTE) {
//                System.out.println("READ END");
                return new TagEnd();
            }
            String s = in.readUTF();
            DataBase base = newTag(b, s);
//            System.out.println("READ " + s + " [" + tagName(base) + "]");
            base.read(in);
            return base;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static DataBase newTag(Class<? extends DataBase> type, String name) {
        return newTag(dataList.get(type).tagID, name);
    }

    /*
     * returns a new instance of a tag with given name and of given type. @param
     * s: tag type @param name: name for new tag @return: new NBTBASE of
     * specified type
     */
    public static DataBase newTag(String s, String name) {
        return newTag(idList.get(s), name);
    }

    /*
     * returns a new instance of a tag with given name and of given type. @param
     * s: tag type @param name: name for new tag @return: new NBTBASE of
     * specified type
     */
    public static DataBase newTag(byte b, String name) {
        try {
            Class<? extends DataBase> c = typeList.get(b);
            DataBase base = (DataBase) c.getConstructor(new Class[]{String.class}).newInstance(new Object[]{name});
            return base;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * returns a String array of stored tag types. @return: ...String array of
     * stored tag types.
     */
    public static String[] tagTypes() {
        Set<String> s = idList.keySet();
        String[] tagList = s.toArray(new String[s.size()]);
        return tagList;
    }

    /*
     * adds a tag type to the list of tags. @param c: class object of new tag
     * @param s: name of tag type @param i: id of tag type @param flag: whether
     * to load with name or id (true for id) @return: true if succeeded in
     * adding the tag type to list
     */
    public static boolean addTag(Class<? extends DataBase> c, String s, int i, boolean flag) {
        try {
            byte b = (byte) i;
            TagDataSet set = new TagDataSet(s, b, flag);
            dataList.put(c, set);
            idList.put(s, b);
            typeList.put(b, c);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static String tagName(DataBase base) {
        return dataList.get(base.getClass()).tagName;
    }

    public static byte tagID(DataBase base) {
        return dataList.get(base.getClass()).tagID;
    }

    public static boolean tagLoad(DataBase base) {
        return dataList.get(base.getClass()).tagLoad;
    }

    static {
        addTag(TagEnd.class, TagEnd.END, TagEnd.ENDBYTE, true);
        addTag(TagCompound.class, "COMPOUND", 1, true);
        addTag(TagByte.class, "BYTE", 2, true);
        addTag(TagShort.class, "SHORT", 3, true);
        addTag(TagInteger.class, "INT", 4, true);
        addTag(TagLong.class, "LONG", 5, true);
        addTag(TagDouble.class, "DOUBLE", 6, true);
        addTag(TagFloat.class, "FLOAT", 7, true);
        addTag(TagString.class, "STRING", 8, true);
        addTag(TagByteArray.class, "BYTEARRAY", 9, true);
        addTag(TagBoolean.class, "BOOLEAN", 10, true);
        addTag(TagList.class, "LIST", 11, true);
    }
}
