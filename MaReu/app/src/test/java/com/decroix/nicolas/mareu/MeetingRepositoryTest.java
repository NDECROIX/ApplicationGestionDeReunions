package com.decroix.nicolas.mareu;

import com.decroix.nicolas.mareu.di.DependencyInjector;
import com.decroix.nicolas.mareu.model.Meeting;
import com.decroix.nicolas.mareu.repository.MeetingRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MeetingRepositoryTest {

    private List<Meeting> dummyMeetings;
    private MeetingRepository repository;

    @Before
    public void setUp() {
        repository = DependencyInjector.createMeetingRepository();
        dummyMeetings = Arrays.asList(
                new Meeting("Sujet 1", "Salle 2", new GregorianCalendar(
                        2019, 7, 31, 10, 30).getTime(),
                        "lamzone@gmail.com, lamzone@gmail.com, lamzone@gmail.com"),
                new Meeting("Sujet 2", "Salle 1", new GregorianCalendar(
                        2019, 7, 10, 10, 30).getTime(),
                        "lamzone@gmail.com, lamzone@gmail.com, lamzone@gmail.com"),
                new Meeting("Sujet 3", "Salle 3", new GregorianCalendar(
                        2019, 7, 15, 10, 30).getTime(),
                        "lamzone@gmail.com, lamzone@gmail.com, lamzone@gmail.com"));
    }

    @DataPoint
    public void addMeetingToRepository() {
        repository.addMeeting(dummyMeetings.get(0));
        repository.addMeeting(dummyMeetings.get(1));
        repository.addMeeting(dummyMeetings.get(2));
    }

    @Test
    public void getEmptyMeetingsListWithSuccess() {
        List<Meeting> meetings = repository.getMeetings();
        assertTrue(meetings.isEmpty());
    }

    @Test
    public void addMeetingWithSuccess() {
        repository.addMeeting(dummyMeetings.get(0));
        assertTrue(repository.getMeetings().contains(dummyMeetings.get(0)));
    }

    @Test
    public void deleteMeetingWithSuccess() {
        repository.addMeeting(dummyMeetings.get(0));
        repository.deleteMeeting(dummyMeetings.get(0));
        assertTrue(!repository.getMeetings().contains(dummyMeetings.get(0)));
    }

    @Test
    public void updateMeetingRoomWithSuccess() {
        addMeetingToRepository();
        Meeting update = dummyMeetings.get(0);
        update.setSubject("New Subject");
        repository.updateMeeting(dummyMeetings.get(0), update);
        assertEquals(update, dummyMeetings.get(0));
    }

    @Test
    public void sortByDateWithSuccess() {
        addMeetingToRepository();
        repository.sortByDate();

        assertEquals(dummyMeetings.get(1), repository.getMeetings().get(0));
        assertEquals(dummyMeetings.get(2), repository.getMeetings().get(1));
        assertEquals(dummyMeetings.get(0), repository.getMeetings().get(2));
    }

    @Test
    public void sortByMeetingRoomWithSuccess() {
        addMeetingToRepository();
        repository.sortByMeetingRoom();

        assertEquals(dummyMeetings.get(1), repository.getMeetings().get(0));
        assertEquals(dummyMeetings.get(0), repository.getMeetings().get(1));
        assertEquals(dummyMeetings.get(2), repository.getMeetings().get(2));
    }

    @Test
    public void checkNotAvailableMeetingRoom() {
        addMeetingToRepository();
        assertFalse(repository.availableMeetingRoom(
                new GregorianCalendar(2019, 7, 31, 10, 45)
                        .getTime(), "Salle 2", null));
    }
}
