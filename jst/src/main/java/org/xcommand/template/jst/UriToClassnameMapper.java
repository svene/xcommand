package org.xcommand.template.jst;

import java.util.Objects;

public class UriToClassnameMapper implements IUriToClassnameMapper {
    @Override
    public String getClassnameForUri(String aURI) {
        Objects.requireNonNull(aURI, "aURI");
        if (aURI.length() == 0) {
            throw new IllegalArgumentException("aURI.length() == 0");
        }
        var idx = aURI.indexOf(ENDING);
        if (idx == -1) {
            throw new IllegalArgumentException("URI (" + aURI + ") does not end  with '" + ENDING + "'");
        } else {
            var start = aURI.startsWith("/") ? 1 : 0;
            var result = aURI.substring(start, idx);
            result = result.replace('/', '.');
            return result;
        }
    }

    private static final String ENDING = ".jst";
}
