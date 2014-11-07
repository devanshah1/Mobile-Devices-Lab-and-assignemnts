package ca.UOIT.DevanShah.DevanSuperNotes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/**
 * 
 * @author Devan Shah 100428864
 *
 */
@SuppressLint("NewApi")
public class NotesList extends Fragment implements Serializable {

    /**
     * Default serialization constant for this object.
     */
    private static final long serialVersionUID = 1L ;
	
	ArrayList<Map<String, String>> notesExtracted;
	Vector<Object> notes;
	Activity myNotesStartActivity;

	/**
	 * 
	 */
	public NotesList() {

	}

	/**
	 * 
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view;
		view = inflater.inflate(R.layout.notes_list, container, false);
		myNotesStartActivity = getActivity();
		notes = ((NotesStart) myNotesStartActivity).notes;
		notesExtracted = new ArrayList<Map<String, String>>();
		buildNotesArray();
		SimpleAdapter notesAdapter = new SimpleAdapter ( myNotesStartActivity, 
				                                         notesExtracted, 
				                                         R.layout.listviewrow, 
				                                         new String[] { "Title", "Date" }, 
				                                         new int[] { R.id.TITLE_CELL, R.id.DATE_CELL }
		                                               );
		ListView listView = (ListView) view.findViewById(R.id.listView1);
		listView.setAdapter(notesAdapter);
		listView.setOnItemClickListener(noteSelected);
		return view;
	}

	/**
	 * 
	 */
	private void buildNotesArray() {
        
		Map<String, String> noteMap;
		
        Collections.sort ( notes, new Comparator<Object>() 
        {
        	  public int compare ( Object a, Object b ) 
        	  {
        	    return ( ((Notes) a).compareTo( (Notes) b));
        	  }
        });
        
		// Loop through the note vector and build a HashMap
        for ( int i = 0; i < notes.size(); i++ )
        {
        	noteMap = new HashMap<String, String>();
            Notes noteEntry = ( ( Notes ) notes.elementAt (i) ) ;
            
            noteMap.put("Title", noteEntry.getNoteTitle());
            noteMap.put("Date", noteEntry.getNoteCreationDate());
            notesExtracted.add(noteMap);
        }
	}

	/**
	 * 
	 */
	private OnItemClickListener noteSelected = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
		{	
			((NotesStart) myNotesStartActivity).onNoteClicked(position);
		}
	};
}
