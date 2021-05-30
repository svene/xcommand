package org.collage;

import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.template.CachingTextTemplateCompiler;
import org.collage.template.TextTemplateCompiler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.xcommand.core.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CachingTextTemplateCompilerSample
{

	private static final int RUNS = 10000;

	@BeforeAll
	public void initializeContext() throws Exception
	{
		TCP.getContext().put("firstname", "Uli");
	}

	@Test
	public void exerciseTextTemplateCompiler()
	{
		for (int i = 0; i < RUNS; i++)
		{
			new TextTemplateCompiler().newTemplateCommandFromString("hallo ${firstname}. Wie gehts?").execute();
			assertEquals("hallo Uli. Wie gehts?", stringHandlerCV.getString());
		}
	}

	/** Much faster than previous routine */
	@Test public void exerciseCachingTextTemplateCompiler()
	{
		// On first template request `CachingTextTemplateCompiler' will compile unknown template:
		new CachingTextTemplateCompiler().getTemplateCommand("hallo ${firstname}. Wie gehts?").execute();
		String s = stringHandlerCV.getString();
		assertEquals("hallo Uli. Wie gehts?", s);

		// For further template request `CachingTextTemplateCompiler' should find template in cache:
		for (int i = 0; i < RUNS; i++)
		{
			new CachingTextTemplateCompiler().getTemplateCommand("hallo ${firstname}. Wie gehts?").execute();
			s = stringHandlerCV.getString();
			assertEquals("hallo Uli. Wie gehts?", s);
		}
	}
	private IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);
}
