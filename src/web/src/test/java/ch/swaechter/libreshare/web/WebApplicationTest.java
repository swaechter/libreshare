package ch.swaechter.libreshare.web;

import io.micronaut.context.ApplicationContext;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.runtime.server.EmbeddedServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class WebApplicationTest {

    @Container
    private final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:12").withDatabaseName("libreshare").withUsername("libreshare").withPassword("123456aA");

    @Test
    void testAgent() {
        // Manually start the Micronaut application after all test containers are up and running
        EmbeddedServer embeddedServer = ApplicationContext.run(EmbeddedServer.class, CollectionUtils.mapOf(
            "datasources.default.url", postgreSQLContainer.getJdbcUrl(),
            "datasources.default.username", "libreshare",
            "datasources.default.password", "123456aA"
        ));
        Assertions.assertTrue(embeddedServer.isRunning());
    }
}
