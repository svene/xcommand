package org.xcommand.template.jst.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.StringWriter;
import org.junit.jupiter.api.Test;
import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;
import org.xcommand.technology.janino.JaninoObjectCreator;
import org.xcommand.template.jst.JSTJavaResourceLoader;

public class JSTResourceTester {
    /**
     * Test JSTLoader and JaninoObjectCreator
     */
    @Test
    public void test1() {
        TCP.execute(() -> {
            // Load and parse JST source:
            var jstJavaResourceLoader = new JSTJavaResourceLoader();
            jstJavaResourceLoader.load("R1.java");

            // Compile parsed JST source, instantiate object and execute it:
            var janino = new JaninoObjectCreator(jstJavaResourceLoader.getClassMap());
            var cmd = (ICommand) janino.getObject("R1");

            var sw = new StringWriter();
            TCP.getContext().put("writer", sw);
            cmd.execute();
            assertEquals(
                    """
					
					\t<h1>Hallo Du da! Ich bin Sven. Und Du?</h1>
					\t<p>hallihallo</p>
					""",
                    sw.toString());
        });
    }

    @Test
    public void test4() {
        TCP.execute(() -> {
            // Load and parse JST source:
            var jstJavaResourceLoader = new JSTJavaResourceLoader();
            jstJavaResourceLoader.load("R4.java");

            // Compile parsed JST source, instantiate object and execute it:
            var janino = new JaninoObjectCreator(jstJavaResourceLoader.getClassMap());
            var cmd = (ICommand) janino.getObject("R4");

            var sw = new StringWriter();
            TCP.getContext().put("writer", sw);
            cmd.execute();
            String bart = """
				
				\t<h1>Hallo Du da! Ich bin Bart. Und Du?</h1>
				\t<p>hallihallo</p>
				""";
            assertEquals(bart, sw.toString());
        });
    }

    @Test
    public void test2() {
        TCP.execute(() -> {
            // Load and parse JST source:
            var jstJavaResourceLoader = new JSTJavaResourceLoader();
            jstJavaResourceLoader.load("R2.java");

            // Compile parsed JST source, instantiate object and execute it:
            var janino = new JaninoObjectCreator(jstJavaResourceLoader.getClassMap());
            ICommand cmd = (ICommand) janino.getObject("R2");

            var sw = new StringWriter();
            TCP.getContext().put("writer", sw);
            TCP.getContext().put("firstname", "Lisa");
            cmd.execute();
            assertEquals(
                    """
					
					\t<h1>Hallo Du da! Ich bin Lisa. Und Du?</h1>
					\t<p>hallihallo</p>
					""",
                    sw.toString());
        });
    }
}
