package com.ralitski.util.gui;

import com.ralitski.util.doc.TODO;

public enum BoxPosition {
	CENTER(false) {
		@Override
		public void position(Box toPosition, Box relative, int space, Box window) {
			int x = (window.getWidth() - toPosition.getWidth()) / 2;
			int y = (window.getHeight() - toPosition.getHeight()) / 2;
			toPosition.setCenter(x + window.getMinX(), y + window.getMinY());
		}
	},
	CENTER_WIDE(false) {
		@Override
		public void position(Box toPosition, Box relative, int space, Box window) {
			int x = (window.getWidth() - toPosition.getWidth()) / 2;
			toPosition.setCenterX(x + window.getMinX());
		}
	},
	CENTER_HIGH(false) {
		@Override
		public void position(Box toPosition, Box relative, int space, Box window) {
			int y = (window.getHeight() - toPosition.getHeight()) / 2;
			toPosition.setCenterY(y + window.getMinY());
		}
	},
	LEFT(false) {
		@Override
		public void position(Box toPosition, Box relative, int space, Box window) {
			toPosition.translateX(space - toPosition.getMinX() + window.getMinX());
		}
	},
	RIGHT(false) {
		@Override
		public void position(Box toPosition, Box relative, int space, Box window) {
			toPosition.translateX(window.getMaxX() - toPosition.getMaxX() - space);
		}
	},
	TOP(false) {
		@Override
		public void position(Box toPosition, Box relative, int space, Box window) {
			toPosition.translateY(window.getMaxY() - toPosition.getMaxY() - space);
		}
	},
	BOTTOM(false) {
		@Override
		public void position(Box toPosition, Box relative, int space, Box window) {
			toPosition.translateY(space - toPosition.getMinY() + window.getMinY());
		}
	},
	ABOVE(true) {
		@Override
		public void position(Box toPosition, Box relative, int space, Box window) {
			int pos = relative.getMaxY() + space - toPosition.getMinY();
			toPosition.setCenterY(Math.max(toPosition.getCenterY(), pos));
		}
	},
	ABOVE_STRICT(true) {
		@Override
		public void position(Box toPosition, Box relative, int space, Box window) {
			int pos = relative.getMaxY() + space - toPosition.getMinY();
			toPosition.setCenterY(pos);
		}
	},
	LEFT_OF(true) {
		@Override
                    @TODO
		public void position(Box toPosition, Box relative, int space, Box window) {
			int pos = relative.getMinX() - space - toPosition.getMaxX();
			toPosition.setCenterX(Math.min(toPosition.getCenterX(), pos));
		}
	},
	LEFT_OF_STRICT(true) {
		@Override
                    @TODO
		public void position(Box toPosition, Box relative, int space, Box window) {
			int pos = relative.getMinX() - space - toPosition.getMaxX();
			toPosition.setCenterX(pos);
		}
	},
	RIGHT_OF(true) {
		@Override
                    @TODO
		public void position(Box toPosition, Box relative, int space, Box window) {
			int pos = relative.getMaxX() + space - toPosition.getMinX();
			toPosition.setCenterX(Math.max(toPosition.getCenterX(), pos));
		}
	},
	RIGHT_OF_STRICT(true) {
		@Override
                    @TODO
		public void position(Box toPosition, Box relative, int space, Box window) {
			int pos = relative.getMaxX() + space - toPosition.getMinX();
			toPosition.setCenterX(pos);
		}
	},
	BELOW(true) {
		@Override
		public void position(Box toPosition, Box relative, int space, Box window) {
			int pos = relative.getMinY() - space - toPosition.getMaxY();
			toPosition.setCenterY(Math.min(toPosition.getCenterY(), pos));
		}
	},
	BELOW_STRICT(true) {
		@Override
		public void position(Box toPosition, Box relative, int space, Box window) {
			int pos = relative.getMinY() - space - toPosition.getMaxY();
			toPosition.setCenterY(pos);
		}
	},
	WITHIN(false) {
		@Override
		public void position(Box toPosition, Box relative, int space, Box window) {
			if(toPosition.getMinX() < window.getMinX() + space) {
				toPosition.translateX(window.getMinX() - toPosition.getMinX() + space);
			} else if(toPosition.getMaxX() > window.getMaxX() - space) {
				toPosition.translateX(window.getMaxX() - toPosition.getMaxX() - space);
			}
			if(toPosition.getMinY() < window.getMinY() + space) {
				toPosition.translateY(window.getMinY() - toPosition.getMinY() + space);
			} else if(toPosition.getMaxY() > window.getMaxY() - space) {
				toPosition.translateY(window.getMaxY() - toPosition.getMaxY() - space);
			}
		}
	},
	WITHIN_STRICT(false) {
		@Override
		public void position(Box toPosition, Box relative, int space, Box window) {
			//x
			if(toPosition.getWidth() > window.getWidth()) {
				toPosition.setMinX(window.getMinX());
				toPosition.setMaxX(window.getMaxX());
			} else if(toPosition.getMinX() < window.getMinX() + space) {
				toPosition.translateX(window.getMinX() - toPosition.getMinX() + space);
			} else if(toPosition.getMaxX() > window.getMaxX() - space) {
				toPosition.translateX(window.getMaxX() - toPosition.getMaxX() - space);
			}
			//y
			if(toPosition.getHeight() > window.getHeight()) {
				toPosition.setMinY(window.getMinY());
				toPosition.setMaxY(window.getMaxY());
			} else if(toPosition.getMinY() < window.getMinY() + space) {
				toPosition.translateY(window.getMinY() - toPosition.getMinY() + space);
			} else if(toPosition.getMaxY() > window.getMaxY() - space) {
				toPosition.translateY(window.getMaxY() - toPosition.getMaxY() - space);
			}
		}
	};
	
	private boolean relative;
	
	BoxPosition(boolean relative) {
		this.relative = relative;
	}

	public boolean isRelative() {
		return relative;
	}
	
	public abstract void position(Box toPosition, Box relative, int space, Box window);
	
	public static void position(Box relative, Box boundingBox, Box window, BoxPosition...positions) {
		position(relative, boundingBox, window, 0, positions);
	}
	
	public static void position(Box relative, Box boundingBox, Box window, int boundarySpace, BoxPosition...positions) {
		for(BoxPosition position : positions) {
			if(relative != null || !position.isRelative())
				position.position(boundingBox, relative, boundarySpace, window);
		}
	}
}
