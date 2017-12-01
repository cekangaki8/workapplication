package com.dpwn.smartscanus.IntegrationTests.FullService;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.ImageView;
import android.widget.TextView;

import com.dpwn.smartscanus.IntegrationTests.BaseIntegrationTests;
import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.main.ui.MainActivity;
import com.dpwn.smartscanus.fullServiceImb.ui.ScanFullSvcIMBFragment;
import com.robotium.solo.Condition;
import com.robotium.solo.Solo;

/**
 * Created by cekangak on 7/15/2015.
 */
public class FullServiceTest extends BaseIntegrationTests {

    public void testScanningBarcode() {
        MainActivity mainActivity = (MainActivity) solo.getCurrentActivity();
        mainActivity.setContentFragment(ScanFullSvcIMBFragment.class, true, null);
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {
                MainActivity mainActivity = (MainActivity) solo.getCurrentActivity();
                Fragment currentFragment = mainActivity.getSupportFragmentManager().findFragmentById(R.id.content_frame);
                return (currentFragment instanceof ScanFullSvcIMBFragment);
            }
        }, 3000);

        TextView tvMessage = (TextView) solo.getView(R.id.tvMessage);
        ImageView imgFSScanning = (ImageView) solo.getView(R.id.img_fsimbscanner);

        //Added by Anik
        //Validating Cancel Button
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.clickOnButton("Cancel");

        //1.) Invalid Barcode
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "InvalidBarcode");
        solo.clickOnButton("OK");
        solo.waitForDialogToClose(2000);
        solo.waitForText("Tray or Container not found");
        assertEquals("Tray or Container not found", tvMessage.getText());
        //Added by Anik
        //Robotium returns negative value for color. RED Color : -65536 , Green : -16711936
        assertEquals(Color.RED, tvMessage.getCurrentTextColor()); //validating text color
        ColorDrawable colorDrawableInvalid = (ColorDrawable) imgFSScanning.getBackground();
        int backGroundColorInvalid = colorDrawableInvalid.getColor();
        assertEquals(Color.RED, backGroundColorInvalid); //validating background color

        //2.) Valid Tray Barcode
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "ValidImtp");
        solo.clickOnButton("OK");
        solo.waitForDialogToClose(2000);
        solo.waitForText("Tray Successfully Scanned");
        assertEquals("Tray Successfully Scanned", tvMessage.getText());
        //Added by Anik
        assertEquals(Color.GREEN, tvMessage.getCurrentTextColor()); //validating text color
        ColorDrawable colorDrawableValidTray = (ColorDrawable) imgFSScanning.getBackground();
        int backGroundColorValidTray = colorDrawableValidTray.getColor();
        assertEquals(Color.GREEN,backGroundColorValidTray); //validating background color

        //3.) Valid Container Barcode
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "ValidImcp");
        solo.clickOnButton("OK");
        solo.waitForDialogToClose(2000);
        solo.waitForText("Container Successfully Scanned");
        assertEquals("Container Successfully Scanned", tvMessage.getText());
        //Added by Anik
        assertEquals(Color.GREEN, tvMessage.getCurrentTextColor()); //validating text color
        ColorDrawable colorDrawableValidContainer = (ColorDrawable) imgFSScanning.getBackground();
        int backGroundColorValidContainer = colorDrawableValidContainer.getColor();
        assertEquals(Color.GREEN,backGroundColorValidContainer); //validating background color

        //Added by Anik on 7-31-15
        //4.) Tray Already Scanned
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "ScannedImtp");
        solo.clickOnButton("OK");
        solo.waitForDialogToClose(2000);
        solo.waitForText("Tray already scanned");
        assertEquals("Tray already scanned", tvMessage.getText());
        assertEquals(Color.RED, tvMessage.getCurrentTextColor()); //validating text color
        ColorDrawable colorDrawableDuplicateTray = (ColorDrawable) imgFSScanning.getBackground();
        int backGroundColorDuplicateTray = colorDrawableDuplicateTray.getColor();
        assertEquals(Color.RED,backGroundColorDuplicateTray); //validating background color

        //Added by Anik on 7-31-15
        //5.) Container Already Scanned
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "ScannedImcp");
        solo.clickOnButton("OK");
        solo.waitForDialogToClose(2000);
        solo.waitForText("Container already scanned");
        assertEquals("Container already scanned", tvMessage.getText());
        assertEquals(Color.RED, tvMessage.getCurrentTextColor()); //validating text color
        ColorDrawable colorDrawableDuplicateContainer = (ColorDrawable) imgFSScanning.getBackground();
        int backGroundColorDuplicateContainer = colorDrawableDuplicateContainer.getColor();
        assertEquals(Color.RED,backGroundColorDuplicateContainer); //validating background color

        //6.) Duplicated Manifested Barcode
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "DupImtp");
        solo.clickOnButton("OK");
        solo.waitForDialogToClose(2000);
        solo.waitForText("Tray or Container already manifested");
        assertEquals("Tray or Container already manifested", tvMessage.getText());
        //Added by Anik
        assertEquals(Color.RED, tvMessage.getCurrentTextColor()); //validating text color
        ColorDrawable colorDrawableDuplicate = (ColorDrawable) imgFSScanning.getBackground();
        int backGroundColorDuplicate = colorDrawableDuplicate.getColor();
        assertEquals(Color.RED,backGroundColorDuplicate); //validating background color

    }

    public void testScanningBarcodeOrientation() {
        MainActivity mainActivity = (MainActivity) solo.getCurrentActivity();
        mainActivity.setContentFragment(ScanFullSvcIMBFragment.class, true, null);
        solo.waitForCondition(new Condition() {
            @Override
            public boolean isSatisfied() {

                MainActivity mainActivity = (MainActivity) solo.getCurrentActivity();
                Fragment currentFragment = mainActivity.getSupportFragmentManager().findFragmentById(R.id.content_frame);
                return (currentFragment instanceof ScanFullSvcIMBFragment);
            }
        }, 3000);

        //Landscape View
        solo.setActivityOrientation(Solo.LANDSCAPE);
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "ScannedImtp");
        solo.clickOnButton("OK");
        solo.waitForText("Tray already scanned");

        //Portrait View
        solo.setActivityOrientation(Solo.PORTRAIT);
        solo.clickOnView(solo.getView(R.id.action_input));
        solo.enterText(0, "ValidImcp");
        solo.clickOnButton("OK");
        solo.waitForText("Container Successfully Scanned");
    }

}

