import org.xcommand.core.ICommand;
import org.xcommand.template.jst.DefaultJSTRoutines;
import org.xcommand.template.jst.IJSTRoutines;

public abstract class AbstractTemplate implements ICommand
{

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
