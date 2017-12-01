package com.dpwn.smartscanus.newopsapi;


import retrofit.http.Body;
import rx.Observable;

import com.dpwn.smartscanus.newopsapi.resources.BarcodeRequest;
import com.dpwn.smartscanus.newopsapi.resources.BarcodeResponse;

import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by cekangak on 7/9/2015.
 */
public interface ISortingServiceApi {

    /**
     * Calling this service should return the current status of the specified transport.
     *
     */
    @Headers("Content-Type: application/json; charset=UTF-8")
    @POST("/sorting/processBarCode/")
    Observable<BarcodeResponse> processBarcode(@Body BarcodeRequest body);

    @Headers("Content-Type: application/json; charset=UTF-8")
    @POST("/sorting/processDSPContainerScan/")
    Observable<BarcodeResponse> processDSPContainerScan(@Body BarcodeRequest body);

    @Headers("Content-Type: application/json; charset=UTF-8")
    @POST("/sorting/processDSPSackScan/")
    Observable<BarcodeResponse> processDSPSackScan(@Body BarcodeRequest body);

    @Headers("Content-Type: application/json; charset=UTF-8")
    @POST("/sorting/processDSPPieceScan/")
    Observable<BarcodeResponse> processDSPPieceScan(@Body BarcodeRequest body);



}
