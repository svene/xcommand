public class HtmlFormMaker extends AbstractTemplate
{

	public HtmlFormMaker(String aAction)
	{
		action = aAction;
	}

	public void setAction(String aAction)
	{
		action = aAction;
	}



	public void execute()
	{
/*#	<form action="#*/$s(action);/*#"></form>
#*/

/*#	<form action="$jv{action}"></form>
#*/

	}

	private String action;

}
