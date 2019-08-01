package com.decroix.nicolas.mareu.controller.activities;

import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;

import com.decroix.nicolas.mareu.R;
import com.decroix.nicolas.mareu.controller.fragments.ListMeetingsFragment;

/**
 * Main activity of the application
 */
public class MeetingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        resetMeetingRepository();
        startFragment();
    }

    private void startFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_list_meeting_placeholder, new ListMeetingsFragment());
        transaction.commit();
    }
}
