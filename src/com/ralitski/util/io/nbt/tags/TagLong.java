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
public class TagLong extends TagBase {
    
    private long value;

    public TagLong(String name) {
        super(name);
    }

    public TagLong(String name, long value) {
        super(name);
        this.value = value;
    }

    @Override
    public Long getValue() {
        return value;
    }

    @Override
    public void write(DataOutput out, TagManager manager) throws IOException {
        out.writeLong(value);
    }

    @Override
    public void read(DataInput in, TagManager manager) throws IOException {
        value = in.readLong();
    }
    
}
