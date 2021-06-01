package org.xcommand.template.jst.parser;

import org.junit.jupiter.api.Test;
import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;
import org.xcommand.technology.janino.JaninoObjectCreator;
import org.xcommand.template.jst.JSTSourceLoader;

import java.io.StringWriter;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSTTester
{
	/** Test JSTLoader and JaninoObjectCreator */
	@Test
	public void test1() {
		// Load and parse JST source:
		TCP.pushContext(new HashMap());
		JSTSourceLoader jstSourceLoader = new JSTSourceLoader();
		jstSourceLoader.setSrcDir("../jst-testdata/src/main/java");
		jstSourceLoader.loadClass("T1");


		// Compile parsed JST source, instatiate object and execute it:
		JaninoObjectCreator janino = new JaninoObjectCreator(jstSourceLoader.getClassMap());
		Object obj = janino.getObject("T1");
		ICommand cmd = (ICommand) obj;

		StringWriter sw = new StringWriter();
		TCP.getContext().put("writer", sw);
		cmd.execute();
		assertEquals("\n\t<h1>Hallo Du da! Ich bin Sven. Und Du?</h1>\n\t<p>hallihallo</p>\n", sw.toString());
	}

	@Test
	public void test4() {
		// Load and parse JST source:
		TCP.pushContext(new HashMap());
		JSTSourceLoader jstSourceLoader = new JSTSourceLoader();
		jstSourceLoader.setSrcDir("../jst-testdata/src/main/java");
		jstSourceLoader.loadClass("T4");


		// Compile parsed JST source, instatiate object and execute it:
		JaninoObjectCreator janino = new JaninoObjectCreator(jstSourceLoader.getClassMap());
		Object obj = janino.getObject("T4");
		ICommand cmd = (ICommand) obj;

		StringWriter sw = new StringWriter();
		TCP.getContext().put("writer", sw);
		cmd.execute();
		String bart = "\n\t<h1>Hallo Du da! Ich bin Bart. Und Du?</h1>\n\t<p>hallihallo</p>\n";
		assertEquals(bart, sw.toString());
	}

	@Test
	public void test2() {
		// Load and parse JST source:
		TCP.pushContext(new HashMap());
		JSTSourceLoader jstSourceLoader = new JSTSourceLoader();
		jstSourceLoader.setSrcDir("../jst-testdata/src/main/java");
		jstSourceLoader.loadClass("T2");


		// Compile parsed JST source, instatiate object and execute it:
		JaninoObjectCreator janino = new JaninoObjectCreator(jstSourceLoader.getClassMap());
		Object obj = janino.getObject("T2");
		ICommand cmd = (ICommand) obj;

		StringWriter sw = new StringWriter();
		TCP.getContext().put("writer", sw);
		TCP.getContext().put("firstname", "Lisa");
		cmd.execute();
		assertEquals("\n\t<h1>Hallo Du da! Ich bin Lisa. Und Du?</h1>\n\t<p>hallihallo</p>\n", sw.toString());
	}

}
