package com.ralitski.util.math.var;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


/**
 * utility class for getting variables by name. is it necessary? idk.
 * 
 * @author ralitski
 */
public class VariableSet implements Cloneable {
    
    public static final VariableSet EMPTY_SET = new VariableSet() {

        public VariableSet add(String varName, Variable var) {
            return this;
        }
    };
    
    private Map<String, Variable> vars = new HashMap<String, Variable>();
    
    public VariableSet() {
    }
    
    public VariableSet add(String varName, float var) {
    	return add(varName, new VariableFixed(var));
    }
    
    public VariableSet add(String varName, Variable var) {
        vars.put(varName, var);
        return this;
    }
    
    public Set<String> get() {
        return vars.keySet();
    }
    
    public Set<Entry<String, Variable>> getAll() {
        return vars.entrySet();
    }
    
    public Variable get(String name) {
        return vars.get(name);
    }
    
    @Override
    public VariableSet clone() {
        try {
            return (VariableSet)super.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }
}
