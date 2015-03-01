package com.ralitski.util.l;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A pen to be used with L-System output. String commands of various lengths can be associated with Runnable objects. Each time a command is found in the state, the CommandPen will call run().
 * 
 * @author ralitski
 */
public class CommandPen implements Runnable {
	
	private String state;
	private List<Rules> rules;
	
	public CommandPen() {
		rules = new LinkedList<Rules>();
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getState() {
		return state;
	}

	@Override
	public void run() {
		StringInputStream in = new StringInputStream(state);
		int avail;
		while((avail = in.available()) > 0) {
			//go through rules in order of length
			boolean complete = false; //if the section has been handled
			for(Rules r : rules) {
				if(avail >= r.length) {
					String s = in.read(r.length);
					Runnable command = r.rules.get(s);
					if(command != null) {
						command.run();
						complete = true;
						break;
					} else {
						in.back(r.length);
					}
				}
			}
			//go forward once and start over
			if(!complete) in.read();
		}
	}
	
	public void addRule(String text, Runnable cmd) {
		Rules rules = getOrCreate(text.length());
		rules.rules.put(text, cmd);
	}
	
	public Runnable getRule(String text) {
		Rules r = get(text.length());
		return r != null ? r.rules.get(text) : null;
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
	
	private class Rules implements Comparable<Rules> {
		
		private int length;
		private Map<String, Runnable> rules = new HashMap<String, Runnable>();

		@Override
		public int compareTo(Rules r) {
			//reverse order (biggest to smallest)
			return r.length - length;
		}
		
	}
}
