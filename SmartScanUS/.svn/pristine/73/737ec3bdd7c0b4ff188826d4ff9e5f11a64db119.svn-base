package com.dpwn.smartscanus.newopsapi;


import com.dpwn.smartscanus.newopsapi.resources.LoginApiRequest;


import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by cekangak on 7/8/2015.
 */
public interface IAuthenticationApi {
    /**
     * @param LoginApiRequest
     * @return
     */
    @Headers("Content-Type: application/json; charset=UTF-8")
    @POST("/authentication/login")
    Observable<Response> login(@Body LoginApiRequest body);

    @Headers("Content-Type: application/json; charset=UTF-8")
    @POST("/authentication/logout")
    Observable<Response> logout();
}
