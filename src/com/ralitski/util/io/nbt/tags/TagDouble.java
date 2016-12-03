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
public class TagDouble extends TagBase {
    
    private double value;

    public TagDouble(String name) {
        super(name);
    }

    public TagDouble(String name, double value) {
        super(name);
        this.value = value;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void write(DataOutput out, TagManager manager) throws IOException {
        out.writeDouble(value);
    }

    @Override
    public void read(DataInput in, TagManager manager) throws IOException {
        value = in.readDouble();
    }
    
}
