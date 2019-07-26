package com.decroix.nicolas.mareu.controller.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.decroix.nicolas.mareu.R;
import com.decroix.nicolas.mareu.controller.activities.MeetingActivity;
import com.decroix.nicolas.mareu.model.Meeting;
import com.decroix.nicolas.mareu.repository.MeetingRepository;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Fragment that implements the creation of a meeting
 */
public class AddMeetingFragment extends Fragment
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, MailPickerFragment.OnMailsSelectedListener {

    private static final String MEETING_ARGUMENT = "meetingArgument";

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
    @BindView(R.id.add_meeting_til_mails)
    TextInputLayout layoutMeetingMails;

    private MeetingRepository repository;
    private Date date;
    private boolean update;
    private Meeting meetingToUpdate;

    //Starts the AddMeetingFragment
    static void startsAddMeetingFragment(FragmentManager fragmentManager, @Nullable Meeting meeting) {
        AddMeetingFragment addMeetingFragment = new AddMeetingFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(MEETING_ARGUMENT, meeting);
        addMeetingFragment.setArguments(bundle);

        fragmentManager.beginTransaction()
                .replace(R.id.activity_list_meeting_placeholder, addMeetingFragment)
                .addToBackStack(null).commit();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = ((MeetingActivity) getActivity()).getMeetingRepository();
    }

    private void setDetails() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            meetingToUpdate = (Meeting) bundle.get(MEETING_ARGUMENT);
        }
        if (meetingToUpdate != null) {
            meetingName.setText(meetingToUpdate.getSubject());
            meetingLocation.setText(meetingToUpdate.getLocation());
            meetingDate.setText(meetingToUpdate.getDateString());
            meetingTime.setText(meetingToUpdate.getTimeString());
            meetingMails.setText(meetingToUpdate.getEmails());
            update = true;
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_meeting, container, false);
        ButterKnife.bind(this, view);
        configToolbar();
        configListeners();
        setDetails();
        return view;
    }

    private void configToolbar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeActionContentDescription(getString(R.string.add_meeting_home_item));
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_24);
        activity.getSupportActionBar().setTitle(getString(R.string.add_meeting_title_toolbar));
        setHasOptionsMenu(true);
    }

    // Listeners on the TextInputLayout drawing date and time
    private void configListeners() {
        layoutMeetingDate.setEndIconOnClickListener(v ->
                new DatePickerFragment(this).show(getFragmentManager(), getString(R.string.date_picker_tag)));

        layoutMeetingTime.setEndIconOnClickListener(v ->
                new TimePickerFragment(this).show(getFragmentManager(), getString(R.string.time_picker_tag)));

        layoutMeetingMails.setEndIconOnClickListener(v ->
                new MailPickerFragment(this).show(getFragmentManager(), getString(R.string.mail_picker_tag)));
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        meetingDate.setText(day + "/" + (month + 1) + "/" + year);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        meetingTime.setText(hour + ":" + String.format("%02d", minute));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_meeting, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onMailsSelected(List<String> emails) {
        if (!TextUtils.isEmpty(meetingMails.getText()))
            meetingMails.append(getString(R.string.mails_splitter));
        meetingMails.append(TextUtils.join(getString(R.string.mails_splitter), emails));
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

    // Save the meeting
    private void saveMeeting() {
        if (checkMeeting()) {
            String name, location, emails;
            name = meetingName.getText().toString();
            location = meetingLocation.getText().toString();
            emails = meetingMails.getText().toString();
            Meeting meeting = new Meeting(name, location, date, emails);
            if (update){
                repository.updateMeeting(meetingToUpdate, meeting);
                showToast(R.string.toast_update_meeting);
            }
            else{
                repository.addMeeting(meeting);
                showToast(R.string.toast_add_meeting);
            }
            getFragmentManager().popBackStack();
        }
    }

    // Check the data
    private boolean checkMeeting() {
        if (!allFieldsFilledIn()) {
            showToast(R.string.toast_empty_fields);
        } else if (!checkDateTimeFormat()) {
            showToast(R.string.toast_error_date_time_format);
        } else if (!checkAvailability()) {
            showToast(R.string.toast_room_used);
        } else if (!checkMailsInput()) {
            showToast(R.string.toast_error_mails_input);
        } else {
            return true;
        }
        return false;
    }

    private void showToast(int message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Check all fields
     *
     * @return returns true if all has been completed
     */
    private boolean allFieldsFilledIn() {
        return !TextUtils.isEmpty(meetingName.getText()) &&
                !TextUtils.isEmpty(meetingLocation.getText()) &&
                !TextUtils.isEmpty(meetingDate.getText()) &&
                !TextUtils.isEmpty(meetingTime.getText()) &&
                !TextUtils.isEmpty(meetingMails.getText());
    }

    /**
     * Check the date and time format in the fields
     *
     * @return returns true if the format is correct
     */
    private boolean checkDateTimeFormat() {
        DateFormat dateTimeFormat = new SimpleDateFormat(getText(R.string.date_time_format).toString(), Locale.FRANCE);
        try {
            date = dateTimeFormat.parse(meetingDate.getText().toString() + meetingTime.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Check if the meeting room is not occupied at that time.
     *
     * @return returns true if the meeting room is free
     */
    private boolean checkAvailability() {
        if (date.before(new Date())) {
            return false;
        } else {
            return repository.availableMeetingRoom(date, meetingLocation.getText().toString(), meetingToUpdate);
        }
    }

    /**
     * Check if the emails of the participants match the email template.
     */
    private boolean checkMailsInput() {
        Pattern mailPattern = Patterns.EMAIL_ADDRESS;
        String mails = meetingMails.getText().toString().toLowerCase();
        List<String> mailsList = new ArrayList<>(Arrays.asList(mails.split(getString(R.string.mails_splitter))));
        for (String mail : mailsList) {
            if (!mailPattern.matcher(mail).matches()) return false;
        }
        return true;
    }
}
