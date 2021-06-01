package org.xcommand.technology.janino;

import org.codehaus.commons.compiler.util.resource.Resource;
import org.codehaus.commons.compiler.util.resource.ResourceFinder;

import java.util.Map;
import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;

/**
 * Janino-ResourceFinder implementation providing the base for in-memory Java compilation.
 * See {@ link http://www.janino.net/ } for details.
 */
public class XCMapResourceFinder extends ResourceFinder
{
	private final Map map;
	private long lastModified = 0L;

	public XCMapResourceFinder(Map map)
	{
		this.map = map;
	}

	public void setLastModified(long lastModified)
	{
		this.lastModified = lastModified;
	}

	public final Resource findResource(String resourceName)
	{
		int p = resourceName.indexOf(".java");
		String s = resourceName.substring(0, p);
		byte[] ba = (byte[]) map.get(s);
		if (ba == null) {
			return null;
		}

		return new Resource()
		{
			public InputStream open() {
				return new ByteArrayInputStream(ba);
			}

			public String getFileName()
			{
				return s;
			}

			public long lastModified()
			{
				return lastModified;
			}
		};
	}
}
