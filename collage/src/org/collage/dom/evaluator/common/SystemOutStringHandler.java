package org.collage.dom.evaluator.common;

import org.collage.dom.evaluator.common.IStringHandler;

import java.util.Map;

public class SystemOutStringHandler implements IStringHandler
{

	public void handleString(Map aCtx, String aString)
	{
		System.out.println(aString);
	}
}
