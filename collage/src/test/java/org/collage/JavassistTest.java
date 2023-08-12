package org.collage;

import org.collage.dom.evaluator.java.javassist.JavassistTraverser;
import org.collage.template.JavassistTemplateCompiler;
import org.collage.template.TemplateCV;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xcommand.core.ICommand;
import org.xcommand.core.TCP;
import org.xcommand.util.ResourceUtil;

import java.io.StringWriter;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class JavassistTest {
	JavassistTemplateCompiler javassistTemplateCompiler;
	@BeforeEach
	public void initializeContext() {
		javassistTemplateCompiler = new JavassistTemplateCompiler(new JavassistTraverser());
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
		ICommand cmd = javassistTemplateCompiler.newTemplateCommandFromString(
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
		ICommand cmd = javassistTemplateCompiler.newTemplateCommandFromString(
			"""
				hallo ${firstname}.
				Wie geht's?
				""");

		TCP.getContext().put("firstname", "Sven");
		StringWriter sw = new StringWriter();
		TemplateCV.setWriter(sw);
		cmd.execute();
		assertThat(sw.toString()).isEqualTo("""
			hallo Sven.
			Wie geht's?
			""");
	}

	@Test
	public void testNewTemplateCommandFromStringWithNOPJava() {
		final String s = """
			hallo <?java int i = 1;?> ${firstname}.
			Wie geht's?
			""";
		ICommand cmd = javassistTemplateCompiler.newTemplateCommandFromString(s);

		TCP.getContext().put("firstname", "Sven");
		StringWriter sw = new StringWriter();
		TemplateCV.setWriter(sw);
		cmd.execute();
		assertThat(sw.toString()).isEqualTo("""
			hallo  Sven.
			Wie geht's?
			""");
	}

	@Test
	public void testNewTemplateCommandFromStringWithEffectiveJava() {

		ICommand cmd = javassistTemplateCompiler.newTemplateCommandFromString(
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
		assertThat(sw.toString()).isEqualTo("""
			hallo
			0 Sven.
			Wie geht's?
			1 Sven.
			Wie geht's?
			2 Sven.
			Wie geht's?
			""");
	}

	@Test
	public void testNewTemplateCommandFromFileWithEffectiveJava() {
		ICommand cmd = javassistTemplateCompiler.newTemplateCommandFromStream(
			ResourceUtil.newInputStreamFromResourceLocation("java03_in.txt"));

		StringWriter sw = new StringWriter();
		TemplateCV.setWriter(sw);

		TCP.getContext().put("firstname", "Sven");
		cmd.execute();
		assertThat(sw.toString()).isEqualTo("""
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
					""");
	}


}
