/** Shows different approaches to create a form (only the last (direct html) seems to make sense */
public class T3 extends AbstractTemplate
{
	public void execute()
	{

/*#		<h1>T3</h1>#*/
		HtmlFormMaker fm = new HtmlFormMaker("/someaction.do");
		fm.execute();
		fm.setAction("/other.do");
		fm.execute();

		Html html = new Html();
		html.form("/someotheraction.do");

		String action = "/thirdaction.do";
		String firstname = "sven";
/*#
		<form action="$jv{action}">
			<input type="text" name="firstname" value="$jv{firstname}"/>
			<input type="submit" value="Submit"/>
		</form>
#*/

	}
}
