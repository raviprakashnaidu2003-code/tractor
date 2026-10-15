package com.tractor.aem.core.services;

import java.util.List;

public interface StoreFinderService {
    StoreRegionResult findStores(String region);
    final class StoreRegionResult {
        private final String region;
        private final String regionLabel;
        private final List<Store> stores;
        public StoreRegionResult(String region, String regionLabel, List<Store> stores) { this.region = region; this.regionLabel = regionLabel; this.stores = stores; }
        public String getRegion() { return region; }
        public String getRegionLabel() { return regionLabel; }
        public List<Store> getStores() { return stores; }
    }
    final class Store {
        private final String name;
        private final String address;
        public Store(String name, String address) { this.name = name; this.address = address; }
        public String getName() { return name; }
        public String getAddress() { return address; }
    }
}
