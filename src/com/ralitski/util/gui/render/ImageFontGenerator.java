package com.ralitski.util.gui.render;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import com.ralitski.util.gui.Component;

public class ImageFontGenerator {
	
	public ImageFontGenerator() {
	}
	
	public Rectangle2D getBounds(String text, Component c, RenderStyle style) {
		BufferedImage base = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = base.createGraphics();
		getGraphics(c, style, g);
		Font font = g.getFont();
		FontRenderContext context = g.getFontRenderContext();
		TextLayout layout = new TextLayout(text, font, context);
		return layout.getPixelBounds(context, 0, 0);
	}
	
	public BufferedImage produceImage(String text, Component c, RenderStyle style) {
		Rectangle2D bounds = getBounds(text, c, style);
		BufferedImage image = new BufferedImage((int)Math.ceil(bounds.getWidth()), (int)Math.ceil(bounds.getHeight()), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		
		BufferedImage base = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = base.createGraphics();
		getGraphics(c, style, g2);
		set(g2, g);
		g.drawString(text, -(int)bounds.getX(), -(int)bounds.getY());
		
		return image;
	}
	
	private void set(Graphics2D src, Graphics2D dst) {
		dst.setColor(src.getColor());
		dst.setPaint(src.getPaint());
		dst.setFont(src.getFont());
		dst.setComposite(src.getComposite());
		dst.setBackground(src.getBackground()); //doesn't seem to do anything...
		dst.setRenderingHints(src.getRenderingHints());
	}
	
	private void getGraphics(Component c, RenderStyle style, Graphics2D g) {
		Object color = style.getStyle(c, "font-color");
		Color jColor = color != null ? (color instanceof Color ? (Color)color : ((com.ralitski.util.render.img.Color)color).jColor()) : Color.BLACK;
		g.setColor(jColor);
		g.setPaint(jColor);
		
		String font = style.getStyle(c, "font");
		Integer size = style.getStyle(c, "font-size");
		g.setFont(new Font(font != null ? font : "Arial", Font.PLAIN, size != null ? size : 20));
		
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		g.setBackground(Color.WHITE);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}
}
