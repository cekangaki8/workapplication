package com.dpwn.smartscanus.IntegrationTests.Login;


import android.test.ActivityInstrumentationTestCase2;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.Spinner;
import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.login.ui.LoginFragment;
import com.dpwn.smartscanus.main.ui.MainActivity;
import com.robotium.solo.Solo;


/**
 * Created by cekangak on 7/14/2015.
 */
public class LoginTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    private int selectedFacIndex = 2;

    public LoginTest() {
        super(MainActivity.class);
    }

    public void setUp() {
        solo = new Solo(getInstrumentation(), getActivity());
    }


    public void testLogin() throws Exception{

        MainActivity mainActivity = (MainActivity) solo.getCurrentActivity();
        final Fragment fgmntLogin = mainActivity.getSupportFragmentManager().findFragmentById(R.id.content_frame);
        assertNotNull(fgmntLogin);
        assertTrue(fgmntLogin instanceof LoginFragment);

        EditText etUserName = (EditText) solo.getView(R.id.etLoginUser);
        assertNotNull(etUserName);
        EditText etPassword = (EditText) solo.getView(R.id.etLoginPass);
        assertNotNull(etPassword);

        Spinner spinner = solo.getView(Spinner.class,0);
        solo.clickOnView(spinner);
        solo.clickInList(selectedFacIndex);

        //1.) Invalid credentials
        solo.clearEditText(etUserName);
        solo.clearEditText(etPassword);
        solo.enterText(etUserName, "InvalidCredentials");
        solo.enterText(etPassword, "testPassword");
        solo.clickOnView(solo.getView(R.id.btn_login));
        assertTrue(solo.waitForText("The specified password is incorrect. Please verify and try again."));

        //2.) Expired password
        solo.clearEditText(etUserName);
        solo.clearEditText(etPassword);
        solo.enterText(etUserName, "ExpiredUserName");
        solo.enterText(etPassword, "testPassword");
        solo.clickOnView(solo.getView(R.id.btn_login));
        assertTrue(solo.waitForText("Your password has expired. Please change it and try again."));

        //3.) Invalid user
        solo.clearEditText(etUserName);
        solo.clearEditText(etPassword);
        solo.enterText(etUserName, "InvalidUser");
        solo.enterText(etPassword, "testPassword");
        solo.clickOnView(solo.getView(R.id.btn_login));
        assertTrue(solo.waitForText("The specified username does not exist. Please verify and try again."));

        //4.) Valid user
        solo.clearEditText(etUserName);
        solo.clearEditText(etPassword);
        solo.enterText(etUserName, "ValidUserName");
        solo.enterText(etPassword, "ValidPassword");
        solo.clickOnView(solo.getView(R.id.btn_login));

        //Added by Anik
        assertTrue(solo.waitForView(solo.getView(R.id.btn_mainfrg_logout)));
        solo.clickOnView(solo.getView(R.id.btn_mainfrg_logout));
        solo.waitForDialogToOpen();
        solo.clickOnMenuItem("Yes, Log out");
        assertTrue(solo.waitForView(solo.getView(R.id.btn_login)));

    }

    public void testLoginFacility() {

        Spinner spinner = solo.getView(Spinner.class,0);
        int count = spinner.getAdapter().getCount();

        String actualFacilityValue;
        String expectedFacilityValue;

        for (int i = 1; i <= count ; i++) {

            EditText etUserName = (EditText) solo.getView(R.id.etLoginUser);
            EditText etPassword = (EditText) solo.getView(R.id.etLoginPass);

            solo.clickOnView(spinner);
            solo.clickInList(i);
            expectedFacilityValue = spinner.getAdapter().getItem(i-1).toString();
            solo.clearEditText(etUserName);
            solo.clearEditText(etPassword);
            solo.enterText(etUserName, "ValidUserName");
            solo.enterText(etPassword, "ValidPassword");
            solo.clickOnButton("Login");
            solo.waitForText("Log out");
            solo.clickOnButton("Log out");
            solo.clickOnMenuItem("Yes, Log out");
            assertTrue(solo.waitForView(solo.getView(R.id.btn_login)));
            spinner = solo.getView(Spinner.class,0);
            actualFacilityValue = spinner.getSelectedItem().toString();
            assertEquals("Facility Name Does not Match after Logout;",expectedFacilityValue,actualFacilityValue);
        }
            solo.clickOnView(spinner);
            solo.clickInList(selectedFacIndex);
    }

      public void testLoginOrientation()
    {
        //Landscape View
        solo.setActivityOrientation(Solo.LANDSCAPE);
        Spinner spinner = solo.getView(Spinner.class,0);
        EditText etUserName = (EditText) solo.getView(R.id.etLoginUser);
        EditText etPassword = (EditText) solo.getView(R.id.etLoginPass);
        //Expected Value
        String UserName = "InvalidCredentials";
        String Password = "testPassword";
        int i = 2;
        solo.clickOnView(spinner);
        solo.clickInList(i);
        String expected = spinner.getAdapter().getItem(i-1).toString(); // expected facility
        solo.enterText(etUserName, UserName);
        solo.enterText(etPassword, Password);
        solo.clickOnView(solo.getView(R.id.btn_login));
        assertTrue(solo.waitForText("The specified password is incorrect. Please verify and try again."));

       //Portrait View
        solo.setActivityOrientation(Solo.PORTRAIT);
        spinner = solo.getView(Spinner.class,0);
        assertTrue(solo.waitForText("The specified password is incorrect. Please verify and try again."));
        String actual = spinner.getSelectedItem().toString();  //Actual Facility
        assertEquals("Facility Name Does not Match after changing Orientation;", expected, actual);
        assertEquals(UserName, etUserName.getText().toString());
        assertEquals(Password,etPassword.getText().toString());
        //Expected Value
        UserName = "ExpiredUserName";
        Password = "testPassword";
        i = 1;
        etUserName = (EditText) solo.getView(R.id.etLoginUser);
        etPassword = (EditText) solo.getView(R.id.etLoginPass);
        solo.clickOnView(spinner);
        solo.clickInList(i);
        expected = spinner.getAdapter().getItem(i-1).toString();  // expected facility
        solo.clearEditText(etUserName);
        solo.clearEditText(etPassword);
        solo.enterText(etUserName, UserName);
        solo.enterText(etPassword, Password);
        solo.clickOnView(solo.getView(R.id.btn_login));
        assertTrue(solo.waitForText("Your password has expired. Please change it and try again."));

        //Landscape View
        solo.setActivityOrientation(Solo.LANDSCAPE);
        spinner = solo.getView(Spinner.class,0);
        assertTrue(solo.waitForText("Your password has expired. Please change it and try again."));
        actual = spinner.getSelectedItem().toString();  //Actual Facility
        assertEquals("Facility Name Does not Match after changing orientation;",expected,actual);
        assertEquals(UserName, etUserName.getText().toString());
        assertEquals(Password, etPassword.getText().toString());
        //Valid Login
        UserName = "ValidUserName";
        Password = "ValidPassword";
        etUserName = (EditText) solo.getView(R.id.etLoginUser);
        etPassword = (EditText) solo.getView(R.id.etLoginPass);
        solo.clearEditText(etUserName);
        solo.clearEditText(etPassword);
        solo.enterText(etUserName, UserName);
        solo.enterText(etPassword, Password);
        solo.clickOnView(solo.getView(R.id.btn_login));
       // solo.setActivityOrientation(Solo.LANDSCAPE);  //Dashboard is locked to Portrait View Only
        assertTrue(solo.waitForText("Log out"));
        solo.clickOnButton("Log out");
        solo.clickOnMenuItem("Yes, Log out");
        solo.setActivityOrientation(Solo.LANDSCAPE);
        spinner = solo.getView(Spinner.class,0);
        solo.waitForText("Login");
        actual = spinner.getSelectedItem().toString();  //Actual Facility
        assertEquals("Facility Name Does not Match after Logout;",expected,actual);

        solo.clickOnView(spinner);
        solo.clickInList(selectedFacIndex);
    }

}




