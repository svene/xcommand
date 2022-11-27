package org.xcommand.web;

public interface IResponseCV {
	Integer getHttpErrorCode();

	String getHttpErrorMessage();

	void setHttpErrorCode(Integer aHttpErrorCode);

	void setHttpErrorMessage(String aHttpErrorMessage);
}
