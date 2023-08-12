package org.collage;

import org.xcommand.util.ResourceUtil;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class TestUtil {
	private TestUtil() {
	}

	public static String fileContent(String aFilename) throws java.io.IOException {
		InputStream fis = ResourceUtil.newInputStreamFromResourceLocation(aFilename);
		return new String(fis.readAllBytes(), StandardCharsets.UTF_8);
	}

}
