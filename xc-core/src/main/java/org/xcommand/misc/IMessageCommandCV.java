package org.xcommand.misc;

import java.io.PrintWriter;
import java.util.List;

public interface IMessageCommandCV {
    PrintWriter getPrintWriter();

    List<String> getList();

    void setPrintWriter(PrintWriter aPrintWriter);

    void setList(List<String> aList);
}
