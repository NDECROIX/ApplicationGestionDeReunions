package com.decroix.nicolas.mareu;

import android.os.Bundle;

import com.decroix.nicolas.mareu.dependencyInjector.DependencyInjector;
import com.decroix.nicolas.mareu.model.Meeting;
import com.decroix.nicolas.mareu.service.MeetingApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ListMeetingActivity extends AppCompatActivity {

    @BindView(R.id.activity_list_meeting_rv_meetings)
    RecyclerView recyclerView;

    @BindView(R.id.activity_list_meeting_toolbar)
    Toolbar toolbar;

    private MeetingApiService meetingApiService;
    private List<Meeting> meetings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        meetingApiService = DependencyInjector.getNewInstanceApiService();
        configRecyclerView();
    }

    @OnClick(R.id.activity_list_meeting_fab)
    public void addMeeting(){ }

    private void configRecyclerView() {
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(LayoutManager);
        initList();
    }

    private void initList() {
        meetings = meetingApiService.getMeetings();
        recyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(meetings));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_sort) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
