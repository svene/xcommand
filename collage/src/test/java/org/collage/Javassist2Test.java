package org.collage;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringWriter;
import java.util.HashMap;
import org.collage.template.TemplateCV;
import org.collage.template.TemplateFactory;
import org.collage.template.TemplateSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xcommand.core.DynaBeanProvider;
import org.xcommand.core.ICommand;
import org.xcommand.core.IDynaBeanProvider;
import org.xcommand.core.TCP;

class Javassist2Test {
    IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();

    @BeforeEach
    void initializeContext() {
        TCP.pushContext(new HashMap<>());
        TCP.getContext().put("firstname", "Uli");
    }

    @AfterEach
    void tearDownContext() {
        TCP.popContext();
    }

    @Test
    void verify_that_recursive_template_resolving_works() throws Exception {
        var s =
                """
			Header
			
			<?java for (int i = 0; i< 3; i++) {?>
			hallo ${name}.
			  Wie gehts?
			<?java }?>
			Footer
			""";

        // First replace '${name}' with '${firstname} ${lastname}':
        TCP.pushContext(new HashMap<>());
        TCP.getContext().put("name", "${firstname} ${lastname}");
        ICommand cmd = TemplateFactory.newRecursiveTemplateInstance(new TemplateSource(s));
        TCP.popContext();

        TCP.getContext().put("firstname", "Uli");
        TCP.getContext().put("lastname", "Ehrke");
        StringWriter sw = new StringWriter();
        TemplateCV.setWriter(sw);
        cmd.execute();

        // todo: why is newline at beginning lost?:
        assertThat(sw.toString().charAt(0)).isNotEqualTo('\n');
        assertThat(sw.toString().charAt(0)).isEqualTo('H');
        assertThat(sw.toString())
                .isEqualTo(
                        """
			Header
			
			hallo Uli Ehrke.
			  Wie gehts?
			hallo Uli Ehrke.
			  Wie gehts?
			hallo Uli Ehrke.
			  Wie gehts?
			Footer
			""");
    }

    @Test
    void verify_that_recursive_template_resolving_works_with_3_levels_of_nesting() throws Exception {
        // person -> nameAndAddress -> address:
        String person = "${person}";
        String nameAndAddress = "${firstname} ${lastname} ${address}";
        String address = """
			
			Sohlweg 13
			D-79589 Binzen
			""";

        // Use new context for template command creation:
        TCP.pushContext(new HashMap());
        TCP.getContext().put("person", nameAndAddress);
        TCP.getContext().put("address", address);
        ICommand cmd = TemplateFactory.newRecursiveTemplateInstance(new TemplateSource(person));
        TCP.popContext();

        StringWriter sw = new StringWriter();
        TemplateCV.setWriter(sw);

        cmd.execute();
        assertThat(sw.toString()).isEqualTo("""
			Uli ${lastname}\s
			Sohlweg 13
			D-79589 Binzen
			""");

        sw = new StringWriter();
        TemplateCV.setWriter(sw);
        TCP.getContext().put("lastname", "Ehrke");
        cmd.execute();
        assertThat(sw.toString()).isEqualTo("""
				Uli Ehrke\s
				Sohlweg 13
				D-79589 Binzen
				""");
    }
}
