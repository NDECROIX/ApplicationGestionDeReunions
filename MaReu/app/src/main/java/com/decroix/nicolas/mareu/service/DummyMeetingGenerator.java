package com.decroix.nicolas.mareu.service;

import com.decroix.nicolas.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETINGS = new Arrays.asList(
            new Meeting("Peach", "Réunion A", "01/07/19", "14h00", "14h45", new Arrays.asList("maxime@lamzone.com", "alex@lamzon.com")),
            new Meeting("Mario", "Réunion B", "02/07/19", "16h00", "16h45", new Arrays.asList("paul@lamzone.com", "viviane@lamzon.com")),
            new Meeting("Peach", "Réunion A", "01/07/19", "19h00", "19h45", new Arrays.asList("amandine@lamzone.com", "luc@lamzon.com"))
    );

    static List<Meeting> generateMettings() { return new ArrayList<>(DUMMY_MEETINGS);}
}
