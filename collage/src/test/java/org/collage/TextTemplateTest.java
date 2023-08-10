package org.collage;

import org.collage.dom.creationhandler.IDomNodeCreationHandlerCV;
import org.collage.dom.evaluator.common.IStringHandlerCV;
import org.collage.dom.evaluator.java.javassist.JavassistTraverser;
import org.collage.template.JavassistTemplateCompiler;
import org.collage.template.TemplateCV;
import org.collage.template.TextTemplateCompiler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.xcommand.core.*;

import java.io.StringWriter;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextTemplateTest {
	static String FIRSTNAME = "Denis";
	static String LASTNAME = "Bogan";

	StringWriter stringWriter;

	@BeforeEach
	public void initializeContext() {
		TCP.pushContext(new HashMap<>());
		TCP.getContext().put("firstname", FIRSTNAME);

		stringWriter = new StringWriter();
	}

	@AfterEach
	public void tearDownContext() {
		TCP.popContext();
	}

	private String createTemplate(String in) {
		new TextTemplateCompiler().newTemplateCommandFromString(in).execute();
		return stringHandlerCV.getString();
	}

	@Nested
	class Basic {
		@Test
		public void test1() {
			String out = createTemplate("hallo ${firstname}.\nWie gehts?\n");
			assertEquals("hallo %s.\nWie gehts?\n".formatted(FIRSTNAME), out);
		}
		@Test
		public void test2() {
			var tc = new TextTemplateCompiler().newTemplateCommandFromString("hallo ${firstname}.\nWie gehts?\n");
			tc.setWriter(stringWriter);
			tc.execute();
			assertEquals("hallo %s.\nWie gehts?\n".formatted(FIRSTNAME), stringWriter.toString());
		}

		@Test
		public void verfiyProperFunctionIfInputComesFromFile() {
			var tc = new TextTemplateCompiler().newTemplateCommandFromResourceName("in.txt");
			tc.setWriter(stringWriter);
			tc.execute();
			assertEquals("Hallo %s! Willkommen bei uns.\n<?java int i = 1 ?>d\n".formatted(FIRSTNAME), stringWriter.toString());
		}

		@Test
		public void test5() {
			domNodeCreationHandlerCV.setProduceJavaSource(Boolean.FALSE);
			var tc = new TextTemplateCompiler().newTemplateCommandFromString("hallo ${firstname}.\nWie gehts?\n");

			TCP.getContext().put("firstname", FIRSTNAME);
			tc.setWriter(stringWriter);
			tc.execute();
			assertEquals("hallo %s.\nWie gehts?\n".formatted(FIRSTNAME), stringWriter.toString());
		}

	}

	/**
	 * Demonstrate recursive template handling
	 */
	@Nested
	class Recursive {
		@Test
		public void recursive_placeholder_handling() {
			TCP.getContext().put("name", "${firstname} ${lastname}");
			assertThat(
				createTemplate("hallo ${name}. Wie gehts?")
			).isEqualTo("hallo ${firstname} ${lastname}. Wie gehts?");
		}

	}

	@Nested
	class Misc {
		@Test
		public void unknowns_are_not_replaced() {
			assertThat(createTemplate("hallo ${lastname}. Wie gehts?")).isEqualTo("hallo ${lastname}. Wie gehts?");
		}
		@Test
		public void only_knowns_are_replaced() {
			assertThat(
				createTemplate("hallo ${firstname} ${lastname}. Wie gehts?")
			).isEqualTo("hallo %s ${lastname}. Wie gehts?".formatted(FIRSTNAME));
		}
		@Test
		public void javassist_template() {
			var cmd = new JavassistTemplateCompiler(new JavassistTraverser()).newTemplateCommandFromString("hallo ${lastname}. Wie gehts?");
			TCP.getContext().put("firstname", FIRSTNAME);
			TCP.getContext().put("lastname", LASTNAME);
			TemplateCV.setWriter(stringWriter);
			cmd.execute();
			assertEquals("hallo %s. Wie gehts?".formatted(LASTNAME), stringWriter.toString());
		}

	}

	private final IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
	final IStringHandlerCV stringHandlerCV = dbp.newBeanForInterface(IStringHandlerCV.class);
	final IDomNodeCreationHandlerCV domNodeCreationHandlerCV = dbp.newBeanForInterface(IDomNodeCreationHandlerCV.class);
}
