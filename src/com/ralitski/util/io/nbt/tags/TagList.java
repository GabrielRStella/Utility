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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ralitski
 */
public class TagList extends TagBase {
    
    private List<TagBase> value;

    public TagList(String name) {
        super(name);
    }
    
    public TagList(String name, List<TagBase> value) {
        super(name);
        this.value = value;
    }

    @Override
    public List<TagBase> getValue() {
        return value;
    }

    @Override
    public void write(DataOutput out, TagManager manager) throws IOException {
        out.writeInt(value.size());
        for(TagBase tag : value) {
            manager.writeTag(tag, out);
        }
    }

    @Override
    public void read(DataInput in, TagManager manager) throws IOException {
        int size = in.readInt();
        value = new ArrayList<>(size);
        for(int i = 0; i < size; i++) {
            TagBase tag = manager.readTag(in);
            value.add(tag);
        }
    }
    
}
