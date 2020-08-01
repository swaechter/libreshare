package ch.swaechter.libreshare.web.components.accounts;

import java.util.Map;
import java.util.UUID;

public class Account {

    private UUID id;

    private String username;

    private String emailAddress;

    private String passwordHash;

    private Map<String, String> functionalPermissions;

    public Account() {
    }

    public Account(UUID id, String username, String emailAddress, String passwordHash, Map<String, String> functionalPermissions) {
        this.id = id;
        this.username = username;
        this.emailAddress = emailAddress;
        this.passwordHash = passwordHash;
        this.functionalPermissions = functionalPermissions;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Map<String, String> getFunctionalPermissions() {
        return functionalPermissions;
    }

    public void setFunctionalPermissions(Map<String, String> functionalPermissions) {
        this.functionalPermissions = functionalPermissions;
    }
}
