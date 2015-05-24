package com.ralitski.util.gui;

import com.ralitski.util.input.event.KeyEvent;
import com.ralitski.util.input.event.MouseEvent;
import com.ralitski.util.render.img.Image;

public class ImageCanvas extends ComponentAbstract {
	
	private Image image;
	
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

	protected void doRender() {
		gui.getOwner().getGuiOwner().drawImage(image, box, this, style);
	}

	@Override
	public void onMouseEvent(MouseEvent event) {
	}

	@Override
	public void onKeyEvent(KeyEvent event) {
	}

}
