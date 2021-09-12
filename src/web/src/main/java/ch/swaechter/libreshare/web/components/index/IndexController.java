package ch.swaechter.libreshare.web.components.index;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@Controller
@SecurityRequirement(name = "default")
@Secured(SecurityRule.IS_AUTHENTICATED)
public class IndexController {

    @Get(value = "/", consumes = MediaType.TEXT_PLAIN, produces = MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Welcome to Libreshare!";
    }
}
