package com.dpwn.smartscanus.login;

import com.dpwn.smartscanus.interactor.async.IAsyncInteractorOutputPort;

/**
 * Created by fshamim on 29.09.14.
 */
public interface ILoginOutputPort extends IAsyncInteractorOutputPort{

    /**
     * When the login is successful role and the token is returned
     */
    void loginSuccessful();


    /**
     * When the login is not successful either password or username is false.
     */
    void loginNotSuccessful(String message);


}
