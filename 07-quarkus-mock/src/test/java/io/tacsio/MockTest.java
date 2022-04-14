package io.tacsio;

import javax.inject.Inject;

import io.tacsio.beans.BonjourMockableBean2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.tacsio.beans.MockableBean1;
import io.tacsio.beans.MockableBean2;

@QuarkusTest
public class MockTest {

    @Inject
    MockableBean1 mockableBean1;

    @Inject
    MockableBean2 mockableBean2;

    @BeforeAll
    public static void setup() {
        MockableBean1 mock = Mockito.mock(MockableBean1.class);
        Mockito.when(mock.greet("Claire")).thenReturn("A mock for Claire");
        QuarkusMock.installMockForType(mock, MockableBean1.class);

        /**
         * QuarkusMock can be used for any normal scoped CDI bean -
         * the most common of which are @ApplicationScoped and @RequestScoped.
         * This means that beans with @Singleton and @Dependent scope cannot be used with QuarkusMock.
         */
    }

    @Test
    public void beforeALl() {
        Assertions.assertEquals("A mock for Claire", mockableBean1.greet("Claire"));
        Assertions.assertEquals("Hello Claire", mockableBean2.greet("Claire"));
    }

    @Test
    public void perTestMock() {
        //Using BonjourMockableBean2 as a mock
        QuarkusMock.installMockForInstance(new BonjourMockableBean2(), mockableBean2);
        Assertions.assertEquals("A mock for Claire", mockableBean1.greet("Claire"));
        Assertions.assertEquals("Bonjour Claire", mockableBean2.greet("Claire"));
    }

}
