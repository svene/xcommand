package org.xcommand.technology.janino;

import org.codehaus.commons.compiler.util.resource.Resource; // dependency to 'janino:commons-compiler'
import org.codehaus.commons.compiler.util.resource.ResourceFinder;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

/**
 * Janino-ResourceFinder implementation providing the base for in-memory Java compilation.
 * See {@ link http://www.janino.net/ } for details.
 */
public class XCMapResourceFinder extends ResourceFinder {
	private final Map<String, byte[]> map;
	private long lastModified;

	public XCMapResourceFinder(Map<String, byte[]> map) {
		this.map = map;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	@Nullable
	public final Resource findResource(String resourceName) {
		int p = resourceName.indexOf(".java");
		String s = resourceName.substring(0, p);
		byte[] ba = map.get(s);
		if (ba == null) {
			return null;
		}

		return new Resource() {
			@Override
			public InputStream open() {
				return new ByteArrayInputStream(ba);
			}

			@Override
			public String getFileName() {
				return s;
			}

			@Override
			public long lastModified() {
				return lastModified;
			}
		};
	}
}
