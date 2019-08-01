package com.decroix.nicolas.mareu.meetingsList;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.decroix.nicolas.mareu.R;
import com.decroix.nicolas.mareu.controller.activities.MeetingActivity;
import com.decroix.nicolas.mareu.model.Meeting;
import com.decroix.nicolas.mareu.repository.MeetingRepository;
import com.decroix.nicolas.mareu.utils.GetItemDataRecyclerView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.GregorianCalendar;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class UpdateMeetingTest {

    @Rule
    public ActivityTestRule<MeetingActivity> mActivityRule =
            new ActivityTestRule<>(MeetingActivity.class);

    private MeetingActivity mActivity;
    private MeetingRepository repository;

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        repository = mActivity.getMeetingRepository();
        addMeeting();
    }

    @DataPoint
    public void addMeeting() {
        String subject = "Subject";
        String location = "Location";
        String mails = "lamezon@lamzone.com, lamzzon@lamzone.com";
        Date date = new GregorianCalendar(2029, 6, 13, 12, 8).getTime();
        Meeting meeting = new Meeting(subject, location, date, mails);
        repository.addMeeting(meeting);
    }

    /**
     * When we click on an item, the AddMeetingFragment starts
     */
    @Test
    public void MyMeetingList_clickItem_shouldShowAddMeetingFragment() {
        onView(withId(R.id.activity_list_meeting_rv_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.fragment_add_meeting)).check(matches(isDisplayed()));
    }

    /**
     * When we update a meeting, a Toast is displayed
     */
    @Test
    public void myUpdateMeeting_updateMeeting_shouldShowToast() {
        onView(withId(R.id.activity_list_meeting_rv_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.add_meeting_item_save)).perform(click());
        onView(withText(R.string.toast_update_meeting))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    /**
     * When we update a meeting, the item is updated
     */
    @Test
    public void myUpdateMeeting_updateMeeting_shouldShowSameItem() {
        GetItemDataRecyclerView getItemDataRecyclerViewAfterUpdate = new GetItemDataRecyclerView();

        // perform a click on the item
        onView(withId(R.id.activity_list_meeting_rv_meetings)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // change the subject of the meeting and save
        onView(withId(R.id.add_meeting_et_event)).perform(replaceText("Nouvelle réunion"));
        onView(withId(R.id.add_meeting_item_save)).perform(click());

        // get data from the item
        onView(withId(R.id.activity_list_meeting_rv_meetings)).perform(RecyclerViewActions.actionOnItemAtPosition(0, getItemDataRecyclerViewAfterUpdate));

        assertEquals("Nouvelle réunion - 13 juil. 12:08 - Location", getItemDataRecyclerViewAfterUpdate.getMeetingsTitle().get(0));
    }
}
