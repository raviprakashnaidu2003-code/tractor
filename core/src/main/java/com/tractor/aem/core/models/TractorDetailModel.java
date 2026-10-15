
package com.tractor.aem.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class TractorDetailModel {

    @ValueMapValue
    private String fragmentPath;

    @SlingObject
    private ResourceResolver resourceResolver;

    private ValueMap getFragmentProperties() {
        if (fragmentPath == null
                || !fragmentPath.startsWith("/content/dam/tractor/models/")) {
            return ValueMap.EMPTY;
        }

        Resource fragmentResource =
                resourceResolver != null
                        ? resourceResolver.getResource(fragmentPath)
                        : null;

        if (fragmentResource == null) {
            return ValueMap.EMPTY;
        }

        Resource masterResource = fragmentResource.getChild(
                "jcr:content/data/master"
        );

        return masterResource != null
                ? masterResource.getValueMap()
                : ValueMap.EMPTY;
    }

    private String getValue(String propertyName) {
        return getFragmentProperties().get(propertyName, "");
    }

    public boolean isValid() {
        return !getModelName().isEmpty();
    }

    public String getModelName() {
        return getValue("modelName");
    }

    public String getHorsePower() {
        return getValue("horsePower");
    }

    public String getEngineType() {
        return getValue("engineType");
    }

    public String getWeightkg() {
        return getValue("weightkg");
    }
    public String getShortDescription() {
        return getValue("shortDescription");
    }
    public String getFullDescription() {
        return getValue("fullDescription");
    }
    public String getPagePath() {
        return getValue("pagePath");
    }
    public String getImagePath() {
        String path = getValue("mainImage");

        return path.startsWith("/content/dam/") ? path : "";
    }
}
