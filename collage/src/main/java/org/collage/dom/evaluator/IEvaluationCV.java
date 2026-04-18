package org.collage.dom.evaluator;

import java.io.Writer;
import org.xcommand.core.TCP;

public interface IEvaluationCV {
    Writer getWriter();

    void setWriter(Writer aWriter);

    String NS = IEvaluationCV.class.getName() + ".";
    String KEY_WRITER = NS + "Writer";

    static boolean hasWriter() {
        return TCP.getContext().containsKey(KEY_WRITER);
    }
}
