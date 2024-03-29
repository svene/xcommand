package org.xcommand.template.jst.parser;

import org.junit.jupiter.api.Test;
import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;
import org.xcommand.technology.janino.JaninoObjectCreator;
import org.xcommand.template.jst.JSTSourceLoader;

import java.io.StringWriter;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSTTester {
	/**
	 * Test JSTLoader and JaninoObjectCreator
	 */
	@Test
	public void test1() {
		var currentWorkingDir = System.getProperty("user.dir");
		assertThat(currentWorkingDir).endsWith("xcommand/jst");
		TCP.execute(() -> {
			// Load and parse JST source:
			var jstSourceLoader = new JSTSourceLoader();
			jstSourceLoader.setSrcDir("../jst-testdata/src/main/java");
			jstSourceLoader.loadJavaFile("T1");

			// Compile parsed JST source, instatiate object and execute it:
			var janino = new JaninoObjectCreator(jstSourceLoader.getClassMap());
			var cmd = (ICommand) janino.getObject("T1");

			var sw = new StringWriter();
			TCP.getContext().put("writer", sw);
			cmd.execute();
			assertEquals("\n" +
			 "\t<h1>Hallo Du da! Ich bin Sven. Und Du?</h1>\n" +
			 "\t<p>hallihallo</p>\n", sw.toString()
			);
		});
	}

	@Test
	public void test4() {
		TCP.execute(() -> {
			// Load and parse JST source:
			var jstSourceLoader = new JSTSourceLoader();
			jstSourceLoader.setSrcDir("../jst-testdata/src/main/java");
			jstSourceLoader.loadJavaFile("T4");


			// Compile parsed JST source, instatiate object and execute it:
			var janino = new JaninoObjectCreator(jstSourceLoader.getClassMap());
			ICommand cmd = (ICommand) janino.getObject("T4");

			var sw = new StringWriter();
			TCP.getContext().put("writer", sw);
			cmd.execute();
			var bart = "\n" +
			   "\t<h1>Hallo Du da! Ich bin Bart. Und Du?</h1>\n" +
			   "\t<p>hallihallo</p>\n";
			assertEquals(bart, sw.toString());
		});
	}

	@Test
	public void test2() {
		TCP.execute(() -> {
			// Load and parse JST source:
			var jstSourceLoader = new JSTSourceLoader();
			jstSourceLoader.setSrcDir("../jst-testdata/src/main/java");
			jstSourceLoader.loadJavaFile("T2");


			// Compile parsed JST source, instatiate object and execute it:
			var janino = new JaninoObjectCreator(jstSourceLoader.getClassMap());
			ICommand cmd = (ICommand) janino.getObject("T2");

			var sw = new StringWriter();
			TCP.getContext().put("writer", sw);
			TCP.getContext().put("firstname", "Lisa");
			cmd.execute();
			assertEquals("\n" +
						 "\t<h1>Hallo Du da! Ich bin Lisa. Und Du?</h1>\n" +
						 "\t<p>hallihallo</p>\n",
				sw.toString()
			);
		});
	}

}
