package com.tractor.aem.core.servlets;

import com.tractor.aem.core.services.StoreFinderService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.osgi.service.component.propertytypes.ServiceVendor;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class, property = {
    Constants.SERVICE_DESCRIPTION + "=iTransparity Store Finder JSON Servlet",
    "sling.servlet.methods=GET",
    "sling.servlet.paths=/bin/itransparity/stores.json",
    "sling.servlet.extensions=json"
})
@ServiceDescription("Returns dealership stores for a selected region")
@ServiceVendor("iTransparity")
public class StoreFinderServlet extends SlingSafeMethodsServlet {
    private static final long serialVersionUID = 1L;
    @Reference
    private transient StoreFinderService storeFinderService;

    @Override protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        String region = request.getParameter("region");
        StoreFinderService.StoreRegionResult result = storeFinderService.findStores(region);
        JSONObject payload = new JSONObject();
        JSONArray stores = new JSONArray();
        try {
            payload.put("region", result.getRegion());
            payload.put("regionLabel", result.getRegionLabel());
            payload.put("count", result.getStores().size());
            for (StoreFinderService.Store store : result.getStores()) {
                JSONObject item = new JSONObject();
                item.put("name", store.getName());
                item.put("address", store.getAddress());
                stores.put(item);
            }
            payload.put("stores", stores);
        } catch (org.apache.sling.commons.json.JSONException e) {
            throw new ServletException("Could not serialize store data", e);
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        response.setHeader("Pragma", "no-cache");
        response.getWriter().write(payload.toString());
    }
}
