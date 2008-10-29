package org.collage.dom.evaluator.common;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.ClassAndMethodKeyProvider;
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
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	IMessageCommandCV messageCommandCV = (IMessageCommandCV) dbp.newBeanForInterface(IMessageCommandCV.class);
}
