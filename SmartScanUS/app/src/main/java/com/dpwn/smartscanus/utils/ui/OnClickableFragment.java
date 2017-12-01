package com.dpwn.smartscanus.utils.ui;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.inject.Inject;

import com.dpwn.smartscanus.logging.L;
import roboguice.RoboGuice;
import roboguice.fragment.RoboFragment;

/**
 * A fragment that allows the Clickable functionality to all its widgets.
 * Add onClick attribute in the xml file for receiving the event.
 * Created by fshamim on 18.09.14.
 */
public abstract class OnClickableFragment extends RoboFragment implements IOnClickEventReceiver {
    private static final String TAG = OnClickableFragment.class.getSimpleName();
    private IOnClickEventSender mListener;

    protected IFragmentContainer fragmentContainer;
    protected FragmentActivity activity;

    @Inject
    protected DialogUtils dlgUtils;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.activity = (FragmentActivity) activity;
            mListener = (IOnClickEventSender) activity;
            mListener.addOnClickEventReceiver(this);
            fragmentContainer = (IFragmentContainer) activity;
        } catch (ClassCastException e) {
            L.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        L.d(TAG, "onConfigurationChange");
        super.onConfigurationChanged(newConfig);
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        ViewGroup viewGroup = (ViewGroup) getView();
        // remove and inflate view again since the main activity doesn't cause the recreation
        if (viewGroup != null) {
            viewGroup.removeAllViewsInLayout();
            inflater.inflate(getLayoutId(), viewGroup);
            RoboGuice.getInjector(getActivity()).injectViewMembers(this);
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        if (mListener != null) {
            mListener.removeOnClickEventReceiver(this);
        }
        mListener = null;
    }

    /**
     * Abstract method for initializing the layout with the default method overrides like
     * onCreateView etc.
     * @return
     */
    public abstract int getLayoutId();
}
