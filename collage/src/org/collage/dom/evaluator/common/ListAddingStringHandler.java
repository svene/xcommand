package org.collage.dom.evaluator.common;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.misc.IMessageCommandCV;

import java.util.List;
import java.util.Map;

public class ListAddingStringHandler implements IStringHandler
{

	public void handleString(Map aCtx, String aString)
	{
		List lst = messageCommandCV.getList();
		lst.add(aString);
	}
	private DynaBeanProvider dbp = new DynaBeanProvider();
	IMessageCommandCV messageCommandCV = (IMessageCommandCV) dbp.getBeanForInterface(IMessageCommandCV.class);
}
