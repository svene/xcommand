package org.xcommand.core;

import junit.framework.TestCase;

import java.util.Date;

public class DynaBeanProviderTester extends TestCase
{

	public void test0()
	{
		IPerson person1 = (IPerson) dbp.newBeanForInterface(IPerson.class);
		assertNotNull(person1);

		IPerson person2 = (IPerson) dbp.newBeanForInterface(IPerson.class);
		assertNotSame(person1, person2);

		person1 = (IPerson) dbp2.newBeanForInterface(IPerson.class);
		assertNotNull(person1);

		person2 = (IPerson) dbp2.newBeanForInterface(IPerson.class);
		assertNotSame(person1, person2);

	}
	public void test1()
	{
		IPerson person = (IPerson) dbp.newBeanForInterface(IPerson.class);
		person.setFirstName("Sven");
		person.setLastName("Ehrke");
		Date d = new Date();
		person.setBirthDate(d);
		assertEquals("Sven", person.getFirstName());
		assertEquals("Ehrke", person.getLastName());
		assertEquals(d, person.getBirthDate());
		assertSame(d, person.getBirthDate());
	}

	public void test2()
	{
		IPerson person1 = (IPerson) dbp.newBeanForInterface(IPerson.class);
		IPerson person2 = (IPerson) dbp.newBeanForInterface(IPerson.class);
		assertNotSame(person1, person2);

		person1 = (IPerson) dbp.newBeanForInterface(IPerson.class);
		person2 = (IPerson) dbp.newBeanForInterface(IPerson.class);
		assertNotSame(person1, person2);
		person1.setFirstName("Sven");
		person1.setLastName("Ehrke");
		assertEquals("Sven", person1.getFirstName());
		assertEquals("Ehrke", person1.getLastName());
		assertEquals("Sven", person2.getFirstName());
		assertEquals("Ehrke", person2.getLastName());

	}
// --- Implementation ---

	private IDynaBeanProvider dbp = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	private IDynaBeanProvider dbp2 = DynaBeanProvider.getObjectIdentityBasedDynaBeanProvider();
}
