package com.dpwn.smartscanus.interactor.async;

import android.os.Bundle;

import com.google.inject.Inject;
import com.squareup.otto.Bus;

import java.util.List;

import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.interactor.IInteractorInputPort;
import com.dpwn.smartscanus.interactor.OutputPortHelper;
import com.dpwn.smartscanus.logging.L;
import com.dpwn.smartscanus.utils.ui.DialogUtils;
import com.dpwn.smartscanus.utils.ui.OnClickableActivity;

/**
 * Created by fshamim on 01.10.14.
 */
public abstract class AsyncInteractorActivity extends OnClickableActivity implements IAsyncInteractorOutputPort {

    private static final String TAG = AsyncInteractorActivity.class.getSimpleName();
    protected OutputPortHelper outputPortHelper;

    @Inject
    protected Bus bus;
    @Inject
    protected DialogUtils dlgUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        outputPortHelper = new OutputPortHelper();
    }


    @Override
    public void onResume() {
        super.onResume();
        outputPortHelper.register(this, getInteractors());
    }

    @Override
    public void onPause() {
        super.onPause();
        outputPortHelper.unregister();
    }


    @Override
    public void indicateProgress(String loadingMsg) {
        outputPortHelper.showProgress(this, loadingMsg);
    }

    @Override
    public void indicateProgressFinished() {
        outputPortHelper.hideProgress();
    }

    @Override
    public void unknownErrorOccurred(int statusCode, String reason) {
        outputPortHelper.hideProgress();
        final String msg = "Unknown Error occurred: " + statusCode + " " + reason;
        L.e(TAG, msg);
        dlgUtils.showDialogNegative(this, msg, R.drawable.ic_warning_red, getString(android.R.string.ok), null);
    }

    /**
     * Abstract method to get the interactors of the implementing Fragment
     *
     * @return web interactors for registeration as a output ports
     */
    public abstract List<IInteractorInputPort> getInteractors();
}
