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

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ApplicationTest implements TestPropertyProvider {

    @Inject
    private EmbeddedApplication<?> application;

    @NonNull
    @Override
    public Map<String, String> getProperties() {
        Map<String, String> properties = new LinkedHashMap<>();
        properties.put("datasources.default.url", "jdbc:tc:postgresql:13:///postgres");
        properties.put("datasources.default.driverClassName", "org.testcontainers.jdbc.ContainerDatabaseDriver");
        return properties;
    }

    @Test
    void testItWorks() {
        assertTrue(application.isRunning());
    }
}
