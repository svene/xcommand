package org.collage.template;

import org.xcommand.core.TCP;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.Map;

public class TemplateSource {

	public TemplateSource(InputStream aTemplateStream) {
		if (aTemplateStream == null) {
			throw new RuntimeException("aTemplateStream == null");
		}

		inputStream = aTemplateStream;
	}

	public TemplateSource(String aTemplateText) {
		this(new ByteArrayInputStream(aTemplateText.getBytes()));
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public Map<String, Object> getContext() {
		return TCP.getContext();
	}

	public void setInputStream(InputStream aInputStream) {
		inputStream = aInputStream;
	}

	private InputStream inputStream;
}
