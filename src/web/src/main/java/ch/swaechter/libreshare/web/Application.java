package ch.swaechter.libreshare.web;

import ch.swaechter.libreshare.web.utils.globals.Globals;
import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import java.io.File;

@OpenAPIDefinition(
    info = @Info(
        title = "libreshare",
        version = Globals.LIBRESHARE_VERSION
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
        // Check the command arguments
        if (arguments.length != 1) {
            System.err.println("Usage:   java -jar web.jar <Path to settings JSON file>");
            System.err.println("Example: java -jar web.jar config/application.json");
            System.exit(1);
        }

        // Check if the settings file exist
        File settingsJsonFile = new File(arguments[0]);
        if (!settingsJsonFile.isFile()) {
            System.err.println("The file path " + settingsJsonFile.getAbsolutePath() + " is not a file");
            System.exit(1);
        }

        // Set the system property
        System.setProperty("micronaut.config.files", settingsJsonFile.getAbsolutePath());

        // Launch the application
        Micronaut.build(arguments).mainClass(Application.class).banner(false).start();
    }
}
