package com.tractor.aem.core.models;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class TractorCardModel {
    private static final Logger LOG = LoggerFactory.getLogger(TractorCardModel.class);
    @ValueMapValue private String fragmentPath;
    @SlingObject private ResourceResolver resourceResolver;
    private ContentFragment fragment;

    private ContentFragment getFragment() {
        if (fragment != null || resourceResolver == null || fragmentPath == null || !fragmentPath.startsWith("/content/dam/tractor/models/")) return fragment;
        Resource resource = resourceResolver.getResource(fragmentPath);
        if (resource != null) fragment = resource.adaptTo(ContentFragment.class);
        if (fragment == null) LOG.debug("No Content Fragment found at {}", fragmentPath);
        return fragment;
    }
    private String value(String name) {
        ContentFragment cf = getFragment();
        if (cf == null || !cf.hasElement(name)) return "";
        ContentElement element = cf.getElement(name);
        return element == null || element.getContent() == null ? "" : element.getContent().trim();
    }
    public boolean isValid() { return !getModelName().isEmpty(); }
    public String getModelName() { return value("modelName"); }
    public String getShortDescription() { return value("shortDescription"); }
    public String getHorsepower() { return value("horsepower"); }
    public String getEngineType() { return value("engineType"); }
    public String getDetailPagePath() { String path = value("detailPagePath"); return path.startsWith("/") ? path : "/tractors.html"; }
    public String getImagePath() {
        String path = value("mainImage");
        if (path.isEmpty()) return "";
        // Content Reference fields may contain a DAM path; only allow repository paths.
        return path.startsWith("/content/dam/") ? path : "";
    }
}
