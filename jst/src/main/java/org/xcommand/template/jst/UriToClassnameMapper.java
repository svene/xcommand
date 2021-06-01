package org.xcommand.template.jst;

public class UriToClassnameMapper implements IUriToClassnameMapper
{
	public String getClassnameForUri(String aURI)
	{
		if (aURI == null) {
			throw new IllegalArgumentException("aURI == null");
		}
		if (aURI.length() == 0) {
			throw new IllegalArgumentException("aURI.length() == 0");
		}
		int idx = aURI.indexOf(ENDING);
		if (idx == -1)
		{
			throw new IllegalArgumentException("URI (" + aURI + ") does not end  with '" + ENDING + "'");
		}
		else
		{
			int start = aURI.startsWith("/") ? 1 : 0;
			String result = aURI.substring(start, idx);
			result = result.replace('/', '.');
			return result;
		}
	}

	private static final String ENDING = ".jst";
}
