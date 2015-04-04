package com.ralitski.util.gui.render;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import com.ralitski.util.gui.Box;
import com.ralitski.util.gui.Component;
import com.ralitski.util.gui.Dimension;
import com.ralitski.util.gui.GuiOwner;
import com.ralitski.util.gui.render.FontRenderer;
import com.ralitski.util.gui.render.RenderStyle;
import com.ralitski.util.render.img.Image;

//TODO: use text baseline to not move text up/down when there is a y or g (character going below bottom line)
public class ImageFontRenderer implements FontRenderer {
	
	private GuiOwner owner;
	private ImageFontGenerator generator;
	
	public ImageFontRenderer(GuiOwner owner) {
		this.owner = owner;
		generator = new ImageFontGenerator();
	}

	@Override
	public void renderLine(String line, Box bounds, Component c, RenderStyle style, int align) {
		BufferedImage image = generator.produceImage(line);
		boolean resize = false;
		int cutLeft = 0;
		int cutRight = 0;
		if(image.getWidth() > bounds.getWidth()) {
			resize = true;
			int cut = image.getWidth() - bounds.getWidth();
			switch(align & 7 /*0b00000111*/) {
			case WIDTH_ALIGN_RIGHT:
				cutLeft = cut;
				break;
			case WIDTH_ALIGN_CENTER:
				cut /= 2;
				cutLeft = cutRight = cut;
				break;
			default: //align left
				cutRight = cut;
			}
		}
		int cutBottom = 0;
		int cutTop = 0;
		if(image.getHeight() > bounds.getHeight()) {
			resize = true;
			int cut = image.getHeight() - bounds.getHeight();
			switch(align & 56 /*0b00111000*/) {
			case HEIGHT_ALIGN_TOP:
				cutBottom = cut;
				break;
			case HEIGHT_ALIGN_CENTER:
				cut /= 2;
				cutBottom = cutTop = cut;
				break;
			default: //align bottom
				cutTop = cut;
			}
		}
		if(resize) {
			//cut image
			image = image.getSubimage(cutLeft, cutBottom, image.getWidth() - cutLeft - cutRight, image.getHeight() - cutTop - cutBottom);
		}
		int x = 0;
		int y = 0;
		switch(align & 7) {
		case WIDTH_ALIGN_RIGHT:
			x = bounds.getMaxX() - image.getWidth();
			break;
		case WIDTH_ALIGN_CENTER:
			x = bounds.getMinX() + ((bounds.getWidth() - image.getWidth()) / 2);
			break;
		default: //align left
			x = bounds.getMinX();
		}
		switch(align & 56) {
		case HEIGHT_ALIGN_TOP:
			y = bounds.getMaxY() - image.getHeight();
			break;
		case HEIGHT_ALIGN_CENTER:
			y = bounds.getMinY() + ((bounds.getHeight() - image.getHeight()) / 2);
			break;
		default: //align bottom
			y = bounds.getMinY();
		}
		Box box = new Box(x, y, x + image.getWidth(), y + image.getHeight());
		owner.drawImage(new Image(image), box, c, style);
	}

	@Override
	public Box getBounds(String line, Box bounds, Component c, RenderStyle style, int align) {
		Rectangle2D rect = generator.getBounds(line);
		int width = (int)Math.ceil(rect.getWidth());
		int height = (int)Math.ceil(rect.getHeight());
		switch(align) {
		case WIDTH_ALIGN_RIGHT:
			return new Box(bounds.getMaxX() - width, bounds.getMinY(), bounds.getMaxX(), bounds.getMinY() + height);
		case WIDTH_ALIGN_CENTER:
			int width2 = width / 2;
			if(width % 2 != 0) width2++;
			return new Box(bounds.getCenterX() - width2, bounds.getMinY(), bounds.getCenterX() + width2, bounds.getMinY() + height);
		default: //align left
			return new Box(bounds.getMinX(), bounds.getMinY(), bounds.getMinX() + height, bounds.getMinY() + height);
		}
	}

	@Override
	public Dimension getDimensions(String line, Component c, RenderStyle style) {
		Rectangle2D rect = generator.getBounds(line);
		return new Dimension(rect);
	}

}
