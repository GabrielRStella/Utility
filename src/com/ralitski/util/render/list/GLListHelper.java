package com.ralitski.util.render.list;

import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glNewList;

import org.lwjgl.opengl.GL11;

public class GLListHelper {

    public static void getList(ListMaker m) {
        if (m.registered()) {
            deleteList(m);
        }
        doGetList(m);
    }

    private static void doGetList(ListMaker m) {
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
    
    /*
     * premade lists (2D squares)
     */
    
    private static CenteredSquareRenderListCCW squareListCenteredCCW = new CenteredSquareRenderListCCW();
    private static UncenteredSquareRenderListCCW squareListUncenteredCCW = new UncenteredSquareRenderListCCW();
    private static CenteredSquareRenderListCW squareListCenteredCW = new CenteredSquareRenderListCW();
    private static UncenteredSquareRenderListCW squareListUncenteredCW = new UncenteredSquareRenderListCW();

    public static ListMaker getSquareListCenteredCCW() {
        return squareListCenteredCCW;
    }

    public static ListMaker getSquareListUncenteredCCW() {
        return squareListUncenteredCCW;
    }

    public static ListMaker getSquareListCentered() {
        return squareListCenteredCW;
    }

    public static ListMaker getSquareListUncenteredCW() {
        return squareListUncenteredCW;
    }

    private static class UncenteredSquareRenderListCCW extends ListMaker {

        private UncenteredSquareRenderListCCW() {
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

    private static class CenteredSquareRenderListCCW extends ListMaker {

        private CenteredSquareRenderListCCW() {
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

    private static class UncenteredSquareRenderListCW extends ListMaker {

        private UncenteredSquareRenderListCW() {
            GLListHelper.getList(this);
        }

        @Override
        public void makeList() {
            GL11.glBegin(GL11.GL_QUADS);
            {
                GL11.glVertex2f(0F, 0F);
                GL11.glVertex2f(1F, 0F);
                GL11.glVertex2f(1F, 1F);
                GL11.glVertex2f(0F, 1F);
            }
            GL11.glEnd();
        }
    }

    private static class CenteredSquareRenderListCW extends ListMaker {

        private CenteredSquareRenderListCW() {
            GLListHelper.getList(this);
        }

        @Override
        public void makeList() {
            GL11.glBegin(GL11.GL_QUADS);
            {
                GL11.glVertex2f(-0.5F, -0.5F);
                GL11.glVertex2f(0.5F, -0.5F);
                GL11.glVertex2f(0.5F, 0.5F);
                GL11.glVertex2f(-0.5F, 0.5F);
            }
            GL11.glEnd();
        }
    }
}
