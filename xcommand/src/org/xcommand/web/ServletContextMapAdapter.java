package org.xcommand.web;

import javax.servlet.ServletContext;
import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.Enumeration;

/** Adapter from a `ServletContext' to a `java.util.Map'.
 *  Instances of this class thus provide the possibility to access a servletcontext's attributes
 *  via the `java.util.Map' interface. 
 */

public class ServletContextMapAdapter implements Map
{

// --- Initialization ---

	public ServletContextMapAdapter(ServletContext aServletContext)
	{
		servletContext = aServletContext;
	}

// --- java.util.Map routines ---

	public int size()
	{
		return _size();
	}

	public boolean isEmpty()
	{
		return _size() == 0;
	}

	/**
	 * `key' must be of type String
	 */
	public boolean containsKey(Object key)
	{
		return servletContext.getAttribute((String) key) != null;
	}

	public boolean containsValue(Object value)
	{
		Enumeration enumeration = servletContext.getAttributeNames();
		while (enumeration.hasMoreElements())
		{
			String name = (String) enumeration.nextElement();
			if (value.equals(servletContext.getAttribute(name)))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * `key' must be of type String
	 */
	public Object get(Object key)
	{
		return servletContext.getAttribute((String) key);
	}

	/**
	 * `key' must be of type String
	 */
	public Object put(Object key, Object value)
	{
		servletContext.setAttribute((String) key, value);
		return value;
	}

	/**
	 * `key' must be of type String
	 */
	public Object remove(Object key)
	{
		Object result = servletContext.getAttribute((String) key);
		servletContext.removeAttribute((String) key);
		return result;
	}

	public void putAll(Map m)
	{
	}

	public void clear()
	{
	}

	public Set keySet()
	{
		return null;
	}

	public Collection values()
	{
		return null;
	}

	public Set entrySet()
	{
		return null;
	}

// --- Implementation ---

	private int _size()
	{
		int result = 0;
		Enumeration enumeration = servletContext.getAttributeNames();
		while (enumeration.hasMoreElements())
		{
			result++;
			enumeration.nextElement();
		}
		return result;
	}

	ServletContext servletContext;
}
