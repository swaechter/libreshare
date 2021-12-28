package ch.swaechter.libreshare.web.components.settings.entries;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Provide access to the component specific settings.
 *
 * @author Simon Wächter
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ComponentSettings {

    /**
     * Share settings.
     */
    private ShareSettings share;

    /**
     * Create an empty component settings object.
     */
    public ComponentSettings() {
    }

    /**
     * Get the share settings.
     *
     * @return Share settings
     */
    public ShareSettings getShareSettings() {
        return share;
    }

    /**
     * Set the new share settings.
     *
     * @param share New share settings
     */
    public void setShareSettings(ShareSettings share) {
        this.share = share;
    }
}
