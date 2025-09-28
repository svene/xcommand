package org.collage.template;

import java.util.HashMap;
import java.util.Map;
import org.xcommand.core.ICommand;

public class CachingTextTemplateCompiler {
    public ICommand getTemplateCommand(String aStringTemplate) {
        synchronized (templateCache) {
            var cmd = templateCache.get(aStringTemplate);
            if (cmd == null) {
                cmd = textTemplateCompiler.newTemplateCommandFromString(aStringTemplate);
                templateCache.put(aStringTemplate, cmd);
            }
            return cmd;
        }
    }

    private static final Map<String, ICommand> templateCache = new HashMap<>();
    private static final TextTemplateCompiler textTemplateCompiler = new TextTemplateCompiler();
}
