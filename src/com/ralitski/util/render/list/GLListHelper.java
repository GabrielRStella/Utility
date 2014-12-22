package com.ralitski.util.render.list;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;

import org.lwjgl.opengl.GL11;

public class GLListHelper {

    public static void getList(ListMaker m) {
        int glId = glGenLists(1);
        
        m.setCallId(glId);

        glNewList(glId, GL_COMPILE);

        m.makeList();

        glEndList();
    }

    public static void deleteList(ListMaker m) {
        if (m.registered()) {
            int glId = m.getCallId();
            GL11.glDeleteLists(glId, 1);
            m.setCallId(0);
        }
    }

    public static void regenList(ListMaker m) {
        if (m.registered()) {
            deleteList(m);
        }
        getList(m);
    }
    
    private static CenteredSquareRenderList squareListCentered = new CenteredSquareRenderList();
    private static UncenteredSquareRenderList squareListUncentered = new UncenteredSquareRenderList();

    public static ListMaker getSquareListCentered() {
        return squareListCentered;
    }

    public static ListMaker getSquareListUncentered() {
        return squareListUncentered;
    }

    private static class UncenteredSquareRenderList extends ListMaker {

        private UncenteredSquareRenderList() {
            GLListHelper.getList(this);
        }

        @Override
        public void makeList() {
            GL11.glBegin(GL11.GL_QUADS);
            {
                GL11.glVertex2f(0F, 0F);
                GL11.glVertex2f(0F, 1F);
                GL11.glVertex2f(1F, 1F);
                GL11.glVertex2f(1F, 0F);
            }
            GL11.glEnd();
        }
    }

    private static class CenteredSquareRenderList extends ListMaker {

        private CenteredSquareRenderList() {
            GLListHelper.getList(this);
        }

        @Override
        public void makeList() {
            GL11.glBegin(GL11.GL_QUADS);
            {
                GL11.glVertex2f(-0.5F, -0.5F);
                GL11.glVertex2f(-0.5F, 0.5F);
                GL11.glVertex2f(0.5F, 0.5F);
                GL11.glVertex2f(0.5F, -0.5F);
            }
            GL11.glEnd();
        }
    }
}
