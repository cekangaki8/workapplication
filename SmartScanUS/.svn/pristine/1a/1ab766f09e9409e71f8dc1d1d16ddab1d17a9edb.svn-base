package com.dpwn.smartscanus.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Customized Progress by rotating the canvas to 90 degrees to make it a vertical progress bar
 * Created by fshamim on 31.10.14.
 */
public class VerticalProgressBar extends ProgressBar {

    /**
     * Required Constructor
     * @param context
     * @param attrs
     * @param defStyle
     */
    public VerticalProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * Required Constructor
     * @param context
     * @param attrs
     */
    public VerticalProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Required Constructor
     * @param context
     */
    public VerticalProgressBar(Context context) {
        super(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldh, oldw);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.rotate(-90);
        canvas.translate(-getHeight(), 0);
        super.onDraw(canvas);
    }

}
