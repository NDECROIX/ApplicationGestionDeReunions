package com.decroix.nicolas.mareu.meetingsList;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.decroix.nicolas.mareu.R;
import com.decroix.nicolas.mareu.controller.activities.MeetingActivity;
import com.decroix.nicolas.mareu.model.Meeting;
import com.decroix.nicolas.mareu.repository.MeetingRepository;
import com.decroix.nicolas.mareu.utils.DeleteItem;
import com.decroix.nicolas.mareu.utils.GetItemDataRecyclerView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import static androidx.test.espresso.Espresso.onIdle;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.decroix.nicolas.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Test ListMeetingsFragment
 */
@RunWith(AndroidJUnit4.class)
public class MeetingListTest {

    private static final int COUNT_MEETINGS = 3;

    @Rule
    public ActivityTestRule<MeetingActivity> mActivityRule =
            new ActivityTestRule<>(MeetingActivity.class);

    private MeetingActivity mActivity;
    private List<Meeting> meetings;
    private MeetingRepository repository;

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
        repository = mActivity.getMeetingRepository();
        fillMeetingsList();
    }

    @DataPoint
    public void fillMeetingsList() {
        meetings = Arrays.asList(
                new Meeting("Sujet 1", "Salle 2", new GregorianCalendar(2019, 7, 31).getTime(),
                        "lamzone@gmail.com, lamzone@gmail.com, lamzone@gmail.com"),
                new Meeting("Sujet 2", "Salle 1", new GregorianCalendar(2019, 7, 10).getTime(),
                        "lamzone@gmail.com, lamzone@gmail.com, lamzone@gmail.com"),
                new Meeting("Sujet 3", "Salle 3", new GregorianCalendar(2019, 7, 15).getTime(),
                        "lamzone@gmail.com, lamzone@gmail.com, lamzone@gmail.com")
        );

        repository.addMeeting(meetings.get(0));
        repository.addMeeting(meetings.get(1));
        repository.addMeeting(meetings.get(2));
    }

    /**
     * We ensure that our RecyclerView is displaying at least on item
     */
    @Test
    public void myMeetingsList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.activity_list_meeting_rv_meetings))
                .check(withItemCount(COUNT_MEETINGS));
    }

    /**
     * When we click on item delete from the RecyclerView an AlertDialog is displayed
     */
    @Test
    public void myMeetingList_clickDelete_shouldShowAlertDialog(){
        onView(withId(R.id.activity_list_meeting_rv_meetings)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteItem()));
        onView(withText(R.string.title_delete_alert_dialog)).check(matches(isDisplayed()));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myMeetingsList_deleteItem_shouldRemoveItem() {
        onIdle();
        onView(withId(R.id.activity_list_meeting_rv_meetings)).check(withItemCount(repository.getMeetings().size()));
        onView(withId(R.id.activity_list_meeting_rv_meetings)).perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteItem()));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.activity_list_meeting_rv_meetings)).check(withItemCount(COUNT_MEETINGS - 1));
    }

    /**
     * When we click on one item, AddMeetingFragment should be show
     */
    @Test
    public void MyMeetingList_clickItem_shouldShowAddMeetingFragment() {
        onView(withId(R.id.activity_list_meeting_rv_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.fragment_add_meeting)).check(matches(isDisplayed()));
    }

    /**
     * When we click on the floating action button, AddMeetingFragment should be show
     */
    @Test
    public void myMeetingList_clickFab_shouldShowAddMeetingFragment() {

        onView(withId(R.id.activity_list_meeting_fab)).perform(click());
        onView(withId(R.id.fragment_add_meeting)).check(matches(isDisplayed()));
    }

    /**
     * When we click on the sort item on the toolbar, the submenu is displayed
     */
    @Test
    public void myListMeetingsToolbar_clickSortItem_shouldShowSubMenu() {

        // perform a click on the icon sort
        onView(withId(R.id.action_sort)).perform(click());

        // check the item "Trier par date"
        onView(withText(R.string.item_sort_by_date)).check(matches(isDisplayed()));
        // check the item "Trier par salle"
        onView(withText(R.string.item_sort_by_room)).check(matches(isDisplayed()));

    }

    /**
     * When we sort meetings by date, the meetings have the right position
     */
    @Test
    public void myMeetingsList_shouldBeSortByDate() {

        GetItemDataRecyclerView getItemDataRecyclerView = new GetItemDataRecyclerView();

        // perform a click on the sort icon
        onView(withId(R.id.action_sort)).perform(click());

        // perform a click on the item "Trier par date"
        onView(withText(R.string.item_sort_by_date)).perform(click());

        // get all titles
        onView(withId(R.id.activity_list_meeting_rv_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, getItemDataRecyclerView));
        onView(withId(R.id.activity_list_meeting_rv_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, getItemDataRecyclerView));
        onView(withId(R.id.activity_list_meeting_rv_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, getItemDataRecyclerView));

        // check position of titles
        assertEquals(getItemDataRecyclerView.getMeetingsTitle().get(0), meetings.get(1).meetingTitleToString());
        assertEquals(getItemDataRecyclerView.getMeetingsTitle().get(1), meetings.get(2).meetingTitleToString());
        assertEquals(getItemDataRecyclerView.getMeetingsTitle().get(2), meetings.get(0).meetingTitleToString());
    }

    /**
     * When we sort meetings by meeting room, the meetings have the right position
     */
    @Test
    public void myMeetingsList_shouldBeSortByRoom() {

        GetItemDataRecyclerView getItemDataRecyclerView = new GetItemDataRecyclerView();

        // perform a click on the icon sort
        onView(withId(R.id.action_sort)).perform(click());

        // perform a click on the item "Trier par salle"
        onView(withText(R.string.item_sort_by_room)).perform(click());

        // get all titles
        onView(withId(R.id.activity_list_meeting_rv_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, getItemDataRecyclerView));
        onView(withId(R.id.activity_list_meeting_rv_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, getItemDataRecyclerView));
        onView(withId(R.id.activity_list_meeting_rv_meetings))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, getItemDataRecyclerView));

        // check position of titles
        assertEquals(getItemDataRecyclerView.getMeetingsTitle().get(0), meetings.get(1).meetingTitleToString());
        assertEquals(getItemDataRecyclerView.getMeetingsTitle().get(1), meetings.get(0).meetingTitleToString());
        assertEquals(getItemDataRecyclerView.getMeetingsTitle().get(2), meetings.get(2).meetingTitleToString());
    }
}
