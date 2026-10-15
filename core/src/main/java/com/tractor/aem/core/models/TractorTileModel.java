package com.tractor.aem.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/** Sling Model for an author-configured tractor tile. */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TractorTileModel {
    @ValueMapValue private String imagePath;
    @ValueMapValue private String altText;
    @ValueMapValue private String title;
    @ValueMapValue private String description;
    @ValueMapValue private String buttonText;
    @ValueMapValue private String buttonLink;

    public String getImagePath() {
        // Restrict the image to repository DAM paths; never render arbitrary schemes.
        return imagePath != null && imagePath.startsWith("/content/dam/") ? imagePath : "";
    }

    public String getAltText() {
        if (altText != null && !altText.trim().isEmpty()) return altText.trim();
        return getTitle();
    }

    public String getTitle() { return clean(title); }
    public String getDescription() { return clean(description); }
    public String getButtonText() { return clean(buttonText); }

    /**
     * Adds .html to simple internal /content page paths without an extension.
     * Only local paths and common safe URL schemes are accepted. Apply your site's
     * Link Manager / vanity URL policy before production use.
     */
    public String getButtonHref() {
        String link = clean(buttonLink);
        if (link.isEmpty()) return "";
        if (link.startsWith("/")) {
            if (link.startsWith("//") || link.startsWith("/apps/") || link.startsWith("/system/")) return "";
            if (link.startsWith("/content/") && !link.contains("?") && !link.contains("#")) {
                int slash = link.lastIndexOf('/');
                int dot = link.lastIndexOf('.');
                if (dot < slash) return link + ".html";
            }
            return link;
        }
        String lower = link.toLowerCase(java.util.Locale.ROOT);
        if (lower.startsWith("https://") || lower.startsWith("http://")
                || lower.startsWith("mailto:") || lower.startsWith("tel:")) {
            return link;
        }
        return "";
    }

    private String clean(String value) {
        return value == null ? "" : value.trim();
    }
}
