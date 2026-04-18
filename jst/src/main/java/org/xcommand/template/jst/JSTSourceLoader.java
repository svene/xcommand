package org.xcommand.template.jst;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.IDynaBeanProvider;

public class JSTSourceLoader {

    private static final Logger log = LoggerFactory.getLogger(JSTSourceLoader.class);

    public JSTSourceLoader(String srcDir) {
        this.srcDir = srcDir;
    }

    public String getSrcDir() {
        return srcDir;
    }

    public Map<String, byte[]> getClassMap() {
        return classMap;
    }

    /**
     * Load source of template for `aClassname' specified in full qualified
     * java package notation (as 'java.lang.String')
     */
    public void loadJavaFile(String aClassname) {
        try {
            //			TCP.pushContext(new HashMap());
            var filename = srcDir + "/" + aClassname.replaceAll("\\.", "/") + ".java";
            log.debug("filename = {}", filename);
            var is = new FileInputStream(filename);
            jstParserCV.setInputStream(is);
            var parser = new DefaultJSTParserProvider().newJSTParser();
            //		TCP.popContext();
            jstParserCV.setGeneratedJavaCode(new StringBuffer());
            parser.parse();
            var s = jstParserCV.getGeneratedJavaCode().toString();
            log.debug("{}", s);

            classMap = new HashMap<>();
            log.debug("aClassname = {}", aClassname);
            classMap.put(aClassname, s.getBytes(StandardCharsets.UTF_8));
            log.debug("");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private final String srcDir;
    private Map<String, byte[]> classMap = new HashMap<>();

    @SuppressWarnings("unused")
    private String getClassnameFromFilename(String aSrcDir, String aAbsolutePath) {
        var idx = aAbsolutePath.indexOf(aSrcDir);
        if (idx == -1) {
            throw new RuntimeException(
                    "cannot find source path '" + aSrcDir + "' in path of file '" + aAbsolutePath + "'");
        }

        var className = aAbsolutePath.substring(idx + aSrcDir.length() + 1, aAbsolutePath.lastIndexOf("."));
        log.debug("className = {}", className);
        return className;
    }

    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final IJSTParserCV jstParserCV = dbp.newBeanForInterface(IJSTParserCV.class);
}
