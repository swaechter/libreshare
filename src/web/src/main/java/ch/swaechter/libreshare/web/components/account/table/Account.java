package ch.swaechter.libreshare.web.components.account.table;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import java.util.UUID;

@MappedEntity
public class Account {

    @Id
    private UUID id;

    private String userName;

    private String emailAddress;

    private String passwordHash;

    public Account() {
    }

    public Account(UUID id, String userName, String emailAddress, String passwordHash) {
        this.id = id;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.passwordHash = passwordHash;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
