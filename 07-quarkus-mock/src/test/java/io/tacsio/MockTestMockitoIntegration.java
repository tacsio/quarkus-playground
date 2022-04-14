package io.tacsio;

import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.tacsio.beans.BonjourMockableBean2;
import io.tacsio.beans.MockableBean1;
import io.tacsio.beans.MockableBean2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;

@QuarkusTest
public class MockTestMockitoIntegration {

    /**
     * Since @InjectMock uses QuarkusMock under the hood, the same limitations apply to its use.
     * This means that beans with @Singleton and @Dependent scope cannot be used with QuarkusMock.
     *
     * Additionally, @InjectMock works like an injection point for the bean, so for it to work properly
     * when the target bean uses CDI qualifiers, those qualifiers also need to be added to the field.
     */
    @InjectMock
    MockableBean1 mockableBean1;

    @InjectMock
    MockableBean2 mockableBean2;

    @BeforeEach
    public void setup() {
        Mockito.when(mockableBean1.greet("Claire")).thenReturn("A mock for Claire");
    }

    @Test
    public void beforeALl() {
        Assertions.assertEquals("A mock for Claire", mockableBean1.greet("Claire"));
        Assertions.assertEquals(null, mockableBean2.greet("Claire"));
    }

    @Test
    public void perTestMock() {
        Mockito.when(mockableBean2.greet("Claire")).thenReturn("Bonjour Claire");

        Assertions.assertEquals("A mock for Claire", mockableBean1.greet("Claire"));
        Assertions.assertEquals("Bonjour Claire", mockableBean2.greet("Claire"));
    }

}
