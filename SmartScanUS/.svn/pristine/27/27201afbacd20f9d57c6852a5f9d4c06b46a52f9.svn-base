package com.dpwn.smartscanus.nfc.helper;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;

import java.util.List;

import com.dpwn.smartscanus.nfc.NdefMessageParser;

/**
 * Created by jrodriguez on 03/09/14.
 */
/**
 * NFCHelper implements the interface of the component NFC
 * */
public class NFCHelper implements INFCHelper{

    /**
     * Constructor
     * */
    public NFCHelper() {
    }
    /**
     * Getting a new intent this method read what is attached to the Intent and figure out which is
     * the first record.
     * */
    public String resolveIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
                if (msgs == null || msgs.length == 0) {
                    return "";
                }
                List<String> records = NdefMessageParser.parse(msgs[0]);
                return records.get(0);
            }
        }
        return "";
    }


}
