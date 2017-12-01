package com.dpwn.smartscanus.interactor;

import android.app.ActionBar;
import android.os.Bundle;

import java.util.List;

import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.utils.ui.ScannerFragment;

/**
 * Abstract Fragment that handles the indication of the progress communicated from the web interactors.
 * Created by fshamim on 18.09.14.
 */
public abstract class InteractorFragment extends ScannerFragment implements IInteractorOutputPort {

    protected OutputPortHelper outputPortHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        outputPortHelper = new OutputPortHelper();
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(getString(R.string.actionbar_title_home));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        outputPortHelper.unregister();
    }

    @Override
    public void onResume() {
        super.onResume();
        outputPortHelper.register(this, getInteractors());
    }

    /**
     * Abstract method to get the interactors of the implementing Fragment
     *
     * @return web interactors for registration as a output ports
     */
    public abstract List<IInteractorInputPort> getInteractors();

}
