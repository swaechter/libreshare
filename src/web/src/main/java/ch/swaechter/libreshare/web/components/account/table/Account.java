package ch.swaechter.libreshare.web.components.account.table;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import java.util.UUID;

/**
 * Represents an SQL account table.
 *
 * @author Simon Wächter
 */
@MappedEntity
public class Account {

    /**
     * ID of the account.
     */
    @Id
    private UUID id;

    /**
     * Username of the account.
     */
    private String username;

    /**
     * Email address of the account.
     */
    private String emailAddress;

    /**
     * Password hash of the account.
     */
    private String passwordHash;

    /**
     * Define a new account.
     *
     * @param id           ID of the account
     * @param username     Username of the account
     * @param emailAddress Email address of the account
     * @param passwordHash Password hash of the account
     */
    public Account(UUID id, String username, String emailAddress, String passwordHash) {
        this.id = id;
        this.username = username;
        this.emailAddress = emailAddress;
        this.passwordHash = passwordHash;
    }

    /**
     * Get the current account ID.
     *
     * @return Current account ID
     */
    public UUID getId() {
        return id;
    }

    /**
     * Set the new account ID.
     *
     * @param id New account ID
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Get the current account username.
     *
     * @return Current account username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the new account username.
     *
     * @param username New account username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the current account email address.
     *
     * @return Current account email address
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * Set the new account email address.
     *
     * @param emailAddress New account email address
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * Get the current account password hash.
     *
     * @return Current account password hash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Set the new account password hash.
     *
     * @param passwordHash New account password hash
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
