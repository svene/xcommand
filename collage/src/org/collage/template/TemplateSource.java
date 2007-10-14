package org.collage.template;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.HashMap;

public class TemplateSource
{

// --- Initialization ---

	public TemplateSource(InputStream aTemplateStream, Map aCtx)
	{
		inputStream = aTemplateStream;
		context = aCtx;
	}

	public TemplateSource(InputStream aTemplateStream)
	{
		this(aTemplateStream, new HashMap());
	}
	public TemplateSource(String aTemplateText, Map aCtx)
	{
		this(new ByteArrayInputStream(aTemplateText.getBytes()), aCtx);
	}
	public TemplateSource(String aTemplateText)
	{
		this(aTemplateText, new HashMap());
	}

// --- Access ---

	public InputStream getInputStream()
	{
		return inputStream;
	}

	public Map getContext()
	{
		return context;
	}

// --- Setting ---

	public void setInputStream(InputStream aInputStream)
	{
		inputStream = aInputStream;
	}

	public void setContext(Map aCtx)
	{
		context = aCtx;
	}

// --- Implementation ---

	private InputStream inputStream;
	private Map context;
}
