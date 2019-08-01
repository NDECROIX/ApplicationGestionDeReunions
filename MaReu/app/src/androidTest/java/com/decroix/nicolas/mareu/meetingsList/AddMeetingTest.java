package com.decroix.nicolas.mareu.meetingsList;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.decroix.nicolas.mareu.R;
import com.decroix.nicolas.mareu.controller.activities.MeetingActivity;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withResourceName;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.decroix.nicolas.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Test AddMeetingFragment
 */
@RunWith(AndroidJUnit4.class)
public class AddMeetingTest {

    @Rule
    public ActivityTestRule<MeetingActivity> mActivityRule =
            new ActivityTestRule<>(MeetingActivity.class);
    private MeetingActivity mActivity;

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    @Before
    public void startAddMeetingFragment() {
        onView(withId(R.id.activity_list_meeting_fab)).perform(click());
        onView(withId(R.id.fragment_add_meeting)).check(matches(isDisplayed()));
    }

    @DataPoint
    public void fillInfields() {
        onView(withId(R.id.add_meeting_et_event)).perform(replaceText("Réunion A"));
        onView(withId(R.id.add_meeting_et_location)).perform(replaceText("Salle A"));
        onView(withId(R.id.add_meeting_et_date)).perform(replaceText("12/07/2020"));
        onView(withId(R.id.add_meeting_et_time)).perform(replaceText("18:45"));
        onView(withId(R.id.add_meeting_et_mails)).perform(replaceText("lamzone@gmail.com"));
    }

    /**
     * When we click on the cancel item we returns to the ListMeetingsFragment
     */
    @Test
    public void myAddMeetingFragment_clickCancel_shouldShowListMeetingsFragment() {

        onView(withContentDescription(R.string.add_meeting_home_item)).perform(click());
        onView(withId(R.id.fragment_list_meetings)).check(matches(isDisplayed()));
    }

    /**
     * When we click on the date icon, DatePickerFragment starts
     */
    @Test
    public void myAddMeeting_clickDatePicker_shouldShowDatePickerFragment() {
        onView(withContentDescription(R.string.desc_date_of_meeting_picker)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).check(matches(isDisplayed()));
    }

    /**
     * When we click on the time icon, TimePickerFragment starts
     */
    @Test
    public void myAddMeeting_clickTimePicker_shouldShowTimePickerFragment() {
        onView(withContentDescription(R.string.desc_time_of_meeting_picker)).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).check(matches(isDisplayed()));
    }

    /**
     * When we click on the date of DatePickerFragment, the date field catches it
     */
    @Test
    public void myAddMeeting_chooseTheDate_shouldShowDate() {

        onView(withId(R.id.add_meeting_et_date)).check(matches(withText("")));
        onView(withContentDescription(R.string.desc_date_of_meeting_picker)).perform(click());
        onView(withResourceName("datePicker")).check(matches(isDisplayed()));
        onView(withResourceName("button1")).perform(click());
        onView(withId(R.id.add_meeting_et_date)).check(matches(not(withText(""))));
    }

    /**
     * When we click on the time of TimePickerFragment, the time field catches it
     */
    @Test
    public void myAddMeeting_chooseTheTime_shouldShowTime() {
        onView(withId(R.id.add_meeting_et_time)).check(matches(withText("")));
        onView(withContentDescription(R.string.desc_time_of_meeting_picker)).perform(click());
        onView(withResourceName("timePicker")).check(matches(isDisplayed()));
        onView(withResourceName("button1")).perform(click());
        onView(withId(R.id.add_meeting_et_time)).check(matches(not(withText(""))));
    }

    /**
     * When we click on the save item with all the fields empty, we stay on the current fragment
     * and display the toast
     */
    @Test
    public void myAddMeeting_saveMeeting_withEmptyFields_shouldStayOnCurrentFragment() {

        onView(withId(R.id.add_meeting_item_save)).perform(click());
        onView(withId(R.id.fragment_add_meeting)).check(matches(isDisplayed()));
        onView(withResourceName("message"))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    /**
     * When we click on the save item with all the fields filled in
     * we come back to ListMeetingsFragment with one more item
     */
    @Test
    public void myAddMeeting_saveMeeting_shouldShowOneMoreItem() {
        fillInfields();
        onView(withId(R.id.add_meeting_item_save)).perform(click());
        onView(withId(R.id.fragment_list_meetings)).check(matches(isDisplayed()));
        onView(withId(R.id.activity_list_meeting_rv_meetings)).check(withItemCount(1));
    }

    /**
     * When we click on the save item with all the fields filled in
     * a Toast will be displayed
     */
    @Test
    public void myAddMeeting_saveMeeting_shouldShowToast() {
        fillInfields();
        onView(withId(R.id.add_meeting_item_save)).perform(click());
        onView(withText(R.string.toast_add_meeting))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
}
