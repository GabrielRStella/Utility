package com.ralitski.util.render.list;

import org.lwjgl.opengl.GL11;

import com.ralitski.util.gui.render.RenderList;

//implements RenderList for easy GUI system compatibility
public abstract class ListMaker implements RenderList {

    private int callId;

    /*
     * do the renderin'
     */
    public abstract void makeList();

    void setCallId(int i) {
        this.callId = i;
    }

    public int getCallId() {
        return this.callId;
    }

    public void call() {
        if(callId >= 0) GL11.glCallList(this.callId);
    }
    
    //also registers the list
    public void compile() {
        GLListHelper.getList(this);
    }

    public boolean registered() {
        return this.callId > 0;
    }

	public void delete() {
		GLListHelper.deleteList(this);
	}
}