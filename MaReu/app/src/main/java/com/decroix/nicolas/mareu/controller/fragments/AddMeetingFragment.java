package com.decroix.nicolas.mareu.controller.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.decroix.nicolas.mareu.R;
import com.decroix.nicolas.mareu.events.GetDateEvent;
import com.decroix.nicolas.mareu.events.GetTimeEvent;
import com.decroix.nicolas.mareu.events.SaveMeetingEvent;
import com.decroix.nicolas.mareu.model.Meeting;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMeetingFragment extends Fragment {

    // UI Components
    @BindView(R.id.add_meeting_toolbar)
    Toolbar toolbar;
    @BindView(R.id.add_meeting_et_event)
    EditText meetingName;
    @BindView(R.id.add_meeting_et_location)
    TextInputEditText meetingLocation;
    @BindView(R.id.add_meeting_et_date)
    TextInputEditText meetingDate;
    @BindView(R.id.add_meeting_et_time)
    TextInputEditText meetingTime;
    @BindView(R.id.add_meeting_et_mails)
    TextInputEditText meetingMails;
    @BindView(R.id.add_meeting_til_date)
    TextInputLayout layoutMeetingDate;
    @BindView(R.id.add_meeting_til_start_time)
    TextInputLayout layoutMeetingTime;

    private Calendar date;

    public static void startAddMeetingFragment(FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.your_placeholder, new AddMeetingFragment());
        transaction.addToBackStack(null).commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_meeting, container, false);
        ButterKnife.bind(this, view);
        date = new GregorianCalendar();
        configToolbar();
        configListeners();
        return view;
    }

    // Listeners on the TextInputLayout drawing date and time
    private void configListeners() {
        layoutMeetingTime.setEndIconOnClickListener(v -> TimePickerFragment
                .showTimePickerDialog(getFragmentManager()));

        layoutMeetingDate.setEndIconOnClickListener(v -> DatePickerFragment
                .showDatePickerDialog(getFragmentManager()));
    }

    private void configToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_24);
        activity.getSupportActionBar().setTitle(getString(R.string.add_meeting_title_toolbar));
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_meeting, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;
            case R.id.add_meeting_item_save:
                saveMeeting();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Save the meeting in the RecyclerView
    private void saveMeeting() {
        if (allFieldsFilledIn()) {
            String name, location, emails;
            name = meetingName.getText().toString();
            location = meetingLocation.getText().toString();
            emails = meetingMails.getText().toString();

            Meeting meeting = new Meeting(name, location, date.getTime(), emails);
            EventBus.getDefault().post(new SaveMeetingEvent(meeting));
            getFragmentManager().popBackStack();
        } else {
            Toast.makeText(getContext(), R.string.one_or_more_empty_fields, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean allFieldsFilledIn() {
        return !TextUtils.isEmpty(meetingName.getText()) &&
                !TextUtils.isEmpty(meetingLocation.getText()) &&
                !TextUtils.isEmpty(meetingDate.getText()) &&
                !TextUtils.isEmpty(meetingTime.getText()) &&
                !TextUtils.isEmpty(meetingMails.getText());
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
    public void onGetDate(GetDateEvent event) {
        Calendar dateMeeting = event.date;
        date.set(dateMeeting.get(Calendar.YEAR), dateMeeting.get(Calendar.MONTH), dateMeeting.get(Calendar.DAY_OF_MONTH));

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        meetingDate.setText(dateFormat.format(dateMeeting.getTime()));
    }

    @Subscribe
    public void onGetTime(GetTimeEvent event) {
        Calendar timeMeeting = event.time;
        date.set(Calendar.HOUR_OF_DAY, timeMeeting.get(Calendar.HOUR_OF_DAY));
        date.set(Calendar.MINUTE, timeMeeting.get(Calendar.MINUTE));

        DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.FRANCE);
        meetingTime.setText(dateFormat.format(timeMeeting.getTime()));
    }

}
