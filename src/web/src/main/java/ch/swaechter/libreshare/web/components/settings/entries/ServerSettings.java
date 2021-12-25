package ch.swaechter.libreshare.web.components.settings.entries;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Provide the general server settings.
 *
 * @author Simon Wächter
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerSettings {

    /**
     * Port of the server
     */
    private Integer port;

    /**
     * Secret of the server. Used for issuing and checking JWT tokens.
     */
    private String secret;

    /**
     * Create an empty server object.
     */
    public ServerSettings() {
    }

    /**
     * Get the server port.
     *
     * @return Server port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * Set the new server port.
     *
     * @param port New server port
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * Get the server secret.
     *
     * @return Server secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Set the new server secret.
     *
     * @param secret New server secret
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }
}
