package org.xcommand.util;

import java.io.*;

public class ResourceUtil {
	private ResourceUtil() {
	}

	public static InputStream newInputStreamFromResourceLocation(String aResourceLocation) {
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(aResourceLocation);
		if (is == null) {
			throw new RuntimeException("could not load resource '%s' via context classloader".formatted(aResourceLocation));
		}
		return is;
	}

	public static InputStream newInputStreamFromFilename(String aFileName) throws FileNotFoundException {
		File file = new File(aFileName);

		if (!file.exists()) {
			throw new RuntimeException("file not found: '%s' (workingdir: '%s')".formatted(aFileName, new File(".").getAbsolutePath()));
		}
		return new BufferedInputStream(new FileInputStream(file));
	}
}
