package com.dpwn.smartscanus.IntegrationTests;

import android.support.v4.app.Fragment;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import android.widget.Spinner;

import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.login.ui.LoginFragment;
import com.dpwn.smartscanus.main.ui.MainActivity;
import com.robotium.solo.Solo;

/**
 * Created by cekangak on 8/26/2015.
 */
public class BaseIntegrationTests extends ActivityInstrumentationTestCase2<MainActivity> {
    protected Solo solo;

    public BaseIntegrationTests() {
        super(MainActivity.class);
    }

    public void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
        setupUserAccount();
    }

    public void setupUserAccount() {
        //Call Login into the app
        MainActivity mainActivity = (MainActivity) solo.getCurrentActivity();
        final Fragment fgmntLogin = mainActivity.getSupportFragmentManager().findFragmentById(R.id.content_frame);
        assertNotNull(fgmntLogin);
        assertTrue(fgmntLogin instanceof LoginFragment);

        EditText etUserName = (EditText) solo.getView(R.id.etLoginUser);
        assertNotNull(etUserName);
        EditText etPassword = (EditText) solo.getView(R.id.etLoginPass);
        assertNotNull(etPassword);

        Spinner spinner = solo.getView(Spinner.class, 0);
        solo.clickOnView(spinner);
        solo.clickInList(2);

        solo.clearEditText(etUserName);
        solo.clearEditText(etPassword);
        solo.enterText(etUserName, "ValidUserName");
        solo.enterText(etPassword, "ValidPassword");
        solo.clickOnView(solo.getView(R.id.btn_login));
        solo.waitForText("Log out");

    }

}
