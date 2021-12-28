package ch.swaechter.libreshare.web.components.settings.entries;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Provide access to the database settings.
 *
 * @author Simon Wächter
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatabaseSettings {

    /**
     * Hostname of the PostgreSQL server.
     */
    private String hostname;

    /**
     * Port of the PostgreSQL server.
     */
    private Integer port;

    /**
     * Database used on the PostgreSQL server.
     */
    private String database;

    /**
     * Username used for the PostgreSQL authentication.
     */
    private String username;

    /**
     * Password used for the PostgreSQL authentication.
     */
    private String password;

    /**
     * Create a new empty database settings object.
     */
    public DatabaseSettings() {
    }

    /**
     * Get the database hostname.
     *
     * @return Database hostname
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * Set the new database hostname.
     *
     * @param hostname new database hostname
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * Get the database port.
     *
     * @return Database port
     */
    public Integer getPort() {
        return port;
    }

    /**
     * Set the new database port.
     *
     * @param port New database port
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    /**
     * Get the database name.
     *
     * @return Database name
     */
    public String getDatabase() {
        return database;
    }

    /**
     * Set the new database name.
     *
     * @param database New database name
     */
    public void setDatabase(String database) {
        this.database = database;
    }

    /**
     * Get the database username.
     *
     * @return Database username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the new database username.
     *
     * @param username New database username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the database password.
     *
     * @return Database password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the new database password.
     *
     * @param password New database password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
