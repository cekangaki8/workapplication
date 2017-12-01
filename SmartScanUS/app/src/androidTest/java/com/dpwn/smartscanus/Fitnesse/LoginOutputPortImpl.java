package com.dpwn.smartscanus.Fitnesse;

import com.dpwn.smartscanus.login.ILoginOutputPort;

/**
 * Created by cekangak on 7/15/2015.
 */
public class LoginOutputPortImpl implements ILoginOutputPort{
    private String messageRecieved = new String();
    private boolean blnLoginSuccess;
    public boolean blnRequestComplete;

    public boolean isSuccesfulLogin() {
        return blnLoginSuccess;
    }

    public String getMessageRecieved() {
        return messageRecieved;
    }

    @Override
    public void loginSuccessful() {
        blnLoginSuccess = true;
        messageRecieved = "";
        blnRequestComplete = true;
    }

    @Override
    public void loginNotSuccessful(String message) {
        blnLoginSuccess = false;
        messageRecieved = message;
        blnRequestComplete = true;
    }

    @Override
    public void indicateProgress(String loadingMsg) {

    }

    @Override
    public void indicateProgressFinished() {

    }

    @Override
    public void unknownErrorOccurred(int statusCode, String reason) {

    }
}
