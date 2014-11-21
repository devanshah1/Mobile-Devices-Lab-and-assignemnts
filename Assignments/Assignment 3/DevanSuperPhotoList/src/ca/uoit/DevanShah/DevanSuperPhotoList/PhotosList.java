package ca.uoit.DevanShah.DevanSuperPhotoList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PhotosList extends Fragment implements Serializable 
{
    /**
     * Default serialization constant for this object.
     */
    private static final long serialVersionUID = 1L ;
    
    // Date Structure Deceleration
	List<PhotoInfo> photosExtracted;
	Vector<Object> photos;
	
	// Stores the main activity
	public static Activity myNotesStartActivity;
	
	/**
	 * Default constructor
	 */
	public PhotosList() {

	}
	
	/**
	 * This function is used to create the fragment view with some important data for the 
	 * fragment.
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		// Grab the inflate the view and store it 
		View view = inflater.inflate(R.layout.photos_list, container, false);
		
		// Get the main activity
		myNotesStartActivity = getActivity();
		
		// get the note vector from the main activity
		photos = ((PhotoStart) myNotesStartActivity).photos;
		
		// Initialize the ArrayList<Map<String, String>>()
		photosExtracted = new ArrayList<PhotoInfo>();
		
		// Build the ArrayList<Map<String, String>>() using the notes object
		buildPhotosArray();
		
		CustomImageHandlerAdapter photosAdapter = new CustomImageHandlerAdapter ( myNotesStartActivity, R.layout.listviewrow, (ArrayList<PhotoInfo>) photosExtracted ) ;
		
		// Grab the list view so that it can be placed in
		ListView listView = (ListView) view.findViewById(R.id.listView1);
		listView.setAdapter(photosAdapter); // Set the adapter in the list view
		listView.setOnItemClickListener(photoSelected); // Enable the on click listener when entries in the list view are clicked
		return view;
	}
	
	/**
	 * This function is used to perform the action when a row is clicked in the list view.
	 */
	private OnItemClickListener photoSelected = new OnItemClickListener() 
	{
		// On item click for the list view
		public void onItemClick ( AdapterView<?> parent, View v, int position, long id ) 
		{	
			// Perform the action for on note clicked, call the function from the main activity.
			((PhotoStart) myNotesStartActivity).onPhotoClicked(position);
		}
	};
	
	/**
	 * This function is used to create the ArrayList<Map<String, String>>() using Vector<Object> of notes
	 */
	private void buildPhotosArray() {
		
		// Sort the entries that are in the notes vector, by date. This is to display the latest note first.
        Collections.sort ( photos, new Comparator<Object>() 
        {
        	  // Define a compare function that calls the compareTo available in the Notes class. (for each note)
        	  public int compare ( Object a, Object b ) 
        	  {
        		// Return the values of compare conditions, 1, -1, 0  
        	    return ( ((PhotoInfo) a).compareTo( (PhotoInfo) b));
        	  }
        });
        
		// Loop through the note vector and build a HashMap and add it to the ArrayList
        for ( int i = 0; i < photos.size(); i++ )
        {	
        	// Grab the notes entry
        	PhotoInfo photoEntry = ( ( PhotoInfo ) photos.elementAt (i) ) ;
            
            // Put the HashMap into the ArrayList
        	photosExtracted.add(photoEntry);
        }
	}
}
