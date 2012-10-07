package smp.components.staff.sequences;

import java.util.ArrayList;

/**
 * A line of notes on the staff. This can include
 * notes, bookmarks, etc.
 * @author Derek
 *
 */
public class StaffNoteLine {

    /**
     * The line number of this StaffNoteLine. For a standard 96-measure
     * song, this number is between 0 and 383.
     */
    private int lineNum;

    /**
     * This ArrayList holds staff notes inside it.
     */
    private ArrayList<StaffNote> notes;

    /**
     * This ArrayList holds staff events in it.
     */
    private ArrayList<StaffEvent> marks;

    /**
     * Creates a new staff note line.
     */
    public StaffNoteLine() {

    }

    public void add(StaffNote n) {

    }

    public void delete(int index) {

    }

    public void delete(StaffNote n) {

    }

}