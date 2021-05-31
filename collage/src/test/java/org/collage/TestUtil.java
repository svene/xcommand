package org.collage;

import org.xcommand.util.ResourceUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TestUtil {
	private TestUtil() {
	}

	public static String fileContent(String aFilename) throws Exception
	{
		InputStream fis = ResourceUtil.newInputStreamFromResourceLocation(aFilename);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		writeInputStreamToOutputStream(fis, bos);
		return bos.toString();
	}

	public static void writeInputStreamToOutputStream(InputStream a_is, OutputStream a_os)
		throws IOException
	{
		byte[] buffer = new byte[4096];
		int bytes_read;
		while ((bytes_read = a_is.read(buffer)) != -1)
		{
			a_os.write(buffer, 0, bytes_read);
		}
	}
}
