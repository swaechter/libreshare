package ch.swaechter.libreshare.web.components.account.dto;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Introspected
@Schema(description = "Schema to create a new account")
public class CreateAccountDto {

    @NotBlank
    @Schema(description = "Unique username of the account that is used for logging in")
    private String username;

    @Email
    @NotBlank
    @Schema(description = "Email of the address. Multiple accounts can share the same email address")
    private String emailAddress;

    @NotBlank
    @Schema(description = "New plaintext password")
    private String plaintextPassword;

    public CreateAccountDto() {
    }

    public CreateAccountDto(String username, String emailAddress, String plaintextPassword) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.plaintextPassword = plaintextPassword;
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

    public String getPlaintextPassword() {
        return plaintextPassword;
    }

    public void setPlaintextPassword(String plaintextPassword) {
        this.plaintextPassword = plaintextPassword;
    }
}
