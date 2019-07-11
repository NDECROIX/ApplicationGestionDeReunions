package com.decroix.nicolas.mareu.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.decroix.nicolas.mareu.R;
import com.decroix.nicolas.mareu.events.DeleteMeetingEvent;
import com.decroix.nicolas.mareu.model.Meeting;
import com.decroix.nicolas.mareu.view.MyMeetingRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListMeetingFragment extends Fragment {

    // UI Components
    @BindView(R.id.activity_list_meeting_rv_meetings)
    RecyclerView recyclerView;
    @BindView(R.id.activity_list_meeting_toolbar)
    Toolbar toolbar;

    private List<Meeting> meetings;

    public ListMeetingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_meeting, container, false);
        ButterKnife.bind(this, view);
        configToolbar();
        configRecyclerView();
        return view;
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
    public void onDeleteMeeting(DeleteMeetingEvent event) {
        meetings.remove(event.meeting);
        initList();
    }

    private void configToolbar() {

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_sort_date:
                sortMeetingDate();
                return true;
            case R.id.action_sort_salle:
                sortMeetingLocation();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void sortMeetingDate() {
        Collections.sort(meetings, (meetingOne, meetingTow) ->
                meetingOne.getDateTime().compareTo(meetingTow.getDateTime()));
        initList();
    }

    private void sortMeetingLocation() {
        Collections.sort(meetings, (meetingOne, meetingTow) ->
                meetingOne.getLocation().compareTo(meetingTow.getLocation()));
        initList();
    }

    @OnClick(R.id.activity_list_meeting_fab)
    public void addMeeting() {
        AddMeetingFragment.startAddMeetingFragment(getFragmentManager());
    }

    private void configRecyclerView() {
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(LayoutManager);
        initList();
    }

    private void initList() {
        if (meetings == null) meetings = new ArrayList<>();
        recyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(meetings));
    }

    public void addMeetingInRecyclerView(Meeting meeting) {
        meetings.add(meeting);
        initList();
    }

}
