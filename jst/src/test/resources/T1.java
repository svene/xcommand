import org.xcommand.core.ICommand;
import org.xcommand.template.jst.DefaultJSTRoutines;
import org.xcommand.template.jst.IJSTRoutines;

public class T1 implements ICommand
{
	@Override
	public void execute()
	{
/*#
	<h1>Hallo Du da! Ich bin Sven. Und Du?</h1>
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

	private final IJSTRoutines jstr = new DefaultJSTRoutines();
}
