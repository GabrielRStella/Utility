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
public class TagEnd extends TagBase {

    public TagEnd() {
        super("");
    }

    public TagEnd(String name) {
        super("");
    }

    @Override
    public Object getValue() {
        return null;
    }

    @Override
    public void write(DataOutput out, TagManager manager) throws IOException {
    }

    @Override
    public void read(DataInput in, TagManager manager) throws IOException {
    }
    
}
