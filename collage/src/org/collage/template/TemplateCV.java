package org.collage.template;

import java.util.Map;
import java.io.Writer;

public class TemplateCV
{

// --- Access ---

	public static Writer getWriter(Map aCtx)
	{
		return (Writer) aCtx.get(KEY_UF_WRITER);
	}

	public static void setWriter(Map aCtx, Writer aWriter)
	{
		aCtx.put(KEY_UF_WRITER, aWriter);
	}	

	private static final String NS = "org.collage.template.TemplateCV.";
	/* UF : user friendly */
	private static final String KEY_UF_WRITER = "writer";
}
