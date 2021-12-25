package ch.swaechter.libreshare.web.components.settings;

import ch.swaechter.libreshare.web.configuration.exceptionhandling.ServerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Read the JSON settings file and inject it into the Micronaut context. Also provide the ability to update the
 * settings file.
 *
 * @author Simon Wächter
 */
@Factory
@Context
public class SettingsService {

    /**
     * Object mapper used to read and write the JSON configuration.
     */
    private final ObjectMapper objectMapper;

    /**
     * Path of the JSON settings file.
     */
    private final File settingsFile;

    /**
     * Current in-memory settings.
     */
    private final Settings settings;

    /**
     * Create a new settings service that loads the JSON settings file and injects it into the Micronaut context.
     *
     * @param objectMapper Object mapper used to read and write the JSON configuration
     * @param path         Path of the JSON settings file
     * @throws ServerException Exception in case of an IO or serialization issue
     */
    public SettingsService(ObjectMapper objectMapper, @Value("${micronaut.config.files}") String path) throws ServerException {
        this.objectMapper = objectMapper;
        this.settingsFile = new File(path);
        this.settings = readSettings();
    }

    /**
     * Get the current in-memory settings.
     *
     * @return Current in-memory settings
     */
    public Settings getSettings() {
        return settings;
    }

    /**
     * Read the settings from the JSON file.
     *
     * @return Read settings
     * @throws ServerException Exception in case of an IO or serialization issue
     */
    private Settings readSettings() throws ServerException {
        try {
            return objectMapper.readValue(settingsFile, Settings.class);
        } catch (Exception exception) {
            throw new ServerException("Unable to read the settings: " + exception.getMessage(), exception);
        }
    }

    /**
     * Write the current in-memory settings to the JSON file.
     *
     * @throws ServerException Exception in case of an IO or serialization issue
     */
    public void writeSettings() throws ServerException {
        try {
            File temporaryFile = new File(settingsFile.getAbsolutePath() + ".tmp");
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(temporaryFile, settings);
            Files.move(temporaryFile.toPath(), settingsFile.toPath(), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (Exception exception) {
            throw new ServerException("Unable to write the settings: " + exception.getMessage(), exception);
        }
    }
}
