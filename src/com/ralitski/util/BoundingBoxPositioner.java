package com.ralitski.util;

import org.lwjgl.opengl.Display;

import com.ralitski.util.doc.TODO;
import com.ralitski.util.math.geom.d2.Point2d;

public class BoundingBoxPositioner {
	
	public static void exclude(BoundingBox relative, BoundingBox boundingBox) {
		exclude(relative, boundingBox, 0);
	}
	
	public static void exclude(BoundingBox relative, BoundingBox boundingBox, float boundarySpace) {
		exclude(relative, boundingBox, boundingBox.getCenter(), boundarySpace);
	}
	
	public static void exclude(BoundingBox relative, BoundingBox boundingBox, Point2d prevPosition) {
		exclude(relative, boundingBox, prevPosition, 0);
	}
	
	public static void exclude(BoundingBox relative, BoundingBox boundingBox, Point2d prevPosition, float boundarySpace) {
		//TODO...
	}
	
	public static void position(GuiPosition[] positions, BoundingBox relative, BoundingBox boundingBox) {
		position(positions, relative, boundingBox, Display.getWidth(), Display.getHeight());
	}
	
	public static void position(GuiPosition[] positions, BoundingBox relative, BoundingBox boundingBox, float boundarySpace) {
		position(positions, relative, boundingBox, boundarySpace, Display.getWidth(), Display.getHeight());
	}
	
	public static void position(GuiPosition[] positions, BoundingBox relative, BoundingBox boundingBox, int w, int h) {
		position(positions, relative, boundingBox, 0, w, h);
	}
	
	public static void position(GuiPosition[] positions, BoundingBox relative, BoundingBox boundingBox, float boundarySpace, int w, int h) {
		for(GuiPosition position : positions) {
			if(relative != null || !position.isRelative())
				position.position(boundingBox, relative, boundarySpace, w, h);
		}
	}
	
	@TODO("account for different portions of the window (not just (0, 0) to (w, h) but also (x, y) to (w, h))")
	public static enum GuiPosition {
		CENTER(false) {
			@Override
			public void position(BoundingBox toPosition, BoundingBox relative, float space, int w, int h) {
				float xOffset = ((float)w - toPosition.getWidth()) / 2F;
				float yOffset = ((float)h - toPosition.getHeight()) / 2F;
				Point2d p = toPosition.getCenter();
				p.setX(xOffset);
				p.setY(yOffset);
			}
		},
		CENTER_WIDE(false) {
			@Override
			public void position(BoundingBox toPosition, BoundingBox relative, float space, int w, int h) {
				float xOffset = ((float)w - toPosition.getWidth()) / 2F;
				Point2d p = toPosition.getCenter();
				p.setX(xOffset);
			}
		},
		CENTER_HIGH(false) {
			@Override
			public void position(BoundingBox toPosition, BoundingBox relative, float space, int w, int h) {
				float yOffset = ((float)h - toPosition.getHeight()) / 2F;
				Point2d p = toPosition.getCenter();
				p.setY(yOffset);
			}
		},
		LEFT(false) {
			@Override
			public void position(BoundingBox toPosition, BoundingBox relative, float space, int w, int h) {
				Point2d p = toPosition.getCenter();
				p.addX(space - toPosition.getMinX());
			}
		},
		RIGHT(false) {
			@Override
			public void position(BoundingBox toPosition, BoundingBox relative, float space, int w, int h) {
				Point2d p = toPosition.getCenter();
				p.addX(w - toPosition.getMaxX() - space);
			}
		},
		TOP(false) {
			@Override
			public void position(BoundingBox toPosition, BoundingBox relative, float space, int w, int h) {
				Point2d p = toPosition.getCenter();
				p.addY(h - toPosition.getMaxY() - space);
			}
		},
		BOTTOM(false) {
			@Override
			public void position(BoundingBox toPosition, BoundingBox relative, float space, int w, int h) {
				Point2d p = toPosition.getCenter();
				p.addY(space - toPosition.getMinY());
			}
		},
		ABOVE(true) {
			@Override
			public void position(BoundingBox toPosition, BoundingBox relative, float space, int w, int h) {
				Point2d p = toPosition.getCenter();
				float pos = relative.getMaxY() + space - toPosition.getMinY();
				p.setY(Math.max(p.getY(), pos));
			}
		},
		ABOVE_STRICT(true) {
			@Override
			public void position(BoundingBox toPosition, BoundingBox relative, float space, int w, int h) {
				Point2d p = toPosition.getCenter();
				float pos = relative.getMaxY() + space - toPosition.getMinY();
				p.setY(pos);
			}
		},
		LEFT_OF(true) {
			@Override
                        @TODO
			public void position(BoundingBox toPosition, BoundingBox relative, float space, int w, int h) {
				Point2d p = toPosition.getCenter();
				float pos = relative.getMinX() - space - toPosition.getMaxX();
				p.setX(Math.min(p.getX(), pos));
			}
		},
		LEFT_OF_STRICT(true) {
			@Override
                        @TODO
			public void position(BoundingBox toPosition, BoundingBox relative, float space, int w, int h) {
				Point2d p = toPosition.getCenter();
				float pos = relative.getMinX() - space - toPosition.getMaxX();
				p.setX(pos);
			}
		},
		RIGHT_OF(true) {
			@Override
                        @TODO
			public void position(BoundingBox toPosition, BoundingBox relative, float space, int w, int h) {
				Point2d p = toPosition.getCenter();
				float pos = relative.getMaxX() + space - toPosition.getMinX();
				p.setX(Math.max(p.getX(), pos));
			}
		},
		RIGHT_OF_STRICT(true) {
			@Override
                        @TODO
			public void position(BoundingBox toPosition, BoundingBox relative, float space, int w, int h) {
				Point2d p = toPosition.getCenter();
				float pos = relative.getMaxX() + space - toPosition.getMinX();
				p.setX(pos);
			}
		},
		BELOW(true) {
			@Override
			public void position(BoundingBox toPosition, BoundingBox relative, float space, int w, int h) {
				Point2d p = toPosition.getCenter();
				float pos = relative.getMinY() - space - toPosition.getMaxY();
				p.setY(Math.min(p.getY(), pos));
			}
		},
		BELOW_STRICT(true) {
			@Override
			public void position(BoundingBox toPosition, BoundingBox relative, float space, int w, int h) {
				Point2d p = toPosition.getCenter();
				float pos = relative.getMinY() - space - toPosition.getMaxY();
				p.setY(pos);
			}
		};
		
		private boolean relative;
		
		GuiPosition(boolean relative) {
			this.relative = relative;
		}

		public boolean isRelative() {
			return relative;
		}
		
		public abstract void position(BoundingBox toPosition, BoundingBox relative, float space, int w, int h);
	}
}
