package com.dpwn.smartscanus.utils.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.otto.Bus;

import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.events.TTSEvent;
import com.dpwn.smartscanus.events.VibrateEvent;
import com.dpwn.smartscanus.logging.L;
import com.dpwn.smartscanus.utils.PrefUtils;
import com.dpwn.smartscanus.utils.PrefsConsts;
import com.dpwn.smartscanus.utils.Utils;

/**
 * Custom Dialog as new alert dialog for the system
 * Created by jrodriguez on 03/11/14.
 * modified by fshamim on 05.11.14
 */
public class CustomizedDialog extends Dialog implements View.OnClickListener {

    private static final java.lang.String TAG = CustomizedDialog.class.getSimpleName();
    private final Bus bus;
    private final PrefUtils prefUtils;
    private final Activity activity;
    private boolean ttsOn;
    private String message;
    private boolean showNegative = true;
    private int drawableId;
    private String positiveButtonText;
    private String negativeButtonText;
    private String hintText;
    private View.OnClickListener btnPositiveOnClickListener;
    private View.OnClickListener btnNegativeOnClickListener;
    private boolean showPositive;
    private boolean vibOn;
    private EditText edtInput;
    private boolean isPasswordType;
    private boolean showPowerDialog;

    /**
     * Standard Constructor needs activity and the bus for doTTS
     *
     * @param activity containing this dialog
     * @param bus      for doTTS event
     */
    public CustomizedDialog(Activity activity, Bus bus, PrefUtils prefUtils) {
        super(activity);
        this.activity = activity;
        this.bus = bus;
        this.prefUtils = prefUtils;
        drawableId = 0;
        this.negativeButtonText = null;
        this.positiveButtonText = null;
        this.btnNegativeOnClickListener = null;
        this.btnPositiveOnClickListener = null;
        this.message = null;
        this.hintText = null;
        showNegative = showPositive = false;
        vibOn = ttsOn = true;
        showPowerDialog = false;
    }

    /**
     * Builder pattern setter for message
     *
     * @param message
     * @return
     */
    public CustomizedDialog setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Builder pattern setter for drawables
     *
     * @param drawableId
     * @return
     */
    public CustomizedDialog setDrawable(int drawableId) {
        this.drawableId = drawableId;
        return this;
    }

    /**
     * Builder pattern setter for positive button
     *
     * @param text            for button
     * @param onClickListener click listener for button
     * @return
     */
    public CustomizedDialog setPositiveButton(String text, View.OnClickListener onClickListener) {
        this.showPositive = true;
        this.positiveButtonText = text;
        this.btnPositiveOnClickListener = onClickListener;
        return this;
    }

    /**
     * Builder pattern setter for negative button
     *
     * @param text            for button
     * @param onClickListener click listener for button
     * @return
     */
    public CustomizedDialog setNegativeButton(String text, View.OnClickListener onClickListener) {
        this.showNegative = true;
        this.negativeButtonText = text;
        this.btnNegativeOnClickListener = onClickListener;
        return this;
    }

    public CustomizedDialog setEditText(String hint, boolean isPasswordType) {
        this.hintText = hint;
        this.isPasswordType = isPasswordType;
        return this;
    }

    /**
     * Builder pattern setter for tts on or of
     *
     * @param ttsOn true is on
     * @return
     */
    public CustomizedDialog setTTSOn(boolean ttsOn) {
        this.ttsOn = ttsOn;
        return this;
    }

    /**
     * Builder pattern setter for vibration on or of
     *
     * @param vibOn true is on
     * @return
     */
    public CustomizedDialog setVibOn(boolean vibOn) {
        this.vibOn = vibOn;
        return this;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_warning);
        setCancelable(false);

        View imgTTSSpeaker = findViewById(R.id.ll_tts_speaker);
        imgTTSSpeaker.setOnClickListener(this);

        TextView tvMessage = (TextView) findViewById(R.id.tv_dialog_message);
        tvMessage.setText(message);
        if (drawableId != 0) {
            tvMessage.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, drawableId);
        }
        edtInput = (EditText) findViewById(R.id.edt_input);
        if (hintText != null) {
            edtInput.setVisibility(View.VISIBLE);
            edtInput.setHint(hintText);
            if (isPasswordType) {
                edtInput.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
        }

        Button btnNegative = (Button) findViewById(R.id.btn_dialog_negative);
        if (showNegative) {
            if (btnNegativeOnClickListener == null) {
                btnNegative.setOnClickListener(this);
            } else {
                btnNegative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                        btnNegativeOnClickListener.onClick(view);
                    }
                });
            }
            if (negativeButtonText != null) {
                btnNegative.setText(negativeButtonText);
            } else {
                btnNegative.setText(android.R.string.cancel);
            }
        } else {
            btnNegative.setVisibility(View.GONE);
        }

        Button btnPositive = (Button) findViewById(R.id.btn_dialog_positive);
        if (showPositive) {
            if (btnPositiveOnClickListener == null) {
                btnPositive.setOnClickListener(this);
            } else {
                btnPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dismiss();
                        btnPositiveOnClickListener.onClick(view);
                    }
                });
            }
            if (positiveButtonText != null) {
                btnPositive.setText(positiveButtonText);
            } else {
                btnPositive.setText(android.R.string.ok);
            }
        } else {
            btnPositive.setVisibility(View.GONE);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus && !prefUtils.isAdminMode()) {
            // Close every kind of system dialog except power dialog
            if (!showPowerDialog) {
                Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                activity.sendBroadcast(closeDialog);
            } else {
                showPowerDialog = false;
            }
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        L.d(TAG, "dispatchKeyEvent" + event);
        if (Utils.isKeyBlocked(event.getKeyCode()) && !prefUtils.isAdminMode()) {
            showPowerDialog = Utils.checkToAllowPowerDialog(event, showPowerDialog);
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }

    @Override
    public void show() {
        super.show();
        if (ttsOn) {
            doTTS();
        }
        if (vibOn) {
            doVibOn();
        }
    }

    private void doVibOn() {
        bus.post(new VibrateEvent(PrefsConsts.VIB_DURATION));
    }

    private void doTTS() {
        bus.post(new TTSEvent(this.message));
    }

    /*
     * getInputText function provides us the user´s input
     */
    public String getInputText() {
        return edtInput.getText().toString();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_dialog_negative || v.getId() == R.id.btn_dialog_positive) {
            dismiss();
        } else if (v.getId() == R.id.ll_tts_speaker) {
            doTTS();
        }
    }
}
