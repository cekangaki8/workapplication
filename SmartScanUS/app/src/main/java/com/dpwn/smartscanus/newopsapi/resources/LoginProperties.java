package com.dpwn.smartscanus.newopsapi.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cekangak on 7/8/2015.
 */
public class LoginProperties {

    @SerializedName("Response")
    private Response response;

    @SerializedName("Status")
    private Status status;

    Map<String, Object> additionalProperties = new HashMap<>();

    public LoginProperties() {}

    public LoginProperties(Response response, Status status) {
        this.response = response;
        this.status = status;
    }

    @JsonProperty("Response")
    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @JsonProperty("Status")
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
