public class HtmlFormWriter extends AbstractTemplate
{

	public HtmlFormWriter(HtmlForm aForm)
	{
		form = aForm;
	}

	public void execute()
	{
/*#	<form action="#*/$s(form.getAction());/*#"></form>#*/
	}

	private HtmlForm form;

}
