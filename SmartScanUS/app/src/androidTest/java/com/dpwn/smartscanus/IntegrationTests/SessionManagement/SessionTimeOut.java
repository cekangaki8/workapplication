package com.dpwn.smartscanus.IntegrationTests.SessionManagement;

import android.test.ActivityInstrumentationTestCase2;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.Spinner;
import com.dpwn.smartscanus.dashboard.MenuFragment;
import com.robotium.solo.Condition;
import com.robotium.solo.Solo;
import com.dpwn.smartscanus.login.ui.LoginFragment;
import com.dpwn.smartscanus.main.ui.MainActivity;
import com.dpwn.smartscanus.R;

/**
 * Created by maanik on 8/27/2015.
 */
public class SessionTimeOut extends ActivityInstrumentationTestCase2<MainActivity>  {

    private Solo solo;
    private int selectedFacIndex = 2;

    public SessionTimeOut() { super(MainActivity.class);}
    public void setUp() {solo = new Solo(getInstrumentation(), getActivity());}

        public void testSessionTimeOut() {

         MainActivity mainActivity = (MainActivity) solo.getCurrentActivity();
            Fragment fragment = mainActivity.getSupportFragmentManager().findFragmentById(R.id.content_frame);
            assertNotNull(fragment);
            assertTrue(fragment instanceof LoginFragment);

            Spinner spinner = solo.getView(Spinner.class,0);
            solo.clickOnView(spinner);
            solo.clickInList(selectedFacIndex);

            EditText etUserName = (EditText) solo.getView(R.id.etLoginUser);
            EditText etPassword = (EditText) solo.getView(R.id.etLoginPass);

            solo.enterText(etUserName, "ValidUserName");
            solo.enterText(etPassword, "ValidPassword");
            solo.clickOnView(solo.getView(R.id.btn_login));
            solo.clickOnView(solo.getView(R.id.btn_mainfrg_logout));
            solo.clickOnMenuItem("Yes, Log out");
            assertTrue(solo.waitForView(solo.getView(R.id.btn_login)));

            mainActivity.setContentFragment(MenuFragment.class, true, null);
            solo.waitForCondition(new Condition() {
                @Override
                public boolean isSatisfied() {
                    MainActivity mainActivity = (MainActivity) solo.getCurrentActivity();
                    Fragment currentFragment = mainActivity.getSupportFragmentManager().findFragmentById(R.id.content_frame);
                    return (currentFragment instanceof MenuFragment);
                }
            }, 3000);

            solo.clickOnMenuItem("Full service IMB.");
            solo.clickOnView(solo.getView(R.id.action_input));
            solo.enterText(0, "InvalidBarcode");
            solo.clickOnButton("OK");
            solo.waitForDialogToOpen(3000);
            assertTrue(solo.waitForText("Session timeout. Please log in again"));
            solo.clickOnMenuItem("OK");
            assertTrue(solo.waitForView(solo.getView(R.id.btn_login)));

    }
}
