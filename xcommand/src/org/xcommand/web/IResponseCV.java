package org.xcommand.web;

public interface IResponseCV
{
	public Integer getHttpErrorCode();
	public String getHttpErrorMessage();
	public void setHttpErrorCode(Integer aHttpErrorCode);
	public void setHttpErrorMessage(String aHttpErrorMessage);
}
