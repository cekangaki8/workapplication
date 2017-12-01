package com.dpwn.smartscanus.newopsapi.resources;

/**
 * Created by cekangak on 9/25/2015.
 */
public class LTReceptacleNestingRequest {

    /**The TU number. */
    private String TransportUnit;

    /**The receptacle number. */
    private String Receptacle;

    /**Value set to true to validate optional errors.
     * Set to false to skip validation and process.
     */
    private boolean CheckOptionalErrors;

    /** Default Constructor.
     * @param transportUnitNumber
     * @param receptacleNumber
     * @param checkOptionalErrors*/
    public LTReceptacleNestingRequest(String transportUnitNumber, String receptacleNumber, boolean checkOptionalErrors) {
        setTransportUnit(transportUnitNumber);
        setReceptacle(receptacleNumber);
        setCheckOptionalErrors(checkOptionalErrors);
    }

    public LTReceptacleNestingRequest(String transportUnit, String receptacle) {
        setTransportUnit(transportUnit);
        setReceptacle(receptacle);
    }

    /** Getter for the TU number. */
    public String getTransportUnit() {
        return TransportUnit;
    }

    /** Setter for the TU number. */
    public void setTransportUnit(String transportUnit) {
        TransportUnit = transportUnit;
    }

    /** Getter for the receptacle number. */
    public String getReceptacle() {
        return Receptacle;
    }

    /** Setter for the receptacle number. */
    public void setReceptacle(String receptacle) {
        this.Receptacle = receptacle;
    }

    public boolean isCheckOptionalErrors() {
        return CheckOptionalErrors;
    }

    public void setCheckOptionalErrors(boolean checkOptionalErrors) {
        this.CheckOptionalErrors = checkOptionalErrors;
    }
}
