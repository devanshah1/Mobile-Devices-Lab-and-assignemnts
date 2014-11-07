package ca.UOIT.DevanShah.DevanSuperNotes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 
 * @author Devan Shah 100428864
 *
 */
@SuppressLint({ "UseSparseArrays", "UseValueOf", "NewApi" })
public class NotesStart extends ActionBarActivity {

	// public ArrayList<Notes> notes;
	public Vector<Object> notes;
	public NotesDescription myNotesDescription;
	public NotesList myNotesList;
	public File notesRawDataFile ;
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		notes = new Vector<Object>() ;

		System.out.println(getFilesDir() + "/NotesRawData.ser");
		
        // Create the File handle for the file that is checked if an restore is needed
        notesRawDataFile = new File ( getFilesDir(), "NotesRawData.ser" ) ;

        // Perform the restore only if the file exists.
        if ( notesRawDataFile.exists() ) 
        { 
	        // Variable deceleration 
	        ObjectInputStream notesRawDataRestore;
	        
	        try
	        {
	            // Open the stream to retrieve the election raw data from the file
	        	notesRawDataRestore = new ObjectInputStream ( new FileInputStream ( notesRawDataFile.getAbsoluteFile() ) ) ;
	            
	            // Read the data in the file and store it in the votesCasted HashMap.
	            notes = ( Vector<Object> ) notesRawDataRestore.readObject() ;
	
	            notesRawDataRestore.close() ; // Close the stream.
	        }
	        catch ( IOException e ) { e.printStackTrace() ; }
	        catch ( ClassNotFoundException e ) { e.printStackTrace() ; }
        }
        
		myNotesList = new NotesList();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.add(R.id.notes_list, myNotesList);
		transaction.commit();
		setContentView(R.layout.activity_notes_start);
	}

	/**
	 * 
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notes_start, menu);
		return true;
	}

	/**
	 * 
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		switch (item.getItemId()) {
		case R.id.add:
			myNotesDescription = new NotesDescription("new", -1);
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.notes_list, myNotesDescription);
			transaction.commit();

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	/**
	 * 
	 * @param position
	 */
	public void onNoteClicked(int position) {
		
		myNotesDescription = new NotesDescription("old", position);
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.notes_list, myNotesDescription);
		transaction.commit();
	}

	/**
	 * 
	 * @param newNote
	 * @param action 
	 */
	public void onSave ( Notes newNote, String action, int position ) 
	{
		
		if ( action == "new" )
		{
		   notes.addElement(newNote);
		}
		else {
		   notes.remove(position);
		   notes.addElement(newNote);
		}
		
		myNotesList = new NotesList();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.notes_list, myNotesList);
		transaction.commit();
		
        // variable deceleration 
        ObjectOutputStream notesRawDataOut ;
        
        try {
            // Construct the stream to write the vector of notes saved already.
            notesRawDataOut = new ObjectOutputStream ( new FileOutputStream ( notesRawDataFile.getAbsoluteFile() ) ) ;
			notesRawDataOut.writeObject ( notes ) ;
	        notesRawDataOut.flush() ; // flush the stream to make sure everything is written.
	        notesRawDataOut.close() ; // Close the stream
		} catch ( IOException e ) { e.printStackTrace() ; }
	}

	/**
	 * 
	 */
	public void onCancel() 
	{
		myNotesList = new NotesList();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.notes_list, myNotesList);
		transaction.commit();
	}

	/**
	 * 
	 * @param position
	 */
	public void onDelete(int position) 
	{
		if ( position >= 0 ) 
		{
			notes.remove(position);
		}
		
		myNotesList = new NotesList();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.notes_list, myNotesList);
		transaction.commit();	
	}
}