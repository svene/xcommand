package org.xcommand.core;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

class DynaBeanProviderTest {
    IDynaBeanProvider dbpM = DynaBeanProvider.newThreadMethodInstance();

    @BeforeEach
    void beforeEach() {
        TCP.pushContext(new HashMap<>());
    }

    @AfterEach
    void afterEach() {
        TCP.popContext();
    }

    @Test
    void verify_that_class_and_method_based_DynaBeanProvider_CM_DBP_produces_new_instances_for_each_call() {
        IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
        assertThat(dbp.newBeanForInterface(IPerson.class)).isNotNull();
        assertThat(dbp.newBeanForInterface(IPerson.class)).isNotSameAs(dbp.newBeanForInterface(IPerson.class));

        IPerson person = dbp.newBeanForInterface(IPerson.class);
        assertThat(person.getFirstName()).isNull();
        assertThat(person.getLastName()).isNull();

        person.setFirstName("Sven");
        person.setLastName("Ehrke");
        Date d = new Date();
        person.setBirthDate(d);

        assertThat(person.getFirstName()).isEqualTo("Sven");
        assertThat(person.getLastName()).isEqualTo("Ehrke");
        assertThat(person.getBirthDate()).isEqualTo(d);
    }

    @Test
    void
            verify_that_object_identity_based_DynaBeanProvider_OID_DBP_produces_new_instances_for_each_call_and_that_getters_setters_work() {
        IDynaBeanProvider dbp = DynaBeanProvider.newThreadObjectIdentityInstance();
        IPerson person = dbp.newBeanForInterface(IPerson.class);
        assertThat(person.getFirstName()).isNull();
        assertThat(person.getLastName()).isNull();
        assertThat(person.getLastName()).isNull();
        assertThat(dbp.newBeanForInterface(IPerson.class)).isNotSameAs(dbp.newBeanForInterface(IPerson.class));

        person.setFirstName("Sven");
        person.setLastName("Ehrke");
        Date d = new Date();
        person.setBirthDate(d);

        assertThat(person.getFirstName()).isEqualTo("Sven");
        assertThat(person.getLastName()).isEqualTo("Ehrke");
        assertThat(person.getBirthDate()).isEqualTo(d);
    }

    @Test
    void
            verify_that_method_based_DynaBeanProvider_M_DBP_produces_new_instances_for_each_call_and_that_getters_setters_work() {
        IDynaBeanProvider dbp = DynaBeanProvider.newThreadMethodInstance();
        IPerson person = dbp.newBeanForInterface(IPerson.class);
        assertThat(person.getFirstName()).isNull();
        assertThat(person.getLastName()).isNull();
        assertThat(dbp.newBeanForInterface(IPerson.class)).isNotSameAs(dbp.newBeanForInterface(IPerson.class));
        assertThat(dbp.newBeanForInterface(IPerson.class)).isNotSameAs(dbp.newBeanForInterface(IPersonNamesView.class));

        person.setFirstName("Sven");
        person.setLastName("Ehrke");
        Date d = new Date();
        person.setBirthDate(d);

        assertThat(person.getFirstName()).isEqualTo("Sven");
        assertThat(person.getLastName()).isEqualTo("Ehrke");
        assertThat(person.getBirthDate()).isEqualTo(d);
    }

    @Test
    void verify_that_two_view_instances_point_to_the_same_virtual_object_if_CM_DBP_is_used() {
        IDynaBeanProvider dbp = DynaBeanProvider.newThreadClassMethodInstance();
        // Create first view instance (person1):
        IPerson person1 = dbp.newBeanForInterface(IPerson.class);
        // Create second view instance (person2):
        IPerson person2 = dbp.newBeanForInterface(IPerson.class);
        // Manipulate virtual person through first view instance:
        person1.setFirstName("Sven");
        person1.setLastName("Ehrke");
        // Verify that 'person2' points to the same virtual person as 'person1':
        assertThat(person2.getFirstName()).isEqualTo("Sven");
        assertThat(person2.getLastName()).isEqualTo("Ehrke");

        // Verify that properties of view instances still point to same destination:
        assertThat(person1.getFirstName()).isSameAs(person2.getFirstName());
        assertThat(person1.getLastName()).isSameAs(person2.getLastName());

        // Manipulate virtual person through second view instance:
        person2.setFirstName("Uli");

        assertThat(person1.getFirstName()).isEqualTo("Uli");

        // Verify that properties of view instances still point to same destination:
        assertThat(person1.getFirstName()).isSameAs(person2.getFirstName());
        assertThat(person1.getLastName()).isSameAs(person2.getLastName());
    }

