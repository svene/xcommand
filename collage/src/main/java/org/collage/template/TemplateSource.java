package org.collage.template;

import org.xcommand.core.TCP;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.Map;

public class TemplateSource
{

// --- Initialization ---

	public TemplateSource(InputStream aTemplateStream)
	{
		if (aTemplateStream == null) {
			throw new RuntimeException("aTemplateStream == null");
		}

		inputStream = aTemplateStream;
	}
	public TemplateSource(String aTemplateText)
	{
		this(new ByteArrayInputStream(aTemplateText.getBytes()));
	}

// --- Access ---

	public InputStream getInputStream()
	{
		return inputStream;
	}

	public Map<String, Object> getContext()
	{
		return TCP.getContext();
	}

// --- Setting ---

	public void setInputStream(InputStream aInputStream)
	{
		inputStream = aInputStream;
	}

// --- Implementation ---

	private InputStream inputStream;
}
