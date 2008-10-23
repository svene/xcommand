package org.xcommand.core;

import junit.framework.TestCase;

import java.util.Date;
import java.util.HashMap;

public class DynaBeanProviderTester extends TestCase
{
	// TODO: instead of TCP use a normal object as storage (does this need reflection then?: java.beans.Introspector)

	protected void setUp() throws Exception
	{
		TCP.pushContext(new HashMap());
	}

	protected void tearDown() throws Exception
	{
		TCP.popContext();
	}

	public void test0()
	{
		IPerson person1 = (IPerson) dbpCM.newBeanForInterface(IPerson.class);
		assertNotNull(person1);

		IPerson person2 = (IPerson) dbpCM.newBeanForInterface(IPerson.class);
		assertNotSame(person1, person2);

		person1 = (IPerson) dbpOI.newBeanForInterface(IPerson.class);
		assertNotNull(person1);

		person2 = (IPerson) dbpOI.newBeanForInterface(IPerson.class);
		assertNotSame(person1, person2);

	}
	public void test1()
	{
		IPerson person = (IPerson) dbpCM.newBeanForInterface(IPerson.class);
		assertNull(person.getFirstName());
		assertNull(person.getLastName());
		person.setFirstName("Sven");
		person.setLastName("Ehrke");
		Date d = new Date();
		person.setBirthDate(d);
		assertEquals("Sven", person.getFirstName());
		assertEquals("Ehrke", person.getLastName());
		assertEquals(d, person.getBirthDate());
		assertSame(d, person.getBirthDate());
	}

	/** Test that two view instances point to the same virtual object if
	 * the ClassAndMethodBasedDynaBeanProvider is used
	 */
	public void testClassAndMethodBasedDynaBeanProvider()
	{
		// Create first view instance (person1):
		IPerson person1 = (IPerson) dbpCM.newBeanForInterface(IPerson.class);
		// Create second view instance (person2):
		IPerson person2 = (IPerson) dbpCM.newBeanForInterface(IPerson.class);
		// Check that the two view instances are disctinct:
		assertNotSame(person1, person2);

		// Check that properties of view instances are null:
		assertNull(person1.getFirstName());
		assertNull(person1.getLastName());
		assertNull(person2.getFirstName());
		assertNull(person2.getLastName());

		// Manipulate virtual person through first view instance:
		person1.setFirstName("Sven");
		person1.setLastName("Ehrke");

		// Check that virtual person object pointed to by 'person1' got changed:
		assertEquals("Sven", person1.getFirstName());
		assertEquals("Ehrke", person1.getLastName());

		// Check that 'person2' points to the same virtual person as 'person1':
		assertEquals("Sven", person2.getFirstName());
		assertEquals("Ehrke", person2.getLastName());

		// Check that properties of view instances still point to same destination:
		assertSame(person1.getFirstName(), person2.getFirstName());
		assertSame(person1.getLastName(), person2.getLastName());

		// Manipulate virtual person through second view instance:
		person2.setFirstName("Uli");
		assertEquals("Uli", person1.getFirstName());
		assertEquals("Uli", person2.getFirstName());

		// Check that properties of view instances still point to same destination:
		assertSame(person1.getFirstName(), person2.getFirstName());
		assertSame(person1.getLastName(), person2.getLastName());

	}
	/** Test that two view instances point to separate virtual person objects if
	 * the ObjectIdentityBasedDynaBeanProvider is used
	 */
	public void testObjectIdentityBasedDynaBeanProvider()
	{
		// Create first view instance (person1):
		IPerson person1 = (IPerson) dbpOI.newBeanForInterface(IPerson.class);
		// Create second view instance (person2):
		IPerson person2 = (IPerson) dbpOI.newBeanForInterface(IPerson.class);
		// Check that the two view instances are disctinct:
		assertNotSame(person1, person2);

		// Check that properties of view instances are null:
		assertNull(person1.getFirstName());
		assertNull(person1.getLastName());
		assertNull(person2.getFirstName());
		assertNull(person2.getLastName());

		// Manipulate first virtual person through first view instance:
		person1.setFirstName("Sven");
		person1.setLastName("Ehrke");

		// Check that first virtual person object pointed to by 'person1' got changed:
		assertEquals("Sven", person1.getFirstName());
		assertEquals("Ehrke", person1.getLastName());

		// Check that properties of second view instance are null (which proves that 'person2' does not refer to
		// 'person1''s virtual object:
		assertNull(person2.getFirstName());
		assertNull(person2.getLastName());

		// Manipulate second virtual person through second view instance:
		person2.setFirstName("Uli");
		assertEquals("Sven", person1.getFirstName());
		assertEquals("Ehrke", person1.getLastName());

		assertEquals("Uli", person2.getFirstName());
		assertNull(person2.getLastName());
	}

