package com.dpwn.smartscanus.newopsapi.resources;

/**
 * Created by cekangak on 7/9/2015.
 */
public class FSProcessBarcodeRequest {

    private String BarCode;

    public FSProcessBarcodeRequest(String barCode) {
        this.BarCode = barCode;
    }

    public void setBarCode(String barcode) {
        this.BarCode = barcode;
    }

    public String getBarCode() {
        return this.BarCode;
    }
}
