package org.xcommand.core

import spock.lang.Specification
import org.springframework.beans.BeanWrapper
import org.springframework.beans.BeanWrapperImpl
import java.beans.PropertyDescriptor

public class DynaBeanProviderSpockTest extends Specification
{
	IDynaBeanProvider dbpM = DynaBeanProvider.newThreadBasedDynabeanProvider(new MethodKeyProvider())

	// TODO: instead of TCP use a normal object as storage (does this need reflection then?: java.beans.Introspector)
	def setup() {
		TCP.pushContext(new HashMap())
	}

	def	cleanup() {
		TCP.popContext()
	}

	def "verify that class-and-method based DynaBeanProvider (CM-DBP) produces new instances for each call"() {
		given:
			IDynaBeanProvider dbp = newCM_DBP()
		expect:
			dbp.newBeanForInterface(IPerson)
			!dbp.newBeanForInterface(IPerson).is(dbp.newBeanForInterface(IPerson))
		when:
			IPerson person = dbp.newBeanForInterface(IPerson)
		then:
			!person.getFirstName()
			!person.getLastName()
		when:
			person.setFirstName("Sven")
			person.setLastName("Ehrke")
			Date d = new Date()
			person.setBirthDate(d)
		then:
			"Sven" == person.getFirstName()
			"Ehrke" == person.getLastName()
			d.is(person.getBirthDate())
	}

	def "verify that object-identity based DynaBeanProvider (OID-DBP) produces new instances for each call and that getters/setters work"()
	{
		given:
			IDynaBeanProvider dbp = newOID_DBP()
		when:
			IPerson person = dbp.newBeanForInterface(IPerson)
		then:
			person
			!person.getFirstName()
			!person.getLastName()
			!dbp.newBeanForInterface(IPerson).is(dbp.newBeanForInterface(IPerson))
		when:
			person.setFirstName("Sven")
			person.setLastName("Ehrke")
			Date d = new Date()
			person.setBirthDate(d)
		then:
			"Sven" == person.getFirstName()
			"Ehrke" == person.getLastName()
			d.is(person.getBirthDate())
	}

	def "verify that method based DynaBeanProvider (M-DBP) produces new instances for each call and that getters/setters work"()
	{
		given:
		given:
			IDynaBeanProvider dbp = newM_DBP()
		when:
			IPerson person = dbp.newBeanForInterface(IPerson)
		then:
			person
			!person.getFirstName()
			!person.getLastName()
			!dbp.newBeanForInterface(IPerson).is(dbp.newBeanForInterface(IPerson))
			!dbp.newBeanForInterface(IPerson).is(dbp.newBeanForInterface(IPersonNamesView))
		when:
			person.setFirstName("Sven")
			person.setLastName("Ehrke")
			Date d = new Date()
			person.setBirthDate(d)
		then:
			"Sven" == person.getFirstName()
			"Ehrke" == person.getLastName()
			d.is(person.getBirthDate())
	}

	def "verify that two view instances point to the same virtual object if CM-DBP is used"()
	{
		given:
			IDynaBeanProvider dbp = newCM_DBP()
			// Create first view instance (person1):
			IPerson person1 = dbp.newBeanForInterface(IPerson.class)
			// Create second view instance (person2):
			IPerson person2 = dbp.newBeanForInterface(IPerson.class)
		when:
			// Manipulate virtual person through first view instance:
			person1.setFirstName("Sven")
			person1.setLastName("Ehrke")
		then:
			// Verify that 'person2' points to the same virtual person as 'person1':
			"Sven" == person2.getFirstName()
			"Ehrke" == person2.getLastName()

			// Verify that properties of view instances still point to same destination:
			person1.getFirstName().is(person2.getFirstName())
			person1.getLastName().is(person2.getLastName())

		when:
			// Manipulate virtual person through second view instance:
			person2.setFirstName("Uli")
		then:
			"Uli" == person1.getFirstName()

			// Verify that properties of view instances still point to same destination:
			person1.getFirstName().is(person2.getFirstName())
			person1.getLastName().is(person2.getLastName())
	}

