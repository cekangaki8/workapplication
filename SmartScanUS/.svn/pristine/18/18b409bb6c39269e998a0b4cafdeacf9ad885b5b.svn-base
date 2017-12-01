package com.dpwn.smartscanus.utils.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dpwn.smartscanus.R;
import com.dpwn.smartscanus.utils.MenuElements;

import java.util.List;

/**
 * Created by cekangak on 9/15/2015.
 */
public class MenuItemAdapter extends BaseAdapter {

    /** The current Context for the given container. */
    private Context context;
    /** List of the menuItems to be loaded dynaically. */
    private List<MenuElements> mobileValues;

    public MenuItemAdapter(Context c, List<MenuElements> mobileValues) {
        this.context = c;
        this.mobileValues = mobileValues;
    }

    @Override
    public int getCount() {
        return mobileValues.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if (convertView == null) {

            gridView = inflater.inflate(R.layout.menu_display, null);

            TextView tvDetailText = (TextView) gridView.findViewById(R.id.grid_item_label);
            ImageView ivIconDisplayed = (ImageView) gridView.findViewById(R.id.grid_item_image);
            TextView tvHeaderText = (TextView) gridView.findViewById(R.id.menu_header);
            TextView tvNextFragmentId = (TextView) gridView.findViewById(R.id.etNextFragmentId);

            tvDetailText.setText(mobileValues.get(position).getMenuDetailText());
            ivIconDisplayed.setImageResource(mobileValues.get(position).getImageResourceId());
            tvHeaderText.setText(mobileValues.get(position).getMenuHeaderText());
            tvNextFragmentId.setText(String.valueOf(mobileValues.get(position).getNextFragmentId()));

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

}
