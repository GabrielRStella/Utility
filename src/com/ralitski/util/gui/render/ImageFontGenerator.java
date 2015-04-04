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

public class ImageFontGenerator {
	
	Graphics2D g;
	
	public ImageFontGenerator() {
		BufferedImage base = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		g = base.createGraphics();
		g.setColor(Color.BLACK);
		g.setPaint(Color.BLACK);
		g.setFont(new Font("Arial", Font.PLAIN, 20));
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
		g.setBackground(Color.WHITE);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}
	
	public Rectangle2D getBounds(String text) {
		Font font = g.getFont();
		FontRenderContext c = g.getFontRenderContext();
		TextLayout layout = new TextLayout(text, font, c);
		return layout.getPixelBounds(c, 0, 0);
	}
	
	public BufferedImage produceImage(String text) {
		Rectangle2D bounds = getBounds(text);
		BufferedImage image = new BufferedImage((int)Math.ceil(bounds.getWidth()), (int)Math.ceil(bounds.getHeight()), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		set(g);
		g.drawString(text, -(int)bounds.getX(), -(int)bounds.getY());
		
		return image;
	}
	
	private void set(Graphics2D g2) {
		g2.setColor(g.getColor());
		g2.setPaint(g.getPaint());
		g2.setFont(g.getFont());
		g2.setComposite(g.getComposite());
		g2.setBackground(g.getBackground()); //doesn't seem to do anything...
		g2.setRenderingHints(g.getRenderingHints());
	}
}
