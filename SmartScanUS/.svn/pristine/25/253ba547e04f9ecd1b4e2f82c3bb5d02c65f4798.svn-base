package com.dpwn.smartscanus.utils.ui;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.squareup.otto.Bus;

import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.login.ui.LoginFragment;
import com.dpwn.smartscanus.utils.PrefUtils;

/**
 * Created by fshamim on 05.11.14.
 */
@Singleton
public class DialogUtils {
    private static final String LOGOUT_MSG = "Do you want to Logout?";

    @Inject
    private Bus bus;

    @Inject
    private PrefUtils prefUtils;

    private CustomizedDialog currentDialog;

    /**
     * show the logout currentDialog
     *
     * @param fragmentContainer
     * @param showDialog        true to show false otherwise
     */
    public void showHideLogoutDialog(Activity activity, final IFragmentContainer fragmentContainer, boolean showDialog) {

        if (showDialog && fragmentContainer != null) {
            currentDialog = new CustomizedDialog(activity, bus, prefUtils)
                    .setMessage(LOGOUT_MSG)
                    .setDrawable(R.drawable.ic_warning_red)
                    .setPositiveButton(activity.getString(R.string.yes_log_out), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            fragmentContainer.setContentFragment(LoginFragment.class, true, null);
                        }
                    })
                    .setNegativeButton(activity.getString(android.R.string.cancel), null);
            currentDialog.show();
        } else {
            hideDialog(currentDialog);
        }

    }

    /**
     * Show custom dialog with following details
     *
     * @param activity           container
     * @param message            on the dialog
     * @param drawableId         on the dialog
     * @param positiveBtnText    as is
     * @param positiveListener   as is
     * @param showStandardCancel true if to show a standard cancel button which on click dismisses the dialog
     */
    public void showDialogPositive(FragmentActivity activity, String message, int drawableId, String positiveBtnText, View.OnClickListener positiveListener, boolean showStandardCancel) {
        hideDialog(currentDialog);
        currentDialog = new CustomizedDialog(activity, bus, prefUtils)
                .setMessage(message)
                .setDrawable(drawableId)
                .setPositiveButton(positiveBtnText, positiveListener);
        if (showStandardCancel) {
            currentDialog.setNegativeButton(activity.getString(android.R.string.cancel), null);
        }
        currentDialog.show();
    }

    /**
     * Show custom dialog with following details
     *
     * @param activity         container
     * @param message          on the dialog
     * @param drawableId       on the dialog
     * @param negativeBtnText  as is
     * @param negativeListener as is
     */
    public void showDialogNegative(FragmentActivity activity, String message, int drawableId, String negativeBtnText, View.OnClickListener negativeListener) {
        hideDialog(currentDialog);
        currentDialog = new CustomizedDialog(activity, bus, prefUtils)
                .setMessage(message)
                .setDrawable(drawableId)
                .setNegativeButton(negativeBtnText, negativeListener);
        currentDialog.show();
    }

    /**
     * Show custom dialog with following details
     *
     * @param activity         container
     * @param message          on the dialog
     * @param drawableId       on the dialog
     * @param positiveBtnText  as is
     * @param positiveListener as is
     * @param negativeBtnText  as is
     * @param negativeListener as is
     */
    public void showDialogTwoButtons(FragmentActivity activity, String message, int drawableId, String positiveBtnText, View.OnClickListener positiveListener, String negativeBtnText, View.OnClickListener negativeListener) {
        hideDialog(currentDialog);
        currentDialog = new CustomizedDialog(activity, bus, prefUtils)
                .setMessage(message)
                .setDrawable(drawableId)
                .setPositiveButton(positiveBtnText, positiveListener)
                .setNegativeButton(negativeBtnText, negativeListener);
        currentDialog.show();
    }

    /**
     * Show custom dialog with following details
     *
     * @param activity         container
     * @param message          on the dialog
     * @param hintText         on the dialog
     * @param isPassword       property
     * @param positiveBtnText  as is
     * @param positiveListener as is
     */
    public void showDialogEditText(FragmentActivity activity, String message, String hintText, Boolean isPassword, String positiveBtnText, View.OnClickListener positiveListener) {
        hideDialog(currentDialog);
        currentDialog = new CustomizedDialog(activity, bus, prefUtils)
                .setMessage(message)
                .setVibOn(false)
                .setTTSOn(false)
                .setDrawable(0)
                .setEditText(hintText, isPassword)
                .setPositiveButton(positiveBtnText, positiveListener)
                .setNegativeButton(activity.getString(android.R.string.cancel), null);
        currentDialog.show();
    }

    /**
     * Check if the dialog is showing
     *
     * @return true if showing
     */
    public boolean isShowing() {
        return currentDialog != null && currentDialog.isShowing();
    }

    private void hideDialog(CustomizedDialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public String getInputText() {
        return currentDialog.getInputText();
    }
}
