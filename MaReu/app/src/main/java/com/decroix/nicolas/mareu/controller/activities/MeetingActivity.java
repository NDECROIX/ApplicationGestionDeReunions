package com.decroix.nicolas.mareu.controller.activities;

import android.os.Bundle;

import com.decroix.nicolas.mareu.controller.fragments.ListMeetingFragment;
import com.decroix.nicolas.mareu.R;
import com.decroix.nicolas.mareu.events.SaveMeetingEvent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MeetingActivity extends AppCompatActivity {

    private ListMeetingFragment listMeetingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);
        startFragment();
    }

    private void startFragment() {
        listMeetingFragment = new ListMeetingFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.your_placeholder, listMeetingFragment);
        transaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onSaveMeeting(SaveMeetingEvent event){
        listMeetingFragment.addMeetingInRecyclerView(event.meeting);
    }
}
