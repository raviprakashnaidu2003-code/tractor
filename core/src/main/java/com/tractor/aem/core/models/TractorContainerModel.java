package com.tractor.aem.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for the Tractor Container component.
 * Reads the author-selected layoutStyle and exposes authored child resources.
 */
@Model(
        adaptables = Resource.class,
        adapters = { TractorContainerModel.class },
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class TractorContainerModel {

    @Self
    private Resource resource;

    @ValueMapValue(name = "layoutStyle")
    private String layoutStyle;

    private int columns;
    private List<Resource> children = Collections.emptyList();

    @PostConstruct
    protected void init() {
        if ("grid-1".equals(layoutStyle)) {
            columns = 1;
        } else if ("grid-2".equals(layoutStyle)) {
            columns = 2;
        } else {
            columns = 3;
        }

        List<Resource> authoredChildren = new ArrayList<>();
        if (resource != null) {
            for (Resource child : resource.getChildren()) {
                // Only render nodes explicitly authored as components.
                if (child.getValueMap().get("sling:resourceType", String.class) != null) {
                    authoredChildren.add(child);
                }
            }
        }
        children = Collections.unmodifiableList(authoredChildren);
    }

    public int getColumns() {
        return columns;
    }

    public String getGridClass() {
        return "tractor-container--grid-" + columns;
    }

    public List<Resource> getChildren() {
        return children;
    }

    public boolean isEmpty() {
        return children.isEmpty();
    }

}
