package com.dpwn.smartscanus.utils;

import android.content.SharedPreferences;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.dpwn.smartscanus.BuildConfig;

/**
 * Created by fshamim on 01.04.15.
 */
@Singleton
public class PrefUtils {

    @Inject
    private SharedPreferences prefs;

    public PrefUtils() {
    }

    public void activateAdminMode() {
        prefs.edit().putBoolean(PrefsConsts.ADMIN_MODE, true).apply();
    }

    public void deactivateAdminMode() {
        prefs.edit().putBoolean(PrefsConsts.ADMIN_MODE, false).apply();
    }

    public boolean isAdminMode() {
        return prefs.getBoolean(PrefsConsts.ADMIN_MODE, false);
    }

    public void initAdminMode() {
        if (BuildConfig.IS_PRODUCTION) {
            deactivateAdminMode();
        } else {
            activateAdminMode();
        }
    }
}
