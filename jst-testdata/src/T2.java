import org.xcommand.template.jst.IJSTRoutines;
import org.xcommand.template.jst.DefaultJSTRoutines;
import org.xcommand.core.ICommand;

public class T2 implements ICommand
{
	public void execute()
	{
/*#
	<h1>Hallo Du da! Ich bin #*/$v("firstname");/*#. Und Du?</h1>
	<p>hallihallo</p>
#*/
	}

	protected void $s(String aString)
	{
		jstr.$s(aString);
	}
	protected void $v(String aName)
	{
		jstr.$v(aName);
	}

	private IJSTRoutines jstr = new DefaultJSTRoutines();
}