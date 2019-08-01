package com.decroix.nicolas.mareu.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.decroix.nicolas.mareu.R;
import com.decroix.nicolas.mareu.controller.activities.MeetingActivity;
import com.decroix.nicolas.mareu.model.Meeting;
import com.decroix.nicolas.mareu.repository.MeetingRepository;
import com.decroix.nicolas.mareu.view.MyMeetingRecyclerViewAdapter;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ListMeetingsFragment that implement all meetings
 */
public class ListMeetingsFragment extends Fragment implements MyMeetingRecyclerViewAdapter.OnClickMeetingListener {

    // UI Components
    @BindView(R.id.activity_list_meeting_rv_meetings)
    RecyclerView recyclerView;
    @BindView(R.id.activity_list_meeting_toolbar)
    Toolbar toolbar;

    private MeetingRepository repository;
    private MyMeetingRecyclerViewAdapter adapter;

    public ListMeetingsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = ((MeetingActivity) getActivity()).getMeetingRepository();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_meeting, container, false);
        ButterKnife.bind(this, view);
        configToolbar();
        configRecyclerView();
        return view;
    }

    private void configToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            setHasOptionsMenu(true);
        }
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
                sortMeetingRoom();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Sort meetings by Date
    private void sortMeetingDate() {
        repository.sortByDate();
        loadData();
    }

    // Sort meetings by meeting room
    private void sortMeetingRoom() {
        repository.sortByMeetingRoom();
        loadData();
    }

    @OnClick(R.id.activity_list_meeting_fab)
    void addMeeting() {
        AddMeetingFragment.startsAddMeetingFragment(getFragmentManager(), null);
    }

    private void configRecyclerView() {
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(LayoutManager);
        adapter = new MyMeetingRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
        loadData();
    }

    @VisibleForTesting
    public void loadData() {
        adapter.updateList(repository.getMeetings());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClickMeeting(Meeting meeting) {
        AddMeetingFragment.startsAddMeetingFragment(getFragmentManager(), meeting);
    }

    @Override
    public void onClickDeleteMeeting(Meeting meeting) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.title_delete_alert_dialog).setMessage(meeting.meetingTitleToString())
                .setPositiveButton(R.string.delete_button_alert_dialog, (dialog, id) -> {
                    repository.deleteMeeting(meeting);
                    Toast.makeText(getContext(), R.string.toast_message_alert_dialog, Toast.LENGTH_SHORT).show();
                    loadData();
                    dialog.dismiss();
                }).setNegativeButton(R.string.back_button_alert_dialog, ((dialog, id) -> dialog.cancel())).show();
    }
}
