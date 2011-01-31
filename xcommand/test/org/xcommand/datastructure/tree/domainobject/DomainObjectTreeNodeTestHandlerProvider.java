package org.xcommand.datastructure.tree.domainobject;

import org.xcommand.datastructure.tree.MapBasedHandlerProvider;
import org.xcommand.datastructure.tree.domainobject.domain.RootDomainObject;
import org.xcommand.datastructure.tree.domainobject.domain.OneDomainObject;
import org.xcommand.datastructure.tree.domainobject.domain.AnotherDomainObject;
import org.xcommand.misc.MessageCommand;

import java.util.Map;
import java.util.HashMap;

public class DomainObjectTreeNodeTestHandlerProvider extends MapBasedHandlerProvider
{
	{
		Map map = new HashMap();
		map.put(RootDomainObject.class, new RootDomainObjectHandler());
		map.put(OneDomainObject.class, new OneDomainObjectHandler());
		map.put(AnotherDomainObject.class, new AnotherDomainObjectHandler());
		setHandlerMap(map);
	}

	private static class RootDomainObjectHandler extends MessageCommand
	{
		public String getMessage()
		{
			return "handling RootDomainObject";
		}
	}
	private static class OneDomainObjectHandler extends MessageCommand
	{
		public String getMessage()
		{
			return "handling OneDomainObject";
		}
	}
	private static class AnotherDomainObjectHandler extends MessageCommand
	{
		public String getMessage()
		{
			return "handling AnotherDomainObject";
		}
	}
}
