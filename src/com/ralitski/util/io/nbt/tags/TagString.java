/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ralitski.util.io.nbt.tags;

import com.ralitski.util.io.nbt.TagBase;
import com.ralitski.util.io.nbt.TagManager;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 *
 * @author ralitski
 */
public class TagString extends TagBase {
    
    private String value;

    public TagString(String name) {
        super(name);
    }

    public TagString(String name, String value) {
        super(name);
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void write(DataOutput out, TagManager manager) throws IOException {
        out.writeUTF(value);
    }

    @Override
    public void read(DataInput in, TagManager manager) throws IOException {
        value = in.readUTF();
    }
    
}
