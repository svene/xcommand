package org.xcommand.core;

import java.util.HashMap;
import java.beans.PropertyDescriptor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.BeanWrapper;

import static org.junit.Assert.*;

public class DynaBeanProviderTester
{
	// TODO: instead of TCP use a normal object as storage (does this need reflection then?: java.beans.Introspector)
	@Before
	public void initializeContext() throws Exception
	{
		TCP.pushContext(new HashMap());
	}

	@After
	public void tearDownContext() throws Exception
	{
		TCP.popContext();
	}

	/** Test that two view instances point to the same virtual object although two different interfaces are used if
	 * the MethodBasedDynaBeanProvider is used
	 */
	@Test public void testMethodBasedDynaBeanProvider()
	{
		// Create first view instance (pv1):
		IPerson pv1 = dbpM.newBeanForInterface(IPerson.class);
		// Create second view instance (pv2):
		IPersonNamesView pv2 = dbpM.newBeanForInterface(IPersonNamesView.class);
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

	@Test public void testPerson1()
	{
		DelegatingPerson p1 = new DelegatingPerson(dbpM.newBeanForInterface(IPerson.class));
		assertNull(p1.getFirstName());
		assertNull(p1.getLastName());

		p1.setFirstName("Sven");
		p1.setLastName("Ehrke");
		assertEquals("Sven", p1.getFirstName());
		assertEquals("Ehrke", p1.getLastName());

		// Create first view instance (pv1):
		IPerson pv1 = dbpM.newBeanForInterface(IPerson.class);
		assertEquals("Sven", pv1.getFirstName());
		assertEquals("Ehrke", pv1.getLastName());

		IPersonNamesView pv2 = dbpM.newBeanForInterface(IPersonNamesView.class);
		assertEquals("Sven", pv2.getFirstName());
		assertEquals("Ehrke", pv2.getLastName());
	}
	@Test public void test21()
	{
		DelegatingPerson p1 = new DelegatingPerson(dbpM.newBeanForInterface(IPerson.class));
		BeanWrapper bw = new BeanWrapperImpl(p1);
		PropertyDescriptor[] descriptors = bw.getPropertyDescriptors();
		assertEquals(4, descriptors.length);
		assertEquals("birthDate", descriptors[0].getName());
		assertEquals("class", descriptors[1].getName());
		assertEquals("firstName", descriptors[2].getName());
		assertEquals("lastName", descriptors[3].getName());
	}
	@Test public void test22()
	{
		IPerson p = new Person();
		BeanHoldingBeanAccessor ba = new BeanHoldingBeanAccessor(p);
		IDynaBeanProvider dbp = DynaBeanProvider.newDynabeanProvider(ba);
		IPerson pv1 = dbp.newBeanForInterface(IPerson.class);
		assertNull(pv1.getFirstName());
		assertNull(pv1.getLastName());
		assertNull(pv1.getBirthDate());
		// Modify real Person 'p':
		p.setFirstName("Sven");
		// Check modification on 'p':
		assertEquals("Sven", p.getFirstName());
		// Check modification on view 'pv1':
		assertEquals("Sven", pv1.getFirstName());

		// Modify through view 'pv1':
		pv1.setLastName("Ehrke");
		// Check modification on view 'pv1':
		assertEquals("Ehrke", pv1.getLastName());
		// Check modification on 'p':
		assertEquals("Ehrke", p.getLastName());
	}

// --- Implementation ---

	private IDynaBeanProvider dbpCM = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	private IDynaBeanProvider dbpOI = DynaBeanProvider.newThreadBasedDynabeanProvider(new ObjectIdentityKeyProvider());
	private IDynaBeanProvider dbpM = DynaBeanProvider.newThreadBasedDynabeanProvider(new MethodKeyProvider());
}
