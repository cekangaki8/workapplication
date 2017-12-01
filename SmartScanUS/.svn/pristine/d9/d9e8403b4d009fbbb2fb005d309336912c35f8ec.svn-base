package com.dpwn.smartscanus.utils.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by jrodriguez on 05/11/14.
 */
public class ExpandableHeightListViewList extends ListView {

    boolean expanded = false;
    /**
     * Constructor
     */
    public ExpandableHeightListViewList(Context context){
        super(context);
    }
    /**
     * Constructor with parameters
     */
    public ExpandableHeightListViewList(Context context, AttributeSet attrs){
        super(context, attrs);
    }
    /**
     * Constructor with parameters
     */
    public ExpandableHeightListViewList(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }
    /**
     * Getter for expandable property
     */
    public boolean isExpanded() {
        return expanded;
    }

    /**
     * Expanding ListView
     */
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        if (isExpanded()){
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);

            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = getMeasuredHeight();
        } else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * Setter for expandable property
     */
    public void setExpanded(boolean expanded){
        this.expanded = expanded;
    }
}