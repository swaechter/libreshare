package ch.swaechter.libreshare.web.components.account;

import ch.swaechter.libreshare.web.components.account.dto.ChangePasswordDto;
import ch.swaechter.libreshare.web.components.account.dto.CreateAccountDto;
import ch.swaechter.libreshare.web.components.account.dto.ReadAccountDto;
import ch.swaechter.libreshare.web.components.account.dto.UpdateAccountDto;
import ch.swaechter.libreshare.web.configuration.exceptionhandling.ServerException;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.validation.Validated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/**
 * Manage all accounts.
 *
 * @author Simon Wächter
 */
@Controller("/api")
@SecurityRequirement(name = "default")
@Secured(SecurityRule.IS_AUTHENTICATED)
@Validated
public class AccountController {

    /**
     * Account service to manage the accounts.
     */
    private final AccountService accountService;

    /**
     * Create a new account controller.
     *
     * @param accountService Account service to manage the accounts
     */
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * Create a new account.
     *
     * @param createAccountDto Account to create
     * @return Created account
     * @throws ServerException Exception in case of invalid data
     */
    @Post("/accounts")
    @Tag(name = "Accounts")
    @Operation(operationId = "createAccount")
    @ApiResponse(responseCode = "200", description = "Created account")
    @ApiResponse(responseCode = "401", description = "Not authenticated")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ReadAccountDto createAccount(@Body @Valid CreateAccountDto createAccountDto) throws ServerException {
        return accountService.createAccount(createAccountDto);
    }

    /**
     * Get all accounts.
     *
     * @return All accounts
     */
    @Get("/accounts")
    @Tag(name = "Accounts")
    @Operation(operationId = "getAccounts")
    @ApiResponse(responseCode = "200", description = "All accounts")
    @ApiResponse(responseCode = "401", description = "Not authenticated")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public List<ReadAccountDto> getAccounts() {
        return accountService.getAccounts();
    }

    /**
     * Get a specific account by ID.
     *
     * @param id ID of the account
     * @return Specific account
     * @throws ServerException Exception in case of invalid data
     */
    @Get("/accounts/{id}")
    @Tag(name = "Accounts")
    @Operation(operationId = "getAccountById")
    @ApiResponse(responseCode = "200", description = "Specific account")
    @ApiResponse(responseCode = "401", description = "Not authenticated")
    @ApiResponse(responseCode = "500", description = "Internal server error or account not found")
    public ReadAccountDto getAccountById(@PathVariable UUID id) throws ServerException {
        return accountService.getAccountById(id);
    }

    /**
     * Update an existing account by ID.
     *
     * @param id               Account ID to update
     * @param updateAccountDto Updated account
     * @throws ServerException Exception in case of invalid data
     */
    @Put("/accounts/{id}")
    @Tag(name = "Accounts")
    @Operation(operationId = "updateAccountById")
    @ApiResponse(responseCode = "200", description = "Account updated")
    @ApiResponse(responseCode = "401", description = "Not authenticated")
    @ApiResponse(responseCode = "500", description = "Internal server error or account not found")
    public void updateAccountById(@PathVariable UUID id, @Body @Valid UpdateAccountDto updateAccountDto) throws ServerException {
        accountService.updateAccount(id, updateAccountDto);
    }

    /**
     * Change the password of an existing account by ID.
     *
     * @param id                Account ID to change the password
     * @param changePasswordDto New password
     * @throws ServerException Exception in case of invalid data
     */
    @Put("/accounts/{id}/password")
    @Tag(name = "Accounts")
    @Operation(operationId = "changeAccountPasswordById")
    @ApiResponse(responseCode = "200", description = "Account password changed")
    @ApiResponse(responseCode = "401", description = "Not authenticated")
    @ApiResponse(responseCode = "500", description = "Internal server error or account not found")
    public void changeAccountPasswordById(@PathVariable UUID id, @Body @Valid ChangePasswordDto changePasswordDto) throws ServerException {
        accountService.changeAccountPassword(id, changePasswordDto);
    }

    /**
     * Delete an existing account by ID.
     *
     * @param id Account ID to delete
     * @throws ServerException Exception in case of invalid data
     */
    @Delete("/accounts/{id}")
    @Tag(name = "Accounts")
    @Operation(operationId = "deleteAccountById")
    @ApiResponse(responseCode = "200", description = "Account deleted")
    @ApiResponse(responseCode = "401", description = "Not authenticated")
    @ApiResponse(responseCode = "500", description = "Internal server error or account not found")
    public void deleteAccountById(@PathVariable UUID id) throws ServerException {
        accountService.deleteAccountById(id);
    }
}
