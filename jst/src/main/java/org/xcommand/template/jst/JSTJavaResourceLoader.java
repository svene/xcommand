package org.xcommand.template.jst;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.jooq.lambda.Sneaky;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;

@Slf4j
public class JSTJavaResourceLoader {

    public Map<String, byte[]> getClassMap() {
        return classMap;
    }

    /**
     * Load source of template for `aClassname' specified in full qualified
     * java package notation (as 'java.lang.String')
     */
    public void load(String resourceName) {
        log.info("resourceName: " + resourceName);

        var is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName);
        if (is == null) {
            throw new RuntimeException("resource '%s' not found".formatted(resourceName));
        }

        jstParserCV.setInputStream(is);
        var parser = new DefaultJSTParserProvider().newJSTParser();
        jstParserCV.setGeneratedJavaCode(new StringBuffer());
        Sneaky.runnable(parser::Start).run();
        var s = jstParserCV.getGeneratedJavaCode().toString();

        classMap = new HashMap<>();
        classMap.put(resourceName.replace(".java", ""), s.getBytes(StandardCharsets.UTF_8));
    }

    private Map<String, byte[]> classMap = new HashMap<>();

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final IJSTParserCV jstParserCV = dbp.newBeanForInterface(IJSTParserCV.class);
}
