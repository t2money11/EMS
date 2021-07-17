package com.constant;

import java.io.Serializable;  
import java.util.AbstractList;  
import java.util.RandomAccess;  
  
public class NullCollection extends AbstractList<Object> implements RandomAccess, Serializable {  
  
	private static final long serialVersionUID = -7370570616727796191L;

	@Override  
    public Object get(int index) {  
        return null;  
    }  
  
    @Override  
    public int size() {  
        return 1;  
    }  
      
    public boolean contains(Object obj) {  
        return null == obj;  
    }  
      
    private Object readResolve() {  
        return null;  
    }
}

