package ch.swaechter.libreshare.web.components.settings;

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
     * Version of the configuration.
     */
    private Integer version;

    /**
     * Server configuration.
     */
    private ServerSettings server;

    /**
     * Create an empty configuration.
     */
    public Settings() {
    }

    /**
     * Get the configuration version.
     *
     * @return Configuration version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * Set the new configuration version
     *
     * @param version New configuration version
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
}
