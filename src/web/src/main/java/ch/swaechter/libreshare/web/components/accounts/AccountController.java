package ch.swaechter.libreshare.web.components.accounts;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.ArrayList;
import java.util.List;

@Controller("/api")
@SecurityRequirement(name = "default")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class AccountController {

    @Get("/accounts")
    public HttpResponse<List<Account>> getAccounts() {
        return HttpResponse.ok(new ArrayList<>());
    }
}
