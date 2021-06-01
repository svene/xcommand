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

	@Override
	public int size()
	{
		return _size();
	}

	@Override
	public boolean isEmpty()
	{
		return _size() == 0;
	}

	/**
	 * `key' must be of type String
	 */
	@Override
	public boolean containsKey(Object key)
	{
		return servletContext.getAttribute((String) key) != null;
	}

	@Override
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
	@Override
	public Object get(Object key)
	{
		return servletContext.getAttribute((String) key);
	}

	/**
	 * `key' must be of type String
	 */
	@Override
	public Object put(Object key, Object value)
	{
		servletContext.setAttribute((String) key, value);
		return value;
	}

	/**
	 * `key' must be of type String
	 */
	@Override
	public Object remove(Object key)
	{
		Object result = servletContext.getAttribute((String) key);
		servletContext.removeAttribute((String) key);
		return result;
	}

	@Override
	public void putAll(Map m)
	{
	}

	@Override
	public void clear()
	{
	}

	@Override
	public Set keySet()
	{
		return null;
	}

	@Override
	public Collection values()
	{
		return null;
	}

	@Override
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

	final ServletContext servletContext;
}
