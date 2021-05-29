package org.xcommand.technology.janino;

import org.codehaus.janino.util.resource.Resource;
import org.codehaus.janino.util.resource.ResourceFinder;

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

	public final Resource findResource(final String resourceName)
	{
		int p = resourceName.indexOf(".java");
		final String s = resourceName.substring(0, p);
		final byte[] ba = (byte[]) this.map.get(s);
		if (ba == null) return null;

		return new Resource()
		{
			public InputStream open() throws IOException
			{
				return new ByteArrayInputStream(ba);
			}

			public String getFileName()
			{
				return s;
			}

			public long lastModified()
			{
				return XCMapResourceFinder.this.lastModified;
			}
		};
	}
}
