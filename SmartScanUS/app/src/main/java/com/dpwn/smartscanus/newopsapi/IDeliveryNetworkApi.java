package com.dpwn.smartscanus.newopsapi;

import com.dpwn.smartscanus.newopsapi.resources.BarcodeRequest;
import com.dpwn.smartscanus.newopsapi.resources.BarcodeResponse;
import com.dpwn.smartscanus.newopsapi.resources.DNScanBarcodeRequest;

import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by cekangaki on 11/30/2017.
 */
public interface IDeliveryNetworkApi {

    @Headers("Content-Type: application/json; charset=UTF-8")
    @POST("/deliveryNetwork/processMailItem/")
    Observable<BarcodeResponse> processBarcode(@Body DNScanBarcodeRequest body);
}
