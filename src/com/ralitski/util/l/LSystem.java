package com.ralitski.util.l;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//TODO: pen, command system, separate Rules structure
public class LSystem {
	
	private String axiom;
	private List<Rules> rules;
	private float grow;
	
	private String state;
	private int iterations;
	
	public LSystem() {
		this("");
	}
	
	public LSystem(float grow) {
		this("", grow);
	}
	
	public LSystem(String axiom) {
		this(axiom, 1.5F);
	}
	
	public LSystem(String axiom, float grow) {
		this.axiom = axiom;
		this.state = axiom;
		this.rules = new LinkedList<Rules>();
		this.grow = grow;
	}
	
	public String getAxiom() {
		return axiom;
	}
	
	//note: doesn't automatically reset state
	public void setAxiom(String axiom) {
		this.axiom = axiom;
	}
	
	public String getState() {
		return state;
	}
	
	public int getIterations() {
		return iterations;
	}
	
	public void reset() {
		state = axiom;
		iterations = 0;
	}
	
	public void addRule(String pre, String replace) {
		Rules rules = getOrCreate(pre.length());
		rules.rules.put(pre, replace);
	}
	
	private Rules getOrCreate(int size) {
		Rules r = get(size);
		if(r == null) {
			rules.add(r = new Rules());
			r.length = size;
			Collections.sort(rules);
		}
		return r;
	}
	
	private Rules get(int size) {
		for(Rules r : rules) {
			if(r.length == size) return r;
		}
		return null;
	}
	
	public String getRule(String pre) {
		Rules r = get(pre.length());
		return r != null ? r.rules.get(pre) : null;
	}
	
	public void iterate() {
		StringInputStream in = new StringInputStream(state);
		StringOutputStream out = new StringOutputStream((int)((float)state.length() * grow));
		int avail;
		while((avail = in.available()) > 0) {
			//go through rules in order of length
			boolean complete = false; //if the section has been handled
			for(Rules r : rules) {
				if(avail >= r.length) {
					String s = in.read(r.length);
					String replace = r.rules.get(s);
					if(replace != null) {
						out.write(replace);
						complete = true;
						break;
					} else {
						in.back(r.length);
					}
				}
			}
			//go forward once and start over
			if(!complete) out.write(in.read());
		}
		state = out.toString();
		iterations++;
	}
	
	private class Rules implements Comparable<Rules> {
		
		private int length;
		private Map<String, String> rules = new HashMap<String, String>();

		@Override
		public int compareTo(Rules r) {
			//reverse order (biggest to smallest)
			return r.length - length;
		}
		
	}
}
