package ca.UOIT.DevanShah.DevanSuperNotes;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

/**
 * 
 * @author Devan Shah 100428864
 *
 */
public class Notes implements Serializable, Comparable<Notes>
{

    /**
     * Default serialization constant for this object.
     */
    private static final long serialVersionUID = 1L ;
    
    // Variable Deceleration 
	public String noteTitle;
	public String noteDescription;
	public Date noteCreationDate;
	
	// Date formatter 
	static DateFormat TimeFormatter = DateFormat.getTimeInstance ();
	static DateFormat DateFormatter = DateFormat.getDateInstance();
	
	/**
	 * 
	 * @param noteTitle
	 * @param noteDescription
	 */
	public Notes ( String noteTitle, String noteDescription) {
		this.noteTitle = noteTitle;
		this.noteDescription = noteDescription;
		this.noteCreationDate = new Date() ;
	}
	
	@Override
	public String toString() {
		return noteTitle + " " + getNoteCreationDate() + ">";
	}
	
	/**
	 * 
	 * @return
	 */
	public String getNoteTitle() {
		return noteTitle;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getNoteDescription() {
		return noteDescription;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getNoteCreationDate() {
		
		// Get the current date
		Date currentDate = new Date();
		
		// If the date is still current only return the time
		if ( DateFormatter.format(currentDate).equals(DateFormatter.format(noteCreationDate)))
		{
			return TimeFormatter.format(noteCreationDate) + " >";
		}
		// When the date is not current return only the date
		else {
			return DateFormatter.format(noteCreationDate) + " >";
		}
	}

	/**
	 * 
	 */
	@Override
	public int compareTo(Notes another) {
		
		if ( this.noteCreationDate.after(another.noteCreationDate)){
			return -1;
		}
		else if ( this.noteCreationDate.before(another.noteCreationDate))
		{
			return 1;	
		}
		
		else {
			return 0;	
		}
	}
}
