package com.dpwn.smartscanus.utils.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by fshamim on 05.11.14.
 */
public interface IFragmentContainer {

    /**
     * One and only function to change active content fragment.
     * It takes care of checking existing content fragment calls, and it will change fragment only if new one is different
     *
     * @param newFragmentClass class of new content fragment.
     * @param anim             set to true if change should be animated, most common case. False is used only internally.
     * @return true if fragment was changed, false it current fragment was same as new one.
     */
    <T extends Fragment> boolean setContentFragment(Class<T> newFragmentClass, boolean anim, Bundle arguments);
}
