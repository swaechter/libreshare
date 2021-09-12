package ch.swaechter.libreshare.web;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration test for the full application. Internally a PostgreSQL testcontainer is started.
 *
 * @author Simon Wächter
 */
@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicationTest implements TestPropertyProvider {

    /**
     * Application that is started.
     */
    @Inject
    private EmbeddedApplication<?> application;

    /**
     * Provide a property configuration that contains the testcontainer JDBC configuration.
     *
     * @return Property configuration
     */
    @NonNull
    @Override
    public Map<String, String> getProperties() {
        Map<String, String> properties = new LinkedHashMap<>();
        properties.put("datasources.default.url", "jdbc:tc:postgresql:13:///postgres");
        properties.put("datasources.default.driverClassName", "org.testcontainers.jdbc.ContainerDatabaseDriver");
        return properties;
    }

    /**
     * Regular test to check if the application can be started.
     */
    @Test
    void testItWorks() {
        assertTrue(application.isRunning());
    }
}
