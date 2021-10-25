package ch.swaechter.libreshare.web.components.account.dto;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Introspected
@Schema(description = "Schema to update an existing account")
public class UpdateAccountDto {

    @NotBlank
    @Schema(description = "Unique user name of the account that is used for logging in")
    private String username;

    @Email
    @NotBlank
    @Schema(description = "Email of the address. Multiple accounts can share the same email address")
    private String emailAddress;

    public UpdateAccountDto() {
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
