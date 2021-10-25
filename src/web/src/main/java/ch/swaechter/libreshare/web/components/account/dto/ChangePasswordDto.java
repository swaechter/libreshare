package ch.swaechter.libreshare.web.components.account.dto;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

@Introspected
@Schema(description = "Schema to change the password of an account")
public class ChangePasswordDto {

    @NotBlank
    @Schema(description = "New plaintext password of the account")
    private String plaintextPassword;

    public ChangePasswordDto() {
    }

    public String getPlaintextPassword() {
        return plaintextPassword;
    }

    public void setPlaintextPassword(String plaintextPassword) {
        this.plaintextPassword = plaintextPassword;
    }
}
