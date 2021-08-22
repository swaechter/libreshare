package ch.swaechter.libreshare.web;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
    info = @Info(
        title = "libreshare",
        version = "0.0.1"
    )
)
@SecurityScheme(name = "default",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT",
    paramName = "Authorization"
)
public class Application {

    public static void main(String[] arguments) {
        // Launch the application
        Micronaut.build(arguments).mainClass(Application.class).banner(false).start();
    }
}
