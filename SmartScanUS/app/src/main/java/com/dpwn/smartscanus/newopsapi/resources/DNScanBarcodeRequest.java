package com.dpwn.smartscanus.newopsapi.resources;

/**
 * The request pojo for deliveryNetwork scanMailItem endpoint
 *
 * Created by cekangaki on 11/30/2017.
 */
public class DNScanBarcodeRequest {

    /**The scanned mailItem barcode. */
    private String scannedMailItemBarcode;

    /**
     * Constructor with scannedMailItemBarcode
     *
     * @param scannedMailItemBarcode scanned mail item barcode
     */
    public DNScanBarcodeRequest(String scannedMailItemBarcode) {
        this.scannedMailItemBarcode = scannedMailItemBarcode;
    }

    /**
     * Getter for the scanned mail item barcode
     *
     * @return the scanned mail item barcode
     */
    public String getScannedMailItemBarcode() {
        return scannedMailItemBarcode;
    }

    /**
     * Setter for the scanned mail item barcode
     *
     * @param scannedMailItemBarcode scanned mail item barcode
     */
    public void setScannedMailItemBarcode(String scannedMailItemBarcode) {
        this.scannedMailItemBarcode = scannedMailItemBarcode;
    }
}
