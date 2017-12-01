package com.dpwn.smartscanus.newopsapi;

import com.dpwn.smartscanus.newopsapi.resources.BarcodeRequest;
import com.dpwn.smartscanus.newopsapi.resources.BarcodeResponse;
import com.dpwn.smartscanus.newopsapi.resources.LTReceptacleNestingRequest;
import com.dpwn.smartscanus.newopsapi.resources.LTReceptacleNestingResponse;


import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by cekangak on 9/25/2015.
 */
public interface ILoadTransportApi {

    @Headers("Content-Type: application/json; charset=UTF-8")
    @POST("/loadtransport/getTransportUnit/")
    Observable<BarcodeResponse> getTransportUnit(@Body BarcodeRequest body);


    @Headers("Content-Type: application/json; charset=UTF-8")
    @POST("/loadtransport/processReceptacleToTU/")
    Observable<LTReceptacleNestingResponse> processReceptacleToTU(@Body LTReceptacleNestingRequest body);
}
