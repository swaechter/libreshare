package ch.swaechter.libreshare.web.components.settings;

import ch.swaechter.libreshare.web.components.settings.entries.ComponentSettings;
import ch.swaechter.libreshare.web.components.settings.entries.DatabaseSettings;
import ch.swaechter.libreshare.web.components.settings.entries.ServerSettings;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Provide the full settings.
 *
 * @author Simon Wächter
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Settings {

    /**
     * Version of the settings.
     */
    private Integer version;

    /**
     * Server settings.
     */
    private ServerSettings server;

    /**
     * Database settings.
     */
    private DatabaseSettings database;

    /**
     * Component settings that provide the component specific settings.
     */
    private ComponentSettings components;

    /**
     * Create an empty settings object.
     */
    public Settings() {
    }

    /**
     * Get the settings version.
     *
     * @return Settings version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * Set the new settings version
     *
     * @param version New settings version
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * Get the server settings.
     *
     * @return Server settings
     */
    public ServerSettings getServer() {
        return server;
    }

    /**
     * Set the new server settings.
     *
     * @param server New server settings
     */
    public void setServer(ServerSettings server) {
        this.server = server;
    }

    /**
     * Get the database settings.
     *
     * @return Database settings
     */
    public DatabaseSettings getDatabaseSettings() {
        return database;
    }

    /**
     * Set the new database settings.
     *
     * @param database New database settings
     */
    public void setDatabaseSettings(DatabaseSettings database) {
        this.database = database;
    }

    /**
     * Get the component settings.
     *
     * @return Component settings
     */
    public ComponentSettings getComponentSettings() {
        return components;
    }

    /**
     * Set the component settings.
     *
     * @param components New component settings
     */
    public void setComponentSettings(ComponentSettings components) {
        this.components = components;
    }
}
