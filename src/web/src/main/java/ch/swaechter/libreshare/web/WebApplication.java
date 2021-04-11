package ch.swaechter.libreshare.web;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
    info = @Info(
        title = "libreshare",
        version = "0.0.1",
        description = "Documentation for the Libreshare web service",
        license = @License(name = "MIT"),
        contact = @Contact(url = "https://github.com/swaechter/libreshare", name = "Simon Waechter", email = "waechter.simon@gmail.com")
    )
)
@SecurityScheme(name = "default",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER, paramName = "Authorization"
)
public class WebApplication {

    public static void main(String[] arguments) {
        Micronaut.build(arguments).mainClass(WebApplication.class).banner(false).start();
    }
}
