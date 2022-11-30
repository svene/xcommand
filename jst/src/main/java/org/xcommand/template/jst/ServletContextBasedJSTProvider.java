package org.xcommand.template.jst;

import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;
import org.xcommand.web.IWebCV;

import jakarta.servlet.ServletContext;

import java.util.*;

public class ServletContextBasedJSTProvider implements IJSTProvider {

	public void initialize() {
		var sc = webCV.getServletContext();

		var set = sc.getResourcePaths("/");
		for (Object o : set) {
			System.out.println("o.class=" + o.getClass().getName() + ", value=" + o);
		}
	}

	@Override
	public ClassMapEntry getClassMapEntry(Map<String, Object> aCtx, String aClassname) {
		return null;
	}


	public void setSrcDirs(List<String> aSrcDirs) {
		srcDirs = aSrcDirs;
	}

	@Override
	public Map<String, ClassMapEntry> getClassMap() {
		return classMap;
	}

	@Override
	public INotifier getChangeNotifier() {
		return changeNotifier;
	}

	private List<String> srcDirs;
	private final Map<String, ClassMapEntry> classMap = new HashMap<>();
	private final INotifier changeNotifier = new BasicNotifier();
	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IWebCV webCV = dbp.newBeanForInterface(IWebCV.class);
}
