package org.xcommand.template.jst;

@FunctionalInterface
public interface IUriToClassnameMapper {
	String getClassnameForUri(String aURI);
}