	def "verify that two view instances point to distinct virtual objects if OID-DBP is used"()
	{
		given:
			IDynaBeanProvider dbp = newOID_DBP()
			IPerson person1 = dbp.newBeanForInterface(IPerson.class)
			IPerson person2 = dbp.newBeanForInterface(IPerson.class)
		when:
			// Manipulate virtual person through first view instance:
			person1.setFirstName("Sven")
			person1.setLastName("Ehrke")
		then:
			// Check that properties of second view instance are still null (which proves that 'person2' does not refer to
			// 'person1''s virtual object:
			!person2.getFirstName()
			!person2.getLastName()
		when:
			person2.setFirstName("Uli")
		then:
			"Uli" == person2.getFirstName()
			"Sven" == person1.getFirstName()
	}

	def "verify that two view instances point to the same virtual object if M-DBP is used even if used interfaces are not the same"()
	{
		given:
			IDynaBeanProvider dbp = newM_DBP()
			IPerson pv1 = dbp.newBeanForInterface(IPerson.class)
			IPersonNamesView pv2 = dbp.newBeanForInterface(IPersonNamesView.class)
		when:
			// Manipulate virtual person through first view instance:
			pv1.setFirstName("Sven")
			pv1.setLastName("Ehrke")
		then:
			// Verify that 'pv2' points to the same virtual person as 'pv1':
			"Sven" == pv2.getFirstName()
			"Ehrke" == pv2.getLastName()

			// Check that properties of view instances still point to same destination:
			pv1.getFirstName().is(pv2.getFirstName())
			pv1.getLastName().is(pv2.getLastName())
		when:
			// Manipulate virtual person through second view instance:
			pv2.setFirstName("Uli");
		then:
			"Uli" == pv1.getFirstName()
			// Verify that properties of view instances still point to same destination:
			pv1.getFirstName().is(pv2.getFirstName())
			pv1.getLastName().is(pv2.getLastName())
	}

	def "demonstrate Spring BeanWrapper"()
	{
		DelegatingPerson p1 = new DelegatingPerson(dbpM.newBeanForInterface(IPerson.class))
		BeanWrapper bw = new BeanWrapperImpl(p1)
		PropertyDescriptor[] descriptors = bw.getPropertyDescriptors()
		4 == descriptors.length
		"birthDate" == descriptors[0].getName()
		"class" == descriptors[1].getName()
		"firstName" == descriptors[2].getName()
		"lastName" == descriptors[3].getName()
	}

	def "verify that object-backed bean provider works"()
	{
		given:
			IPerson p = new Person();
			BeanHoldingBeanAccessor ba = new BeanHoldingBeanAccessor(p)
			IDynaBeanProvider dbp = DynaBeanProvider.newDynabeanProvider(ba)
			IPerson pv = dbp.newBeanForInterface(IPerson.class)
		expect:
			!pv.getFirstName()
			!pv.getLastName()
			!pv.getBirthDate()
		when:
			// Modify real Person 'p':
			p.setFirstName("Sven")
		then:
			"Sven" == p.getFirstName()
			"Sven" == pv.getFirstName()
		when:
			// Modify through view 'pv':
			pv.setLastName("Ehrke")
		then:
			// Check modification on view 'pv':
			"Ehrke" == pv.getLastName()
			// Check modification on 'p':
			"Ehrke" == p.getLastName()
	}

	private IDynaBeanProvider newCM_DBP() {
		return DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider())
	}

	private IDynaBeanProvider newOID_DBP() {
		return DynaBeanProvider.newThreadBasedDynabeanProvider(new ObjectIdentityKeyProvider())
	}

	private IDynaBeanProvider newM_DBP() {
		return DynaBeanProvider.newThreadBasedDynabeanProvider(new MethodKeyProvider())
	}

}