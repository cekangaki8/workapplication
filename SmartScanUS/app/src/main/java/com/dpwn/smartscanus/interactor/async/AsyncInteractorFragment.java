package com.dpwn.smartscanus.interactor.async;

import android.view.View;

import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.interactor.InteractorFragment;
import com.dpwn.smartscanus.logging.L;
import com.dpwn.smartscanus.login.ui.LoginFragment;

/**
 * Abstract Fragment that handles the indication of the progress communicated from the web interactors.
 * Created by fshamim on 18.09.14.
 */
public abstract class AsyncInteractorFragment extends InteractorFragment implements IUnauthorizedOutputPort {

    private static final String TAG = AsyncInteractorFragment.class.getSimpleName();

    @Override
    public void indicateProgress(String loadingMsg) {
        outputPortHelper.showProgress(getActivity(), loadingMsg);
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
        dlgUtils.showDialogNegative(activity, msg, R.drawable.ic_warning_red, getString(android.R.string.ok), null);
    }

    @Override
    public void unauthorizedRequest() {
        final String msg = "Session timeout. Please log in again";
        dlgUtils.showDialogPositive(activity, msg, R.drawable.ic_warning_red, getString(android.R.string.ok), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragmentContainer != null) {
                    fragmentContainer.setContentFragment(LoginFragment.class, true, null);
                }
            }
        }, false);
    }
}
