package org.xcommand.core.multi;

import java.lang.reflect.Method;
import org.jooq.lambda.Sneaky;
import org.xcommand.core.ICommand;

public class MethodCmd implements ICommand {
    public static MethodCmd fromClass(Class<?> aClass, Method aMethod) {
        try {
            var obj = aClass.getDeclaredConstructor().newInstance();
            return fromObject(obj, aMethod);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static MethodCmd fromObject(Object multiCommandObject, Method aMethod) {
        return new MethodCmd(multiCommandObject, aMethod);
    }

    private MethodCmd(Object multiCommandObject, Method aMethod) {
        this.multiCommandObject = multiCommandObject;
        this.method = aMethod;
    }

    @Override
    public void execute() {
        Sneaky.runnable(() -> method.invoke(multiCommandObject, (Object[]) null))
                .run();
    }

    Method method;
    Object multiCommandObject;
}
