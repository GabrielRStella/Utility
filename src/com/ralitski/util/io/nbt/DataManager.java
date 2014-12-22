package com.ralitski.util.io.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataManager {

    public File file;
    public DataInputStream in;
    public DataOutputStream out;

    public DataManager(String s) {
        this(new File(s));
    }

    public DataManager(File f) {
        this.file = f;
        try {
            this.file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupStream() {
        try {
            this.in = new DataInputStream(new FileInputStream(this.file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.out = new DataOutputStream(new FileOutputStream(this.file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearStream() {
        if (in != null) {
            try {
                in.close();
            } catch (IOException ex) {
                Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.in = null;
        if (out != null) {
            try {
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(DataManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.out = null;
    }

    public DataBase readFile() {
        try {
            if (this.in == null) {
                this.in = new DataInputStream(new FileInputStream(this.file));
            }
            DataBase tag = DataBase.readTag(in);
            in.close();
            return tag;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void writeFile(DataBase base) {
        try {
            if (this.out == null) {
                this.out = new DataOutputStream(new FileOutputStream(this.file));
            }
            DataBase.writeTag(base, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
