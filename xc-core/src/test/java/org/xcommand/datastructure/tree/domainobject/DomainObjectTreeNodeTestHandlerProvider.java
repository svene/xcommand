package org.xcommand.datastructure.tree.domainobject;

import org.xcommand.core.ICommand;
import org.xcommand.datastructure.tree.MapBasedHandlerProvider;
import org.xcommand.datastructure.tree.domainobject.domain.AnotherDomainObject;
import org.xcommand.datastructure.tree.domainobject.domain.OneDomainObject;
import org.xcommand.datastructure.tree.domainobject.domain.RootDomainObject;

import java.util.HashMap;
import java.util.Map;

public class DomainObjectTreeNodeTestHandlerProvider extends MapBasedHandlerProvider {

	public DomainObjectTreeNodeTestHandlerProvider(ICommand aRootDomainObjectHandler, ICommand aOneDomainObjectHandler,
		ICommand aAnotherDomainObjectHandler) {

		Map map = new HashMap();
		map.put(RootDomainObject.class, aRootDomainObjectHandler);
		map.put(OneDomainObject.class, aOneDomainObjectHandler);
		map.put(AnotherDomainObject.class, aAnotherDomainObjectHandler);
		setHandlerMap(map);
	}
}
