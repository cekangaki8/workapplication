package com.dpwn.smartscanus.IntegrationTests.Dashboard;

import android.support.v4.app.Fragment;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dpwn.smartscanus.IntegrationTests.BaseIntegrationTests;
import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.main.ui.MainActivity;
import com.robotium.solo.Condition;
import com.robotium.solo.Solo;
import com.dpwn.smartscanus.dashboard.MenuFragment;

/**
 * Created by maanik on 7/17/2015.
 */

public class DashboardTest extends BaseIntegrationTests {

    public void testDashboard() {
        MainActivity mainActivity = (MainActivity) solo.getCurrentActivity();
        mainActivity.setContentFragment(MenuFragment.class, true, null);


        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                MainActivity mainActivity = (MainActivity) solo.getCurrentActivity();
                Fragment currentFragment = mainActivity.getSupportFragmentManager().findFragmentById(R.id.content_frame);
                return (currentFragment instanceof MenuFragment);
            }
        }, 3000);

        //      Portrait View
        solo.setActivityOrientation(Solo.PORTRAIT);
        solo.clickOnView(solo.getView(R.id.btn_mainfrg_logout));
        assertTrue(solo.waitForText("Yes, Log out"));
        assertTrue(solo.waitForText("Cancel"));
        solo.clickOnMenuItem("Cancel");

        //IMB Full Service Scanning
        GridView menuGridView =(GridView) solo.getView(R.id.gvMenuItem);
        solo.clickOnView(menuGridView.getChildAt(0));
        ImageView imgImbScanner = (ImageView) solo.getView(R.id.img_fsimbscanner);
        assertTrue(solo.waitForView(imgImbScanner));
//        solo.clickOnMenuItem("HOME");
        solo.goBack();

        //DSP Scanning page
        solo.clickOnView(((GridView) solo.getView(R.id.gvMenuItem)).getChildAt(1));
        ImageView imgNextDayScan = (ImageView) solo.getView(R.id.img_nextdayscan);
        assertTrue(solo.waitForView(imgNextDayScan));
        solo.goBack();

        //Load Transport page
        solo.clickOnView(((GridView) solo.getView(R.id.gvMenuItem)).getChildAt(2));
        LinearLayout llMessagePane = (LinearLayout) solo.getView(R.id.ll_LoadTuMessagePane);
        assertTrue(solo.waitForView(llMessagePane));
        solo.clickOnView(solo.getView(android.R.id.home));

        solo.waitForView(solo.getView(R.id.btn_mainfrg_logout));
        solo.clickOnView(solo.getView(R.id.btn_mainfrg_logout));
        solo.clickOnMenuItem("Yes, Log out");
        assertTrue(solo.waitForText("Login"));

    }

}
