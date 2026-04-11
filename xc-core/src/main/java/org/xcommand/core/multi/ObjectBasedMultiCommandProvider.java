package org.xcommand.core.multi;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.xcommand.core.ICommand;

public final class ObjectBasedMultiCommandProvider extends BaseMultiCommandProvider {
    public ObjectBasedMultiCommandProvider(Object aTargetObject) {
        super(buildCommandMap(aTargetObject));
    }

    private static Map<String, ICommand> buildCommandMap(Object aTargetObject) {
        return Arrays.stream(aTargetObject.getClass().getDeclaredMethods())
                .filter(m -> m.getParameterTypes().length == 1)
                .filter(m -> m.getParameterTypes()[0].isAssignableFrom(Map.class))
                .collect(Collectors.toMap(m -> m.getName(), m -> MethodCmd.fromObject(aTargetObject, m)));
    }
}
