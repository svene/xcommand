package org.xcommand.template.jst;

import java.io.InputStream;
import org.xcommand.core.TCP;

public interface IJSTParserCV {
    StringBuffer getGeneratedJavaCode();

    InputStream getInputStream();

    String getEncoding();

    void setGeneratedJavaCode(StringBuffer aGeneratedJavaCode);

    void setInputStream(InputStream aInputStream);

    void setEncoding(String aEncoding);

    String NS = IJSTParserCV.class.getName() + ".";
    String KEY_ENCODING = NS + "Encoding";

    static boolean hasEncoding() {
        return TCP.getContext().containsKey(KEY_ENCODING);
    }
}
