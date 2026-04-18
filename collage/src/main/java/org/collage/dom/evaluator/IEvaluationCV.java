package org.collage.dom.evaluator;

import java.io.Writer;
import java.util.Optional;

public interface IEvaluationCV {
    Optional<Writer> getWriter();

    void setWriter(Writer aWriter);
}
