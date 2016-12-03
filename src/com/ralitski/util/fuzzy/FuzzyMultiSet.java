package com.ralitski.util.fuzzy;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.ralitski.util.math.geom.n.Vector;

public class FuzzyMultiSet<T> {
	
	private Map<FuzzySet<T>, Action> actions;
	
	public FuzzyMultiSet() {
		this.actions = new HashMap<>();
	}
	
	public void add(FuzzySet<T> set, Action action) {
		
	}
	
	public int size() {
		return actions.size();
	}
	
	public double getTotalMembership(T member) {
		double d = 0;
		for(FuzzySet<T> set : actions.keySet()) d += set.getMembership(member);
		return d;
	}
	
	public Vector getAction(T member) {
		Vector v = new Vector();
		
		double membership = 0;
		for(Entry<FuzzySet<T>, Action> entries : actions.entrySet()) {
			double d = entries.getKey().getMembership(member);
			Vector action = entries.getValue().getAction(d);
			membership += d;
			v.add(action);
		}
		//normalize the action
		v.multiply((float)(1.0/membership)); //TODO: make better vectors with doubles...
		
		return v;
	}
}
