package org.collage;

import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.template.CachingTextTemplateCompiler;
import org.collage.template.TextTemplateCompiler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xcommand.core.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CachingTextTemplateCompilerTest {

	private static final int RUNS = 10000;

	@BeforeEach
	void initializeContext() {
		TCP.getContext().put("firstname", "Uli");
	}

	@Test
	void exerciseTextTemplateCompiler() {
		for (int i = 0; i < RUNS; i++) {
			new TextTemplateCompiler().newTemplateCommandFromString("hallo ${firstname}. Wie gehts?").execute();
			assertEquals("hallo Uli. Wie gehts?", stringHandlerCV.getString());
		}
	}

	/**
	 * Much faster than previous routine
	 */
	@Test
	void exerciseCachingTextTemplateCompiler() {
		// On first template request `CachingTextTemplateCompiler' will compile unknown template:
		new CachingTextTemplateCompiler().getTemplateCommand("hallo ${firstname}. Wie gehts?").execute();
		String s = stringHandlerCV.getString();
		assertEquals("hallo Uli. Wie gehts?", s);

		// For further template request `CachingTextTemplateCompiler' should find template in cache:
		for (int i = 0; i < RUNS; i++) {
			new CachingTextTemplateCompiler().getTemplateCommand("hallo ${firstname}. Wie gehts?").execute();
			s = stringHandlerCV.getString();
			assertEquals("hallo Uli. Wie gehts?", s);
		}
	}

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	private final IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
}
