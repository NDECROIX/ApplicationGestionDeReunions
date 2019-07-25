package com.decroix.nicolas.mareu.utils;

import android.view.View;
import android.widget.TextView;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.decroix.nicolas.mareu.R;

import org.hamcrest.Matcher;

import java.util.ArrayList;
import java.util.List;

public class GetItemDataRecyclerView implements ViewAction {

    private List<String> meetingsTitle;

    public GetItemDataRecyclerView() {
        this.meetingsTitle = new ArrayList<>();
    }

    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Get the title of the item";
    }

    @Override
    public void perform(UiController uiController, View view) {
        TextView titleItem = view.findViewById(R.id.list_meeting_item_tv_title);
        meetingsTitle.add(titleItem.getText().toString());
    }

    public List<String> getMeetingsTitle() {
        return meetingsTitle;
    }
}
