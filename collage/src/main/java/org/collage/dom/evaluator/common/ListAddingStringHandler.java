package org.collage.dom.evaluator.common;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.misc.IMessageCommandCV;

import java.util.List;
import java.util.Map;

public class ListAddingStringHandler implements IStringHandler
{

	public void handleString(Map<String, Object> aCtx, String aString)
	{
		List<String> lst = messageCommandCV.getList();
		lst.add(aString);
	}
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	IMessageCommandCV messageCommandCV = dbp.newBeanForInterface(IMessageCommandCV.class);
}
