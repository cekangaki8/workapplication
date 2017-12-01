package com.dpwn.smartscanus.nfc;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jrodriguez on 02/09/14.
 */
public class NdefMessageParser {

    private NdefMessageParser() {

    }
    /**
     * parse method is used to get a List of Strings obtained from a NdefMessage Object
     */
    public static List<String> parse(NdefMessage message) {
        return getRecords(message.getRecords());
    }

    /**
     * Getting Records from String List
     */
    public static List<String> getRecords(NdefRecord[] records) {
        List<String> elements = new ArrayList<String>();
        for (final NdefRecord record : records) {
                elements.add(parseRecord(record));
        }
        return elements;
    }
    /**
     * parseRecord method gets the information that a record has. Create and return a String with
     * the obtained data.
     * */
    public static String parseRecord(NdefRecord record) {
        try {
            byte[] payload = record.getPayload();

            String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";
            int languageCodeLength = payload[0] & 63;
            String text =
                    new String(payload, languageCodeLength + 1,
                            payload.length - languageCodeLength - 1, textEncoding);

            return text;
        } catch (UnsupportedEncodingException e) {

            throw new IllegalArgumentException(e);
        }
    }

}