package org.collage.dom.evaluator.common;

import org.xcommand.misc.MessageCommandCV;
import org.collage.dom.evaluator.common.IStringHandler;

import java.util.Map;
import java.util.List;

public class ListAddingStringHandler implements IStringHandler
{

	public void handleString(Map aCtx, String aString)
	{
		List lst = MessageCommandCV.getList(aCtx);
		lst.add(aString);
	}
}
