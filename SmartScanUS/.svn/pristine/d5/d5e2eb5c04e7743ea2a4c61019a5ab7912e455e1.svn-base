package com.dpwn.smartscanus.newopsapi.resources;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cekangak on 7/9/2015.
 */
public class BarcodeResponse {

    @SerializedName("Response")
    private Response response;

    @SerializedName("Status")
    private Status status;

    Map<String, Object> additionalProperties = new HashMap<>();

    public BarcodeResponse() {}

    public BarcodeResponse(Response response, Status status) {
        this.response = response;
        this.status = status;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
