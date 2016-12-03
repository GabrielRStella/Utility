package com.ralitski.util.fuzzy;

import com.ralitski.util.math.geom.n.Vector;

public class FuzzySetAction<T> {
	
	private FuzzySet<T> set;
	private Action action;
	
	public FuzzySetAction(FuzzySet<T> set, Action action) {
		this.set = set;
		this.action = action;
	}

	public FuzzySet<T> getSet() {
		return set;
	}

	public void setSet(FuzzySet<T> set) {
		this.set = set;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
	
	public Vector getAction(T t) {
		return action.getAction(set.getMembership(t));
	}
}
