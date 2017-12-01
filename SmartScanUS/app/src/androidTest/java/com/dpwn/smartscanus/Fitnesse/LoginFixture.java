package com.dpwn.smartscanus.Fitnesse;

import fit.ColumnFixture;
import fit.Fixture;

//TODO: Add robolectric dependency so as to be able to run on the JVM

/**
 * Created by cekangak on 7/21/2015.
 */
public class LoginFixture extends ColumnFixture {
    private String userName;
    private String password;

    public LoginFixture() throws Exception{
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPasword(String password) {
        this.password = password;
    }

    public String returnMessage() {
        if (userName.equalsIgnoreCase("Valid User")
                && password.equalsIgnoreCase("Valid Password")) {
            return"Login Successful!";

        } else if(userName.equalsIgnoreCase("Expired User")) {
            return "Your password has expired. Please change it and try again.";
        } else if(userName.equalsIgnoreCase("Invalid Credentials")) {
            return "The specified password is incorrect. Please verify and try again.";
        } else {
            return "The specified username does not exist. Please verify and try again.";
        }
    }
}
