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
public class TagInteger extends TagBase {
    
    private int value;

    public TagInteger(String name) {
        super(name);
    }

    public TagInteger(String name, int value) {
        super(name);
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void write(DataOutput out, TagManager manager) throws IOException {
        out.writeInt(value);
    }

    @Override
    public void read(DataInput in, TagManager manager) throws IOException {
        value = in.readInt();
    }
    
}
