package com.ralitski.util.gui;

import com.ralitski.util.gui.render.GuiRenderer;
import com.ralitski.util.gui.render.RenderListBounded;
import com.ralitski.util.input.event.KeyEvent;
import com.ralitski.util.input.event.MouseEvent;
import com.ralitski.util.render.img.Image;

public class ImageCanvas extends ComponentAbstract {
	
	private Image image;
	private RenderListBounded imageList;
	private int align;
	
	public ImageCanvas(Gui gui, Image img) {
		super(gui, img.getWidth(), img.getHeight());
		this.image = img;
	}
	
	public ImageCanvas(Gui gui, Image img, float scale) {
		super(gui, (int)((float)img.getWidth() * scale), (int)((float)img.getHeight() * scale));
		this.image = img;
	}
	
	public ImageCanvas(Gui gui, int width, int height, Image img) {
		super(gui, width, height);
		this.image = img;
	}
	
	public ImageCanvas(Gui gui, Box box, Image img) {
		super(gui, box);
		this.image = img;
	}

	public int getAlign() {
		return align;
	}

	public void setAlign(int align) {
		this.align = align;
	}

	protected void doRender() {
		GuiRenderer renderer = gui.getOwner().getGuiOwner().getRenderer();
		if(imageList == null) {
			imageList = renderer.getImageRenderList(image);
		}
		renderer.drawBox(imageList, box, this, style, align);
	}

	@Override
	public void onMouseEvent(MouseEvent event) {
	}

	@Override
	public void onKeyEvent(KeyEvent event) {
	}

	@Override
	public void delete() {
		if(imageList != null) imageList.delete();
	}

}
