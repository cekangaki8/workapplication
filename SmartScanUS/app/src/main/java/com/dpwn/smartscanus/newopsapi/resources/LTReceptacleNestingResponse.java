package com.dpwn.smartscanus.newopsapi.resources;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cekangak on 9/25/2015.
 */
public class LTReceptacleNestingResponse {

    /** Response element for receptacle nesting. */
    @SerializedName("Response")
    private LTReceptacleNestingResponseMsg response;

    /** Status element. */
    @SerializedName("Status")
    private Status status;

    /**Default Constructor */
    public LTReceptacleNestingResponse() {
    }

    /**Default Constructor */
    public LTReceptacleNestingResponse(LTReceptacleNestingResponseMsg response, Status status) {
        this.response = response;
        this.status = status;
    }

    /** Getter fro the response element. */
    public LTReceptacleNestingResponseMsg getResponse() {
        return response;
    }

    /** Setter for the response element. */
    public void setResponse(LTReceptacleNestingResponseMsg response) {
        this.response = response;
    }

    /** Getter for the status element. */
    public Status getStatus() {
        return status;
    }

    /** Setter for the status element. */
    public void setStatus(Status status) {
        this.status = status;
    }
}
