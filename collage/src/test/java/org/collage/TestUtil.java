package org.collage;

import org.apache.commons.io.IOUtils;
import org.xcommand.util.ResourceUtil;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class TestUtil {
	private TestUtil() {
	}

	public static String fileContent(String aFilename) throws Exception
	{
		InputStream fis = ResourceUtil.newInputStreamFromResourceLocation(aFilename);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		IOUtils.copy(fis, bos);
		return bos.toString();
	}

}
