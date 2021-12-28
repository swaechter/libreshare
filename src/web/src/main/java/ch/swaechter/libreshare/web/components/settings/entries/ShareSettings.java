package ch.swaechter.libreshare.web.components.settings.entries;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Provide the file share related settings.
 *
 * @author Simon Wächter
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShareSettings {

    /**
     * Path where the file share is located.
     */
    private String path;

    /**
     * Create an empty share settings object.
     */
    public ShareSettings() {
    }

    /**
     * Get the path to the file share.
     *
     * @return File share path
     */
    public String getPath() {
        return path;
    }

    /**
     * Set the new file share path.
     *
     * @param path New file share path
     */
    public void setPath(String path) {
        this.path = path;
    }
}
