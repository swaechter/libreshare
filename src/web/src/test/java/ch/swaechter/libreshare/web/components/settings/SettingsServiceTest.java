package ch.swaechter.libreshare.web.components.settings;

import ch.swaechter.libreshare.web.components.settings.entries.ComponentSettings;
import ch.swaechter.libreshare.web.components.settings.entries.DatabaseSettings;
import ch.swaechter.libreshare.web.components.settings.entries.ServerSettings;
import ch.swaechter.libreshare.web.components.settings.entries.ShareSettings;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test the read and write functionality of the settings service.
 *
 * @author Simon Wächter
 */
@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SettingsServiceTest {

    /**
     * Settings service used to read and write the configuration.
     */
    @Inject
    private SettingsService settingsService;

    /**
     * Test the read and write functionality of the settings service.
     */
    @Test
    public void testSettingsService() throws Exception {
        // Get the settings
        Settings settings = settingsService.getSettings();

        // Test the main settings
        assertEquals(1, settings.getVersion());

        // Test the server settings
        ServerSettings serverSettings = settings.getServer();
        assertEquals(-1, serverSettings.getPort());
        assertEquals("uXrhbU9KPyls3jlsjkl21jkls1234r57ru4dj42d34rjkly5jkyyj237349shkly5jklH4jEEBzx89jkl5jkl6jkljkl3jkljkl3j34jk5jjkljkl3jkljkl45jkl5jkljkl65jkljkljkljkljkl24jkl24jkljo77wv904kXmbvCZw2TEp", serverSettings.getSecret());

        // Test the database settings
        DatabaseSettings databaseSettings = settings.getDatabaseSettings();
        assertEquals("hostnameisnotused", databaseSettings.getHostname());
        assertEquals(-1, databaseSettings.getPort());
        assertEquals("databaseisnotused", databaseSettings.getDatabase());
        assertEquals("usernameisnotused", databaseSettings.getUsername());
        assertEquals("passwordisnotused", databaseSettings.getPassword());

        // Test the share component settings
        ComponentSettings componentSettings = settings.getComponentSettings();
        ShareSettings shareSettings = componentSettings.getShareSettings();
        assertEquals("../../src/web/src/test/resources/share", shareSettings.getPath());

        // Write the current version
        settingsService.writeSettings();
    }
}
