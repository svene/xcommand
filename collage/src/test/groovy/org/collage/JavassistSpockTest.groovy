package org.collage

import org.collage.dom.evaluator.common.IStringHandlerCV
import org.collage.template.TemplateCV
import org.collage.template.TemplateFactory
import org.collage.template.TemplateSource
import spock.lang.Specification
import org.xcommand.core.*

class JavassistSpockTest extends Specification {
	IDynaBeanProvider dbp = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	IStringHandlerCV stringHandlerCV = (IStringHandlerCV) dbp.newBeanForInterface(IStringHandlerCV.class);

	// TODO: instead of TCP use a normal object as storage (does this need reflection then?: java.beans.Introspector)
	def setup() {
		TCP.pushContext(new HashMap());
		TCP.getContext().put("firstname", "Uli");
	}

	def	cleanup() {
		TCP.popContext();
	}

	def "verify that recursive template resolving works"() {
		given:
			String s = '''
				|Header
				|
				|<?java for (int i = 0; i< 3; i++) {?>
				|hallo ${name}.
				|  Wie gehts?
				|<?java }?>
				|Footer
				|'''.stripMargin()

			// First replace '${name}' with '${firstname} ${lastname}':
			TCP.pushContext(new HashMap());
			TCP.getContext().put("name", '${firstname} ${lastname}');
			ICommand cmd = TemplateFactory.newRecursiveTemplateInstance(new TemplateSource(s));
			TCP.popContext();
		when:
			TCP.getContext().put("firstname", "Uli");
			TCP.getContext().put("lastname", "Ehrke");
			StringWriter sw = new StringWriter();
			TemplateCV.setWriter(sw);
			cmd.execute();
			s[0] == '\n'
		then:
			// todo: why is newline at beginning lost?:
			sw.toString()[0] != '\n'
			sw.toString()[0] == 'H'
			'''Header
			|
			|hallo Uli Ehrke.
			|  Wie gehts?
			|hallo Uli Ehrke.
			|  Wie gehts?
			|hallo Uli Ehrke.
			|  Wie gehts?
			|Footer
			|'''.stripMargin() == sw.toString()
	}

	def "verify that recursive template resolving works with 3 levels of nesting"() {
		given:
			// person -> nameAndAddress -> address:
			String person = '${person}'
			String nameAndAddress = '${firstname} ${lastname} ${address}'
			String address = '''
				|Sohlweg 13
				|D-79589 Binzen
				|'''.stripMargin()

			// Use new context for template command creation:
			TCP.pushContext(new HashMap());
			TCP.getContext().put("person", nameAndAddress);
			TCP.getContext().put("address", address);
			ICommand cmd = TemplateFactory.newRecursiveTemplateInstance(new TemplateSource(person));
			TCP.popContext();

			StringWriter sw = new StringWriter();
			TemplateCV.setWriter(sw);

		when:
			cmd.execute();
		then:
			'''Uli ${lastname} 
			|Sohlweg 13
			|D-79589 Binzen
			|'''.stripMargin() == sw.toString()

		when:
			sw = new StringWriter();
			TemplateCV.setWriter(sw);
			TCP.getContext().put("lastname", "Ehrke");
			cmd.execute();
		then:
			'''Uli Ehrke 
			|Sohlweg 13
			|D-79589 Binzen
			|'''.stripMargin() == sw.toString()

	}
}