    @Test
    void verify_that_two_view_instances_point_to_distinct_virtual_objects_if_OID_DBP_is_used() {
        IDynaBeanProvider dbp = DynaBeanProvider.newThreadObjectIdentityInstance();
        IPerson person1 = dbp.newBeanForInterface(IPerson.class);
        IPerson person2 = dbp.newBeanForInterface(IPerson.class);

        // Manipulate virtual person through first view instance:
        person1.setFirstName("Sven");
        person1.setLastName("Ehrke");

        // Check that properties of second view instance are still null (which proves that 'person2' does not refer to
        // 'person1''s virtual object:
        assertThat(person2.getFirstName()).isNull();
        assertThat(person2.getLastName()).isNull();

        person2.setFirstName("Uli");

        assertThat(person2.getFirstName()).isEqualTo("Uli");
        assertThat(person1.getFirstName()).isEqualTo("Sven");
    }

    @Test
    void
            verify_that_two_view_instances_point_to_the_same_virtual_object_if_M_DBP_is_used_even_if_used_interfaces_are_not_the_same() {
        IDynaBeanProvider dbp = DynaBeanProvider.newThreadMethodInstance();
        IPerson pv1 = dbp.newBeanForInterface(IPerson.class);
        IPersonNamesView pv2 = dbp.newBeanForInterface(IPersonNamesView.class);

        // Manipulate virtual person through first view instance:
        pv1.setFirstName("Sven");
        pv1.setLastName("Ehrke");

        // Verify that 'pv2' points to the same virtual person as 'pv1':
        assertThat(pv2.getFirstName()).isEqualTo("Sven");
        assertThat(pv2.getLastName()).isEqualTo("Ehrke");

        // Check that properties of view instances still point to same destination:
        assertThat(pv1.getFirstName()).isSameAs(pv2.getFirstName());
        assertThat(pv1.getLastName()).isSameAs(pv2.getLastName());

        // Manipulate virtual person through second view instance:
        pv2.setFirstName("Uli");

        assertThat(pv1.getFirstName()).isEqualTo("Uli");

        // Verify that properties of view instances still point to same destination:
        assertThat(pv1.getFirstName()).isSameAs(pv2.getFirstName());
        assertThat(pv1.getLastName()).isSameAs(pv2.getLastName());
    }

    @Test
    void demonstrate_Spring_BeanWrapper() {
        DelegatingPerson p1 = new DelegatingPerson(dbpM.newBeanForInterface(IPerson.class));
        BeanWrapper bw = new BeanWrapperImpl(p1);
        PropertyDescriptor[] descriptors = bw.getPropertyDescriptors();

        assertThat(descriptors.length).isEqualTo(4);
        assertThat(descriptors[0].getName()).isEqualTo("birthDate");
        assertThat(descriptors[1].getName()).isEqualTo("class");
        assertThat(descriptors[2].getName()).isEqualTo("firstName");
        assertThat(descriptors[3].getName()).isEqualTo("lastName");
    }

    @Test
    void verify_that_object_backed_bean_provider_works() {
        IPerson p = new Person();
        BeanHoldingBeanAccessor ba = new BeanHoldingBeanAccessor(p);
        IDynaBeanProvider dbp = DynaBeanProvider.newDynaBeanProvider(DynaBeanProvider.newDynaBeanInvocationHandler(ba));
        IPerson pv = dbp.newBeanForInterface(IPerson.class);

        assertThat(pv.getFirstName()).isNull();
        assertThat(pv.getLastName()).isNull();
        assertThat(pv.getBirthDate()).isNull();

        // Modify real Person 'p':
        p.setFirstName("Sven");

        assertThat(p.getFirstName()).isEqualTo("Sven");
        assertThat(pv.getFirstName()).isEqualTo("Sven");

        // Modify through view 'pv':
        pv.setLastName("Ehrke");

        // Check modification on view 'pv':
        assertThat(pv.getLastName()).isEqualTo("Ehrke");
        // Check modification on 'p':
        assertThat(p.getLastName()).isEqualTo("Ehrke");
    }
}
