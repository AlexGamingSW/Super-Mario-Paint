package smp.models.staff;

import java.io.Serializable;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import smp.components.Values;
import smp.models.stateMachine.TimeSignature;

/**
 * We might not even need MIDI to do this sequencing stuff. This class keeps
 * track of whatever is displaying on the staff right now.
 *
 * @author RehdBlob
 * @since 2013.08.23
 */
public class StaffSequence implements Serializable {

    /**
     * Generated serial ID.
     */
    private static final long serialVersionUID = 5752285850525402081L;

    /**
     * The tempo of this sequence.
     */
    private DoubleProperty tempo  = new SimpleDoubleProperty(Values.DEFAULT_TEMPO);

    /** These are all of the lines on the staff. */
    private ObservableList<StaffNoteLine> theLines = FXCollections.observableArrayList();
    
    /** @since v1.1.3*/
    private ReadOnlyIntegerProperty theLinesSize = new SimpleListProperty<>(theLines).sizeProperty();

    /** This tells us which notes are extended (green highlight) or not. */
    private BooleanProperty[] noteExtensions = new BooleanProperty[Values.NUMINSTRUMENTS];

    /** The time signature of this sequence. */
    private ObjectProperty<TimeSignature> t = new SimpleObjectProperty<TimeSignature>(TimeSignature.FOUR_FOUR);
    
    /** The soundset bound to and should be loaded for this sequence. */
    private StringProperty soundsetBinding = new SimpleStringProperty("");

    /** Default constructor. Makes an empty song. */
    public StaffSequence() {
        for (int i = 0; i < Values.DEFAULT_LINES_PER_SONG; i++)
            theLines.add(new StaffNoteLine());
        for (int i = 0; i < Values.NUMINSTRUMENTS; i++)
        	noteExtensions[i] = new SimpleBooleanProperty();
    }

    /**
     * @param i
     *            The index that we want to get some line from.
     * @return Gets a <code>StaffNoteLine</code> that resides at index i.
     */
    public StaffNoteLine getLine(int i) {
        try {
            return theLines.get(i);
        } catch (IndexOutOfBoundsException e) {
            theLines.add(new StaffNoteLine());
            try {
                return theLines.get(i);
            } catch (IndexOutOfBoundsException e2) {
                return getLine(i);
            }
        }
    }

    /**
     * @return The entire list of the StaffNoteLines of this song.
     */
    public ObservableList<StaffNoteLine> getTheLines() {
        return theLines;
    }

    /**
     * @param i
     *            The index that we want to modify.
     * @param s
     *            The StaffNoteLine that we want to place at this index.
     */
    public void setLine(int i, StaffNoteLine s) {
        theLines.set(i, s);
    }

    /**
     * Adds a line into this sequence.
     *
     * @param s
     *            The StaffNoteLine that we want to add.
     */
    public void addLine(StaffNoteLine s) {
        theLines.add(s);
    }

    /**
     * Adds a line into this sequence.
     *
     * @param i
     *            The index at which we want to add this line.
     * @param s
     *            The StaffNoteLine that we want to add.
     */
    public void addLine(int i, StaffNoteLine s) {
        theLines.add(i, s);
    }

    /**
     * Removes a line from this sequence.
     *
     * @param s
     *            The StaffNoteLine that we want to delete.
     */
    public void deleteLine(StaffNoteLine s) {
        theLines.remove(s);
    }

    /**
     * Removes a line from this sequence.
     *
     * @param i
     *            The line index we want to delete from.
     */
    public void deleteLine(int i) {
        theLines.remove(i);
    }

    /** @return The tempo of this sequence. */
    public DoubleProperty getTempo() {
        return tempo;
    }

    /**
     * Sets the tempo of this sequence.
     *
     * @param t
     *            The tempo of this sequence.
     */
    public void setTempo(double t) {
        tempo.set(t);
    }

    /**
     * @param i
     *            The note extensions bitfield that we want to set.
     */
    public void setNoteExtensions(boolean[] i) {
    	for(int idx = 0; idx < Values.NUMINSTRUMENTS; idx++)
    		noteExtensions[idx].set(i[idx]);
    }    
    
    /**
     * @param i
     *            The note extensions bitfield that we want to set.
     */
    public void setNoteExtensions(BooleanProperty[] i) {
    	for(int idx = 0; idx < Values.NUMINSTRUMENTS; idx++)
    		noteExtensions[idx].set(i[idx].get());
    }

    /** @return The bitfield denoting which notes are extended. */
    public BooleanProperty[] getNoteExtensions() {
        return noteExtensions;
    }

    /**
     * @param s
     *            The time signature to set this <code>StaffSequence</code> to.
     */
    public void setTimeSignature(String s) {
        int top = Integer.parseInt(s.substring(0, s.indexOf("/")));
        int bottom = Integer.parseInt(s.substring(s.indexOf("/") + 1));
        for (TimeSignature tSig : TimeSignature.values()) {
            if (tSig.bottom() == bottom && tSig.top() == top) {
                t.set(tSig);
                break;
            }
        }
        if (t.get() == null) {
            t.set(TimeSignature.FOUR_FOUR);
        }
    }

    /** @return The time signature of this sequence. */
    public ObjectProperty<TimeSignature> getTimeSignature() {
        return t;
    }

	/**
	 * Sets the soundset for this sequence which should be loaded with the
	 * sequence.
	 * @since v1.1.2
	 */
	public void setSoundset(String soundset) {
		soundsetBinding.set(soundset);
    }

	/**
	 * @return The soundset bound to this sequence.
	 * @since v1.1.2
	 */
	public StringProperty getSoundset() {
		return soundsetBinding;
    }
	
	/**
	 * @return IntegerProperty of theLines's size
	 * @since v1.1.3
	 */
	public ReadOnlyIntegerProperty getTheLinesSize() {
		return theLinesSize;
	}
    
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append("Tempo = " + tempo + "\n");
        out.append("Extensions = " + noteExtensions + "\n");
        out.append(theLines.toString() + "\n");
        return out.toString();
    }

}
