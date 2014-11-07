package ca.UOIT.DevanShah.DevanSuperNotes;

import java.io.Serializable;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * 
 * @author Devan Shah 100428864
 *
 */
@SuppressLint("NewApi")
public class NotesDescription extends Fragment implements Serializable {

    /**
     * Default serialization constant for this object.
     */
    private static final long serialVersionUID = 1L ;
    
	String action = NotesStart.action;
	int position = NotesStart.listPosition;
	Vector<Object> notes;
	Activity myNotesStartActivity;
	EditText noteTitle;
	EditText noteDescription;
	
	/**
	 * 
	 * @param position 
	 * @param action 
	 * @param action
	 */
	public NotesDescription() {

	}
	
	/**
	 * 
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		View view = inflater.inflate(R.layout.notes_description, container, false);
		
		myNotesStartActivity = getActivity();
		
		notes = ((NotesStart) myNotesStartActivity).notes;
		((NotesStart) myNotesStartActivity).myNotesDescription = this;
		
		noteTitle = (EditText) view.findViewById(R.id.editText1);
		noteDescription = (EditText) view.findViewById(R.id.editText2);
		
		Button cancel = (Button) view.findViewById(R.id.button1);
		cancel.setOnClickListener(myCancelListener);
		
		Button save = (Button) view.findViewById(R.id.button2);
		save.setOnClickListener(mySaveListener);
		
		Button delete = (Button) view.findViewById(R.id.button3);
		delete.setOnClickListener(myDeleteListener);
		
		if ( action == "old") 
		{
		   displayDetails(position);
		}
		
        // Construct the action to be done when there is a saved instance.
        if ( savedInstanceState != null ) 
        {
        	((NotesStart) myNotesStartActivity).onCancel();
        }
		
		return view;
	}
	
	/**
	 * 
	 * @param position
	 */
	public void displayDetails ( int position ) 
	{
		Notes note = (Notes) notes.get(position);
		
		noteTitle.setText(note.getNoteTitle());
		noteDescription.setText(note.getNoteDescription()) ;
	}
	
	/**
	 * 
	 */
	private OnClickListener mySaveListener = new OnClickListener() 
	{
		public void onClick(View view) 
		{
			Notes newNote = new Notes(noteTitle.getText().toString(), noteDescription.getText().toString());
			if ( newNote.getNoteTitle().isEmpty() )  
			{
				new AlertDialog.Builder(myNotesStartActivity)
			    .setTitle("Empty Note Title")
			    .setMessage("Please enter a Note Title before Saving. Thanks")
			    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) { 
			        	dialog.cancel();
			        }
			     })
			    .setIcon(android.R.drawable.ic_dialog_alert)
			    .show();
			}
			else {
				((NotesStart) myNotesStartActivity).onSave(newNote,action,position);	
			}
		}
	};
	
	/**
	 * 
	 */
	private OnClickListener myCancelListener = new OnClickListener() 
	{
		public void onClick(View view) {
			((NotesStart) myNotesStartActivity).onCancel();
		}
	};
	
	/**
	 * 
	 */
	private OnClickListener myDeleteListener = new OnClickListener() 
	{
		public void onClick(View view) {
			((NotesStart) myNotesStartActivity).onDelete(position);
		}
	};

}
