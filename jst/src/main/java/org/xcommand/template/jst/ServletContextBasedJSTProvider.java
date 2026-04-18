package org.xcommand.template.jst;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.pattern.observer.BasicNotifier;
import org.xcommand.pattern.observer.INotifier;
import org.xcommand.web.IWebCV;

public class ServletContextBasedJSTProvider implements IJSTProvider {

    private static final Logger log = LoggerFactory.getLogger(ServletContextBasedJSTProvider.class);

    public void initialize() {
        var sc = webCV.getServletContext();

        var set = sc.getResourcePaths("/");
        for (Object o : set) {
            log.debug("o.class={}, value={}", o.getClass().getName(), o);
        }
    }

    @Override
    public Optional<ClassMapEntry> getClassMapEntry(Map<String, Object> aCtx, String aClassname) {
        return Optional.empty();
    }

    @Override
    public Map<String, ClassMapEntry> getClassMap() {
        return classMap;
    }

    @Override
    public INotifier getChangeNotifier() {
        return changeNotifier;
    }

    private final Map<String, ClassMapEntry> classMap = new HashMap<>();
    private final INotifier changeNotifier = new BasicNotifier();
    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final IWebCV webCV = dbp.newBeanForInterface(IWebCV.class);
}
