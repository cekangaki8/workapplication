package com.dpwn.smartscanus.events;

/**
 * Event Pojo for showing a toast in the app.
 * Created by fshamim on 04.09.14.
 */
public class ShowToastEvent {
    private final ToastDuration duration;
    private final String text;
    private ToastColor color;
    private TextSize  textSize;


    /**
     * Enum for determining the duration of the toast.
     */

    public enum ToastDuration {
        SHORT, LONG
    }

    /**
     *
     */
    public enum ToastColor {
        RED, GREEN, BLACK, BLUE, PURPLE, ORANGE, GREY
    }

    public enum TextSize{
        SMALL, MEDIUM, LARGE
    }
    /**
     * Constructor
     *
     * @param text   text to be shown in the toast
     * @param period duration of the toast, long or short.
     */
    public ShowToastEvent(String text, ToastDuration period) {
        this.text = text;
        this.duration = period;
        this.color = ToastColor.BLACK;
        this.textSize = TextSize.LARGE;
    }

    public ToastDuration getDuration() {
        return duration;
    }

    public String getText() {
        return text;
    }

    public ShowToastEvent setColor(ToastColor color){
        this.color = color;
        return this;
    }

    public ShowToastEvent setTextSize(TextSize textSize){
        this.textSize = textSize;
        return this;
    }

    public ToastColor getColor() {
        return color;
    }

    public TextSize getTextSize() {
        return textSize;
    }
}
