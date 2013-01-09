/*
 * Offered under Apache Public Licence 2.0
 * blessedgeek [@] synthful.org
 * www.synthful.org 2003
 * 
 */
package org.synthful.util;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import java.util.Map.Entry;

import org.synthful.lang.Empty.EmptyFactory;
import org.synthful.util.ToStringBuffer.ToStringBufferable;

public class HashVector<K,V>
extends Hashtable<K,V>
implements ToStringBufferable, EmptyFactory, Serializable
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new HashVector.
	 */
    public HashVector ()
    {
        super ();
    }
    
    /**
	 * Instantiates a new HashVector.
	 * 
	 * @param map
	 */
    public HashVector (Map<K, V> map)
    {
        add (map);
    }
    
    /**
	 * Instantiates a new HashVector.
	 * 
	 * @param t
	 */
    public HashVector (Object[][] t)
    {
        add (t);
    }
    
    /**
	 * Instantiates a new HashVector.
	 * 
	 * @param initSz
	 */
    public HashVector (int initSz)
    {
        super (initSz);
    }
    
    /**
	 * Instantiates a new HashVector.
	 * 
	 * @param initSz
	 * @param factor
	 */
    public HashVector (int initSz, float factor)
    {
        super (initSz, factor);
    }
    
    /**
	 * Adds the.
	 * 
	 * @param map
	 * 
	 * @return Adds the as HashVector
	 */
    public HashVector<K,V> add (Map<K,V> map)
    {
    	for(Entry<K, V> entry: map.entrySet())
    	{
    		V v = entry.getValue();
    		K k = entry.getKey();
    		super.put(k, v);
    		this.KeysVector.add(k);
    	}
        
        return this;
    }
    
    /**
	 * Adds the.
	 * 
	 * @param t
	 * 
	 * @return Adds the as HashVector
	 */
    public HashVector<K,V> add (Object[][] t)
    {
        if (t != null)
            for (int i = 0; i < t.length; i++)
            {
                if (t[i] == null || t[i].length < 1)
                    continue;
                Object key = t[i][0];
                Object value
                = t[i].length >= 2
                ? t[i][1]
                : null;
                
                if (key==null ||value==null)
                	continue;
                
                try{
                	this.put ((K)key, (V)value);
                }
                catch (ClassCastException ex)
                {
                	
                }
                catch (Exception ex)
                {
                	
                }
            }
        
        return this;
    }
    
    
    
    /**
	 * Gets the String.
	 * 
	 * @param key
	 * 
	 * @return the String as String
	 */
    public String getString (Object key)
    {
        Object o = get (key);
        if (o == null)
            return "";
        return o.toString ();
    }
    
    
    /**
	 * get stored object by position.
	 * 
	 * @param position
	 *            Position of stored object in the hash vector.
	 * 
	 * @return Object stored at that position.
	 */
    public V get (int position)
    {
        if (position < KeysVector.size () && position >= 0)
            return this.get (KeysVector.get (position));
        
        return null;
    }
    
    
    /**
	 * get hash key of stored object by position.
	 * 
	 * @param position
	 *            Position of stored object in the hash vector.
	 * 
	 * @return Hash key of object stored at that position.
	 */
    public K getKey (int position)
    {
        if (position < KeysVector.size () && position >= 0)
            return KeysVector.get (position);
        
        return null;
    }
    
    /**
	 * Gets the KeyPosition.
	 * 
	 * @param key
	 * 
	 * @return the KeyPosition as int
	 */
    public int getKeyPosition (K key)
    {
        for (int i = 0; i < KeysVector.size (); i++)
            if (key.equals (KeysVector.get (i)))
                return i;
        return -1;
    }
    
    /**
	 * Gets the KeysVector.
	 * 
	 * @return the KeysVector as Vector
	 */
    public Vector getKeysVector ()
    {
        return KeysVector;
    }
    
    public V put (K key, V value)
    {
        this.putValue(key, value);        
        return value;
    }
        
    /**
	 * Put value.
	 * 
	 * @param key
	 * @param value
	 * 
	 * @return Put value as HashVector
	 */
    protected HashVector<K,V> putValue (K key, V value)
    {
        if(key==null||value==null)
            return this;
        super.put (key, value);
        if(!KeysVector.contains(key))
            KeysVector.add (key);
        return this;
    }
    
    @Override
    public V remove(Object o)
    {
    	return this.removeValue((K) o);
    }
    
    public V removeValue(K key)
    {
    	try{
	    	V val = this.get(key);
	    	super.remove(key);
	    	this.KeysVector.remove(key);
	    	return val;
    	}
    	catch(Exception e)
    	{
    		return null;
    	}
    }
    
    public V remove(int i)
    {
    	K key = this.KeysVector.get(i);
    	return removeValue(key);
    }
    
    /**
     * @see java.util.Hashtable#clear()
     */
    public void clear ()
    {
        super.clear ();
        KeysVector.clear ();
    }
    
    /**
	 * Converts to Array.
	 * 
	 * @return Array as Object[]
	 */
    public V[] toValueArray ()
    {
        Class<V> componentType = null;
        V[] ar = (V[]) Array.newInstance(componentType, this.size ());
        
        for (int i = 0; i < size (); i++)
        {
            ar[i] = get (i);
        }
        
        return ar;
    }
    
    /**
	 * Converts to Array.
	 * 
	 * @return Array as Object[]
	 */
    public Object[][] toKeyValueArray ()
    {
        Class<V> componentType = null;
        Object[][] ar = new Object[this.size()][2];
        
        for (int i = 0; i < size (); i++)
        {
            ar[i][0] = this.getKey (i);
            ar[i][1] = this.get (i);
        }
        
        return ar;
    }

    /**
	 * Converts to Vector.
	 * 
	 * @param v
	 * 
	 * @return Vector 
	 */
    public Vector<V> toValueVector (Vector<V> v)
    {
        for (int i = 0; i < size (); i++)
        {
            v.add (get (i));
        }
        
        return v;
    }
    
    /**
	 * Converts to Vector.
	 * 
	 * @return Vector as Vector
	 */
    public Vector<V> toVector ()
    {
        return toValueVector (new Vector<V> (size ()));
    }
    
    public StringBuffer toStringBuffer (ToStringBuffer tostrbuf, int depth, int iteration)
    {
        //return tostrbuf.toStringBuffer (this, depth, iteration);
        StringBuffer indent = tostrbuf.mkIndentation(depth);
        StringBuffer strBuf = tostrbuf.getStringBuffer();
        
        Vector<K> kvec = this.KeysVector;
        
    	//tostrbuf.appendNotNull(indent);
    	//tostrbuf.appendNotNull(indent);
        
        tostrbuf.appendNotNull(tostrbuf.ItemTerminatorLeft);

    	for(int i=0; i<kvec.size(); i++)
    	{
    		Object key = kvec.get(i);
    		Object val = this.get(key);
            
            if (i>0)
            	tostrbuf.appendNotNull(tostrbuf.ItemDelimiter);
            
	        // iteration=0 because delimiter should not appear before separator
            tostrbuf.toStringBuffer(key, depth, 0);
            strBuf.append(tostrbuf.PairSeparator);
            tostrbuf.toStringBuffer(val, depth, i);
    	}
    	
    	tostrbuf.appendNotNull(tostrbuf.ItemTerminatorRight);
    	
        return strBuf;
    }

    /**
     * @see org.synthful.util.TreeNode#getNode(int)
     */

    /** Variable KeysVector. */
    protected Vector<K> KeysVector = new Vector<K> ();
    
}
