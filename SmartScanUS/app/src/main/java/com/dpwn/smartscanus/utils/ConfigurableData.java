package com.dpwn.smartscanus.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * This class will hold maps of configurable data.
 *
 * Created by cekangak on 7/28/2015.
 */
public class ConfigurableData {

    public static Map<String, String> getFacilityLocations(String flavor) {
        if ("Test".equalsIgnoreCase(flavor)) {
            return getTestFacilityLocations();
        } else if ("Prod".equalsIgnoreCase(flavor)) {
            return getProdFacilityLocations();
        } else {
            //TODO: Decide what the default value should be
            return null;
        }
    }

    private static Map<String, String> getTestFacilityLocations() {
        Map<String, String> testFacilityLocations = new HashMap<>();

        return testFacilityLocations;
    }


    public static Map<String,String> getProdFacilityLocations() {
        return null;
    }
}
