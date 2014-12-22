package com.ralitski.util;

public interface Cancellable {
	boolean isCancelled();
	void setCancelled(boolean cancelled);
}
