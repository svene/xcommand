package org.xcommand.util;

import java.io.*;

public class ResourceUtil {
	private ResourceUtil() {
	}

	public static InputStream newInputStreamFromResourceLocation(String aResourceLocation) {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(aResourceLocation);
		if (is == null) throw new RuntimeException(String.format("could not load resource '%s' via context classloader", aResourceLocation));
		return is;
	}

	public static InputStream newInputStreamFromFilename(String aFileName) throws FileNotFoundException {
		final File file = new File(aFileName);

		if (!file.exists()) {
			throw new RuntimeException(String.format("file not found: '%s' (workingdir: '%s')", aFileName, new File(".").getAbsolutePath()));
		}
		return new BufferedInputStream(new FileInputStream(file));
	}
}
