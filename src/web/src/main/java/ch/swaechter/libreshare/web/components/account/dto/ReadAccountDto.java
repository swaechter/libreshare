package ch.swaechter.libreshare.web.components.account.dto;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Introspected
@Schema(description = "Schema that represents an existing account")
public class ReadAccountDto {

    @Schema(description = "Unique ID of the account")
    private UUID id;

    @Schema(description = "Unique user name of the account that is used for logging in")
    private String username;

    @Schema(description = "Email of the address. Multiple accounts can share the same email address")
    private String emailAddress;

    public ReadAccountDto() {
    }

    public ReadAccountDto(UUID id, String username, String emailAddress) {
        this.id = id;
        this.username = username;
        this.emailAddress = emailAddress;
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
}
