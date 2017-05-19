package com.caiwei.customui;

import android.app.Activity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wei.cai on 2017/5/19.
 */

public class InitViews {
    private List<ButtonAndLayout> buttonAndLayouts = new ArrayList<>();

    public void initButtonAndLayout(Activity activity, int buttonId, int layoutId) {
        buttonAndLayouts.add(new ButtonAndLayout(activity, buttonId, layoutId));
    }

    private class ButtonAndLayout {
        private View button;
        private View layout;

        ButtonAndLayout(Activity activity, int buttonId, int viewId) {
            layout = activity.findViewById(viewId);
            button = activity.findViewById(buttonId);
            if (button != null) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        for (ButtonAndLayout viewEntity : buttonAndLayouts) {
                            if (v.getId() == viewEntity.button.getId()) {
                                viewEntity.layout.setVisibility(View.VISIBLE);
                            } else {
                                viewEntity.layout.setVisibility(View.GONE);
                            }
                        }
                    }
                });
            }
        }
    }

}