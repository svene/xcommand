package org.xcommand.web;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;

public class RegExpDispatcherCmd implements ICommand {
    public RegExpDispatcherCmd(Map<String, ICommand> commands) {
        this.commands = commands;
    }

    @Override
    public void execute() {
        HttpServletRequest request = webCV.getRequest();
        String path = request.getRequestURI();
        Iterator<String> it = commands.keySet().iterator();
        boolean found = false;
        ICommand cmd = null;
        while (!found && it.hasNext()) {
            String regex = it.next();
            Pattern pattern = Pattern.compile(regex);
            Matcher m = pattern.matcher(path);
            if (m.matches()) {
                cmd = commands.get(regex);
                found = true;
            }
        }

        if (cmd != null) {
            cmd.execute();
        }
    }

    // Commands identified by pattern (e.g. '^/bla.*') used as key for this map:
    private final Map<String, ICommand> commands;
    private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
    private final IWebCV webCV = dbp.newBeanForInterface(IWebCV.class);
}
