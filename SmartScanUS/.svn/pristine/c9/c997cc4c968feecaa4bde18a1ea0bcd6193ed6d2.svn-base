package com.dpwn.smartscanus.kiosk;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import com.dpwn.smartscanus.interactor.async.AsyncInteractorActivity;
import com.dpwn.smartscanus.logging.L;
import com.dpwn.smartscanus.utils.PrefUtils;

/**
 * Created by jrodriguez on 10/03/15.
 */
public abstract class KioskModeActivity extends AsyncInteractorActivity {

    private static final String TAG = KioskModeActivity.class.getSimpleName();
    @Inject
    protected PrefUtils prefUtils;

    @Override
    protected void onStop() {
        super.onStop();
        L.i(TAG, "onStop: ");
        if (prefUtils.isAdminMode()) {
            return;
        }
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<String> whiteList = new ArrayList<>();
        whiteList.add(getPackageName());
        if (!isWhiteList(activityManager, whiteList)) {
            activityManager.moveTaskToFront(getTaskId(), 0);
        }
    }

    private boolean isWhiteList(ActivityManager am, ArrayList<String> whiteList) {
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        for (String item : whiteList) {
            if (item.contains(componentInfo.getPackageName())) {
                return true;
            }
        }
        return false;
    }
}
