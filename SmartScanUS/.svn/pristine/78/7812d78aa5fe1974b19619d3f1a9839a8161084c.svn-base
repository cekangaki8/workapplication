package com.dpwn.smartscanus.utils.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.TextView;

import com.dpwn.smartscanus.R;

/**
 * Created by mmansar on 10.10.14.
 */
public class ProgressUtils {
    private ProgressDialog dlgProgress;

    /**
     * Indicate a progress bar on the given activity
     *
     * @param activity android context
     */
    public void showProgress(Activity activity, String loadingMsg) {
        if (dlgProgress == null) {
            dlgProgress = new ProgressDialog(activity);
        }
        if (!dlgProgress.isShowing()) {
            dlgProgress.show();
            dlgProgress.setCancelable(false);
            dlgProgress.setContentView(R.layout.view_centered_progress_indicator);
        }
        if (loadingMsg != null) {
            TextView tvProgressMsg = (TextView) dlgProgress.findViewById(R.id.tv_progress_msg);
            if (tvProgressMsg != null) {
                tvProgressMsg.setText(loadingMsg);
            }
        }
    }

    /**
     * hide the previously shown progress bar
     */
    public void hideProgress() {
        if (dlgProgress != null && dlgProgress.isShowing()) {
            dlgProgress.dismiss();
        }
    }

    /**
     * check if the progress bar is showing
     *
     * @return true if showing
     */
    public boolean isInProgress() {
        return dlgProgress != null && dlgProgress.isShowing();
    }
}
