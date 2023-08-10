package org.collage;

import org.collage.template.JavassistTemplateCompiler;
import org.collage.template.TemplateCV;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;
import org.xcommand.util.ResourceUtil;

import java.io.StringWriter;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JavassistTest {
	@BeforeEach
	public void initializeContext() {
		TCP.pushContext(new HashMap<>());
		TCP.getContext().put("firstname", "Uli");
	}

	@AfterEach
	public void tearDownContext() {
		TCP.popContext();
	}

	/* Create a template command from a string, execute it and write output to System.out.
	 * Note: by default the code in  {@link org.collage.dom.evaluator.java.javassist.ExitRootHandler.executeMethod} writes to System.out */
	@Test
	public void exerciseNewTemplateCommandFromStringUsingSystemOut() {
		ICommand cmd = new JavassistTemplateCompiler().newTemplateCommandFromString(
			"""
				hallo ${firstname}.
				Wie geht's?
				""");

		TCP.getContext().put("firstname", "Sven");
		cmd.execute();
	}

	/* Create a template command via TemplateSouce, execute it and write output to StringWriter (to be able to unittest result) */
	@Test
	public void testNewTemplateCommandFromString() {
		ICommand cmd = new JavassistTemplateCompiler().newTemplateCommandFromString(
			"""
				hallo ${firstname}.
				Wie geht's?
				""");

		TCP.getContext().put("firstname", "Sven");
		StringWriter sw = new StringWriter();
		TemplateCV.setWriter(sw);
		cmd.execute();
		assertEquals("""
			hallo Sven.
			Wie geht's?
			""", sw.toString());
	}

	@Test
	public void testNewTemplateCommandFromStringWithNOPJava() {
		final String s = """
			hallo <?java int i = 1;?> ${firstname}.
			Wie geht's?
			""";
		ICommand cmd = new JavassistTemplateCompiler().newTemplateCommandFromString(s);

		TCP.getContext().put("firstname", "Sven");
		StringWriter sw = new StringWriter();
		TemplateCV.setWriter(sw);
		cmd.execute();
		assertEquals("""
			hallo  Sven.
			Wie geht's?
			""", sw.toString());
	}

	@Test
	public void testNewTemplateCommandFromStringWithEffectiveJava() {

		ICommand cmd = new JavassistTemplateCompiler().newTemplateCommandFromString(
			"""
				hallo
				<?java for (int i = 0; i< 3; i++){ _writer.write(String.valueOf(i));?> ${firstname}.
				Wie geht's?
				<?java }?>
				""");

		StringWriter sw = new StringWriter();
		TemplateCV.setWriter(sw);
		TCP.getContext().put("firstname", "Sven");
		cmd.execute();
		assertEquals("""
			hallo
			0 Sven.
			Wie geht's?
			1 Sven.
			Wie geht's?
			2 Sven.
			Wie geht's?
			""", sw.toString());
	}

	@Test
	public void testNewTemplateCommandFromFileWithEffectiveJava() {
		ICommand cmd = new JavassistTemplateCompiler().newTemplateCommandFromStream(
			ResourceUtil.newInputStreamFromResourceLocation("java03_in.txt"));

		StringWriter sw = new StringWriter();
		TemplateCV.setWriter(sw);

		TCP.getContext().put("firstname", "Sven");
		cmd.execute();
		assertEquals("""
					0: Hallo Sven. Wie gehts?
					1: Hallo Sven. Wie gehts?
					2: Hallo Sven. Wie gehts?
					3: Hallo Sven. Wie gehts?
					4: Hallo Sven. Wie gehts?
					5: Hallo Sven. Wie gehts?
					6: Hallo Sven. Wie gehts?
					7: Hallo Sven. Wie gehts?
					8: Hallo Sven. Wie gehts?
					9: Hallo Sven. Wie gehts?
					""", sw.toString());
	}


}
