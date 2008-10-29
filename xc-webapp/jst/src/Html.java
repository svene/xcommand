public class Html extends AbstractTemplate
{
	public Html()
	{
	}

	public void execute()
	{
	}

	public void form(String aAction)
	{
		HtmlForm f = new HtmlForm();
		f.setAction(aAction);
		new HtmlFormWriter(f).execute();
	}

}