	/** Test that two view instances point to the same virtual object although two different interfaces are used if
	 * the MethodBasedDynaBeanProvider is used
	 */
	public void testMethodBasedDynaBeanProvider()
	{
		// Create first view instance (pv1):
		IPerson pv1 = (IPerson) dbpM.newBeanForInterface(IPerson.class);
		// Create second view instance (pv2):
		IPersonNamesView pv2 = (IPersonNamesView) dbpM.newBeanForInterface(IPersonNamesView.class);
		// Check that the two view instances are disctinct:
		assertNotSame(pv1, pv2);

		// Check that properties of view instances are null:
		assertNull(pv1.getFirstName());
		assertNull(pv1.getLastName());
		assertNull(pv2.getFirstName());
		assertNull(pv2.getLastName());

		// Manipulate virtual person through first view instance:
		pv1.setFirstName("Sven");
		pv1.setLastName("Ehrke");

		// Check that virtual person object pointed to by 'pv1' got changed:
		assertEquals("Sven", pv1.getFirstName());
		assertEquals("Ehrke", pv1.getLastName());

		// Check that 'person2' points to the same virtual person as 'pv1':
		assertEquals("Sven", pv2.getFirstName());
		assertEquals("Ehrke", pv2.getLastName());

		// Check that properties of view instances still point to same destination:
		assertSame(pv1.getFirstName(), pv2.getFirstName());
		assertSame(pv1.getLastName(), pv2.getLastName());

		// Manipulate virtual person through second view instance:
		pv2.setFirstName("Uli");
		assertEquals("Uli", pv1.getFirstName());
		assertEquals("Uli", pv2.getFirstName());

		// Check that properties of view instances still point to same destination:
		assertSame(pv1.getFirstName(), pv2.getFirstName());
		assertSame(pv1.getLastName(), pv2.getLastName());

	}

	public void testPerson1()
	{
		Person1 p1 = new Person1();
		assertNull(p1.getFirstName());
		assertNull(p1.getLastName());

		p1.setFirstName("Sven");
		p1.setLastName("Ehrke");
		assertEquals("Sven", p1.getFirstName());
		assertEquals("Ehrke", p1.getLastName());

		// Create first view instance (pv1):
		IPerson pv1 = (IPerson) dbpM.newBeanForInterface(IPerson.class);
		assertEquals("Sven", pv1.getFirstName());
		assertEquals("Ehrke", pv1.getLastName());

		IPersonNamesView pv2 = (IPersonNamesView) dbpM.newBeanForInterface(IPersonNamesView.class);
		assertEquals("Sven", pv2.getFirstName());
		assertEquals("Ehrke", pv2.getLastName());
	}

// --- Implementation ---

	private IDynaBeanProvider dbpCM = DynaBeanProvider.getClassAndMethodBasedDynaBeanProvider();
	private IDynaBeanProvider dbpOI = DynaBeanProvider.getObjectIdentityBasedDynaBeanProvider();
	private IDynaBeanProvider dbpM = DynaBeanProvider.getMethodBasedDynaBeanProvider();
}
