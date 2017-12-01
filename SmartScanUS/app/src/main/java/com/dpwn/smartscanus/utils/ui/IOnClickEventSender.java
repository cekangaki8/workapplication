package com.dpwn.smartscanus.utils.ui;

import android.view.View;

/**
 * Interface for the Registration of Objects which are interested in receiving the onClick Events
 * Created by fshamim on 15.04.14.
 */
public interface IOnClickEventSender {
    /**
     * OnClick EventListener method that is to be defined in the xml files.
     * @param view widget on which the click event is produced
     */
    void onClick(View view);

    /**
     * Add onClickEvent Receiver. These are normally OnClickableFragment extending fragments
     * @param receiver receiver to be added
     */
    void addOnClickEventReceiver(IOnClickEventReceiver receiver);

    /**
     * Remove previously registered receiver. Nothing will happen if no previous
     * registration is found.
     * @param receiver receiver to be removed
     */
    void removeOnClickEventReceiver(IOnClickEventReceiver receiver);
}
