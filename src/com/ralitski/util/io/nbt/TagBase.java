/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ralitski.util.io.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 *
 * @author ralitski
 */
public abstract class TagBase {
    
    //static
    
    //non-static
    
    private String name;
    
    public TagBase(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public abstract Object getValue();

    /*
     * abstract data writing method for tag subclasses. @param out: output
     * stream.
     */
    public abstract void write(DataOutput out, TagManager manager) throws IOException;

    /*
     * abstract data loading method for tag subclasses. @param in: input stream.
     */
    public abstract void read(DataInput in, TagManager manager) throws IOException;
    
}
