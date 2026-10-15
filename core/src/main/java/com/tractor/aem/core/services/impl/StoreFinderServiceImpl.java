package com.tractor.aem.core.services.impl;

import com.tractor.aem.core.services.StoreFinderService;
import org.osgi.service.component.annotations.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

@Component(service = StoreFinderService.class)
public class StoreFinderServiceImpl implements StoreFinderService {
    @Override public StoreRegionResult findStores(String requestedRegion) {
        String region = normalize(requestedRegion);
        switch (region) {
            case "london":
                return new StoreRegionResult(region, "London, UK", Arrays.asList(
                    new Store("Thames Tractors", "Address to be verified — London, UK"),
                    new Store("CityFarm Equipment", "Address to be verified — London, UK"),
                    new Store("Kensington Agri", "Address to be verified — London, UK"),
                    new Store("Docklands Motors", "Address to be verified — London, UK"),
                    new Store("Greenwich Farm Co.", "Address to be verified — London, UK")));
            case "kenya":
                return new StoreRegionResult(region, "Nairobi, Kenya", Arrays.asList(
                    new Store("Safari Agri", "Address to be verified — Nairobi, Kenya"),
                    new Store("Nairobi Farm Hub", "Address to be verified — Nairobi, Kenya"),
                    new Store("Rift Valley Tractors", "Address to be verified — Nairobi, Kenya"),
                    new Store("Mombasa Equipment Ltd.", "Address to be verified — Nairobi, Kenya")));
            case "delhi":
                return new StoreRegionResult(region, "Delhi, India", Collections.<Store>emptyList());
            default:
                return new StoreRegionResult("unknown", "your location", Collections.<Store>emptyList());
        }
    }
    private String normalize(String input) {
        String value = input == null ? "unknown" : input.trim().toLowerCase(Locale.ROOT);
        if ("london".equals(value) || "uk".equals(value) || "gb".equals(value)) return "london";
        if ("kenya".equals(value) || "nairobi".equals(value) || "ke".equals(value)) return "kenya";
        if ("delhi".equals(value) || "india".equals(value) || "in".equals(value)) return "delhi";
        return "unknown";
    }
}
