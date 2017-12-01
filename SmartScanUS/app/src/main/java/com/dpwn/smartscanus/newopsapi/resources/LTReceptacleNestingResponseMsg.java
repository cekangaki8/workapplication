package com.dpwn.smartscanus.newopsapi.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by cekangak on 9/25/2015.
 */
public class LTReceptacleNestingResponseMsg extends Response{
    /** Displays the count of receptacles associated to a given transport unit. */
    private String ReceptacleCount;

    /** Returns the state of the response as either Warning, Good or Error.*/
    private String AssociationState;

    /**
     * Getter for the receptacleCount
     * @return
     */
    @JsonProperty("ReceptacleCount")
    public String getReceptacleCount() {
        return ReceptacleCount;
    }

    /**
     * Setter for receptacleCount.
     * @param receptacleCount
     */
    public void setReceptacleCount(String receptacleCount) {
        this.ReceptacleCount = receptacleCount;
    }

    /**
     * Getter for the AssociationState
     * @return
     */
    @JsonProperty("AssociationState")
    public String getAssociationState() {
        return AssociationState;
    }

    /**
     * Setter for AssociationState.
     * @param AssociationState
     */
    public void setAssociationState(String AssociationState) {
        this.AssociationState = AssociationState;
    }
}
