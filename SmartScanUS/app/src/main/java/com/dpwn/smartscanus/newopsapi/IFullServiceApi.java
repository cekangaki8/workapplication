package com.dpwn.smartscanus.newopsapi;


import retrofit.http.Body;
import rx.Observable;

import com.dpwn.smartscanus.newopsapi.resources.FSProcessBarcodeRequest;
import com.dpwn.smartscanus.newopsapi.resources.FSProcessBarcodeResponse;

import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by cekangak on 7/9/2015.
 */
public interface IFullServiceApi {

    /**
     * Calling this service should return the current status of the specified transport.
     *
     */
    @Headers("Content-Type: application/json; charset=UTF-8")
    @POST("/sorting/processBarCode/")
    Observable<FSProcessBarcodeResponse> processBarcode(@Body FSProcessBarcodeRequest body);


}
