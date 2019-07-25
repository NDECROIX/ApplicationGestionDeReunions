package com.decroix.nicolas.mareu.utils;

import android.view.View;
import android.widget.ImageView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.decroix.nicolas.mareu.R;

import org.hamcrest.Matcher;

public class DeleteItem implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Delete an item from the recycler view";
    }

    @Override
    public void perform(UiController uiController, View view) {
        ImageView deleteIcon = view.findViewById(R.id.list_meeting_item_iv_delete);
        deleteIcon.performClick();
    }
}
