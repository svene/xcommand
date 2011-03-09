package org.xcommand.core

import spock.lang.Specification

public class DynaBeanProviderSpockTest extends Specification
{
	IDynaBeanProvider dbpCM = DynaBeanProvider.newThreadBasedDynabeanProvider(new ClassAndMethodKeyProvider());
	IDynaBeanProvider dbpOI = DynaBeanProvider.newThreadBasedDynabeanProvider(new ObjectIdentityKeyProvider());
	IDynaBeanProvider dbpM = DynaBeanProvider.newThreadBasedDynabeanProvider(new MethodKeyProvider());

	// TODO: instead of TCP use a normal object as storage (does this need reflection then?: java.beans.Introspector)
	def setup() {
		TCP.pushContext(new HashMap());
	}

	def	cleanup() {
		TCP.popContext();
	}

	def "testClassAndMethodBasedDynaBeanProvider0"() {
		def String s = null
		expect:
			dbpCM.newBeanForInterface(IPerson)
			!dbpCM.newBeanForInterface(IPerson).is(dbpCM.newBeanForInterface(IPerson))
	}
}