package com.dpwn.smartscanus.IntegrationTests.transport;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dpwn.smartscanus.IntegrationTests.BaseIntegrationTests;
import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.main.ui.MainActivity;
import com.dpwn.smartscanus.transport.ui.ScanReceptactleIntoTuFragment;
import com.dpwn.smartscanus.transport.ui.ScanTransportUnitFragment;
import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

/**
 * Created by cekangak on 10/5/2015.
 */
public class ScanTransportUnitTest extends BaseIntegrationTests {

    /**
     * This will test the load transport test cases.
     */
    public void testScanTransportUnit() {
        MainActivity mainActivity = (MainActivity) solo.getCurrentActivity();
        mainActivity.setContentFragment(ScanTransportUnitFragment.class, true, null);

        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                MainActivity mainActivity = (MainActivity) solo.getCurrentActivity();
                Fragment currentFragment = mainActivity.getSupportFragmentManager().findFragmentById(R.id.content_frame);
                return (currentFragment instanceof ScanTransportUnitFragment);
            }
        }, 3000);

        LinearLayout llMessagePane = (LinearLayout) solo.getView(R.id.ll_LoadTuMessagePane);
        TextView tvErrorMessage = (TextView) solo.getView(R.id.tv_LT_ErrorMessage);

        //Invalid test case
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "99M889363070000000222");
        solo.clickOnButton("OK");
        solo.waitForDialogToClose(2000);
        solo.waitForText("TransportUnit not found.");
        assertEquals(Color.RED, ((ColorDrawable) llMessagePane.getBackground()).getColor());
        assertEquals("TransportUnit not found.", tvErrorMessage.getText());

        //Valid Test Case
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "99M889363070000000444");
        solo.clickOnButton("OK");
        solo.waitForDialogToClose(2000);
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                MainActivity mainActivity = (MainActivity) solo.getCurrentActivity();
                Fragment currentFragment = mainActivity.getSupportFragmentManager().findFragmentById(R.id.content_frame);
                return (currentFragment instanceof ScanReceptactleIntoTuFragment);
            }
        }, 3000);
        TextView tvActiveTransportUnit = (TextView) solo.getView(R.id.tv_lt_active_tu);
        assertEquals("99M889363070000000444", tvActiveTransportUnit.getText());
    }

    /**
     * This will test the landscape mode of load transport
     */
    public void testLoadTransportUnitLandscape() {
        MainActivity mainActivity = (MainActivity) solo.getCurrentActivity();
        mainActivity.setContentFragment(ScanTransportUnitFragment.class, true, null);

        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                MainActivity mainActivity = (MainActivity) solo.getCurrentActivity();
                Fragment currentFragment = mainActivity.getSupportFragmentManager().findFragmentById(R.id.content_frame);
                return (currentFragment instanceof ScanTransportUnitFragment);
            }
        }, 3000);

        //Landscape View
        solo.setActivityOrientation(Solo.LANDSCAPE);
        LinearLayout llMessagePane = (LinearLayout) solo.getView(R.id.ll_LoadTuMessagePane);
        TextView tvErrorMessage = (TextView) solo.getView(R.id.tv_LT_ErrorMessage);

        //Invalid test case
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "99M889363070000000222");
        solo.clickOnButton("OK");
        solo.waitForDialogToClose(2000);
        solo.waitForText("TransportUnit not found.");
        assertEquals(Color.RED, ((ColorDrawable) llMessagePane.getBackground()).getColor());
//        assertEquals("TransportUnit not found.", tvErrorMessage.getText());

      //  solo.clickOnMenuItem("HOME");
        solo.clickOnView(solo.getView(android.R.id.home));
        solo.clickOnView(((GridView) solo.getView(R.id.gvMenuItem)).getChildAt(2));


    }
}
