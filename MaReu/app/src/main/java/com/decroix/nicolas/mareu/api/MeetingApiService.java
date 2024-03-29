package com.decroix.nicolas.mareu.api;

import com.decroix.nicolas.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MeetingApiService implements ApiService {

    private List<Meeting> meetings = new ArrayList<>();

    /**
     * Return a list of {@link Meeting}
     */
    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    /**
     * Add a {@link Meeting} in the {@link MeetingApiService#meetings} list.
     */
    @Override
    public void addMeeting(Meeting meeting) {
        this.meetings.add(meeting);
    }

    /**
     * Delete a {@link Meeting} from the {@link MeetingApiService#meetings} list.
     */
    @Override
    public void deleteMeeting(Meeting meeting) {
        this.meetings.remove(meeting);
    }

    /**
     * Update a {@link Meeting} in the {@link MeetingApiService#meetings} list
     *
     * @param meeting meeting to update
     * @param update  new meeting
     */
    @Override
    public void updateMeeting(Meeting meeting, Meeting update) {
        for (int i = 0; i < meetings.size(); i++) {
            if (meetings.get(i).isContentIdentical(meeting)) {
                meetings.set(i, update);
                break;
            }
        }
    }

    /**
     * Sort the list of meetings by date
     */
    @Override
    public void sortByDate() {
        Collections.sort(meetings, (meetingOne, meetingTow) ->
                meetingOne.getDateTime().compareTo(meetingTow.getDateTime()));
    }

    /**
     * sort the list of meetings by meeting room
     */
    @Override
    public void sortByMeetingRoom() {
        Collections.sort(meetings, (meetingOne, meetingTow) ->
                meetingOne.getMeetingRoom().compareTo(meetingTow.getMeetingRoom()));
    }

    /**
     * Check if the meeting room is available on the scheduled date
     *
     * @param date        meeting date
     * @param meetingRoom meeting room to reserve
     * @return returns true if the meeting room is available
     */
    @Override
    public boolean isMeetingRoomAvailable(Date date, String meetingRoom, Meeting toUpdate) {
        List<Meeting> copyMeetings = new ArrayList<>(meetings);
        if (toUpdate != null) {
            copyMeetings.remove(toUpdate);
        }
        for (Meeting meeting : copyMeetings) {
            Date dateRangeA = new Date(meeting.getDateTime().getTime() - TimeUnit.MINUTES.toMillis(45));
            Date dateRangeB = new Date(meeting.getDateTime().getTime() + TimeUnit.MINUTES.toMillis(45));

            if (date.after(dateRangeA) && date.before(dateRangeB) &&
                    meeting.getMeetingRoom().contentEquals(meetingRoom)) {
                return false;
            }
        }
        return true;
    }
}
