package com.ralitski.util.render.list;

import org.lwjgl.opengl.GL11;

public abstract class ListMaker {

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
    
    public void recompile() {
        GLListHelper.regenList(this);
    }
    
    public void register() {
        GLListHelper.getList(this);
    }

    public boolean registered() {
        return this.callId > 0;
    }

	public void delete() {
		GLListHelper.deleteList(this);
	}
}