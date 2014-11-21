package ca.uoit.DevanShah.DevanSuperPhotoList;

import java.io.Serializable;
import java.util.Vector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class PhotosView  extends Fragment implements Serializable  
{

    /**
     * Default serialization constant for this object.
     */
    private static final long serialVersionUID = 1L ;
    
    // Variable Deceleration 
    int position = PhotoStart.listPosition;
    
	// Date structure Deceleration
	Vector<Object> photos;
	
	// Declare the main activity
	Activity myNotesStartActivity;
	
	// Declare the edit text objects
	public static ImageView photoView ;
	
	/**
	 * Default constructor
	 */
	public PhotosView() {

	}
	
	/**
	 * This function is used to create the fragment view with some important data for the 
	 * fragment.
	 */
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		// Grab the inflate the view and store it 
		View view = inflater.inflate(R.layout.photo_view, container, false);
		
		// Get the main activity
		myNotesStartActivity = getActivity();
		
		// get the note vector from the main activity
		photos = ((PhotoStart) myNotesStartActivity).photos;
		
		// Get the myNotesDescription object from the main activity
		((PhotoStart) myNotesStartActivity).myPhotesView = this;
		
		photoView = (ImageView) view.findViewById(R.id.imageView1);
		
		// Setup the button and listener for the cancel button. 
		Button cancel = (Button) view.findViewById(R.id.button1);
		cancel.setOnClickListener(myCancelListener);
		
		// Setup the button and listener for the delete button. 
		Button delete = (Button) view.findViewById(R.id.button2);
		delete.setOnClickListener(myDeleteListener);
		
		// When the description fragment creation is requested as an old note 
		// display the title and content of the note.
		displayDetails(position);
		
        // Construct the action to be done when there is a saved instance.
        if ( savedInstanceState != null ) 
        {
        	// Cancel the action when instance is saved.
        	((PhotoStart) myNotesStartActivity).onCancel();
        }
		
		return view;
	}
	
	/**
	 * Display the information of the row in the list view that was selected.
	 * @param position
	 */
	public void displayDetails ( int position ) 
	{
		// get the note in the position that the info needs to be displayed.
		PhotoInfo currentPhoto = (PhotoInfo) photos.get(position);
		
		// Set the large image for the photo
		photoView.setImageBitmap((Bitmap) PhotoSaverCreator.resizePhoto((String) currentPhoto.getPhotoDirectory(), photoView.getWidth(), photoView.getHeight()));
		
	}
	
	/**
	 * This is the creation of an action to be performed on the click of the cancel button.
	 */
	private OnClickListener myCancelListener = new OnClickListener() 
	{
		// Action when the click is performed
		public void onClick(View view) 
		{
			// Call the cancel function from the main activity.
			((PhotoStart) myNotesStartActivity).onCancel();
		}
	};
	
	/**
	 * This is the creation of an action to be performed on the click of the delete button.
	 */
	private OnClickListener myDeleteListener = new OnClickListener() 
	{
		// Action when the click is performed
		public void onClick(View view) 
		{
			// Create the dialog to notify the user
			new AlertDialog.Builder(myNotesStartActivity)
		    			   .setTitle("Delete Photo") // Set the title
		    			   .setMessage("Are you sure you want to delete selected photo?") // Set the message
		    			   .setPositiveButton ( android.R.string.yes, // Set the type of button
		    					   			    new DialogInterface.OnClickListener() // Create the new on click listener for click of ok
		    			   						{
		    				   						// handle the on click of ok
		    				   						public void onClick(DialogInterface dialog, int which) 
		    				   						{ 
		    				   							// Call the delete function from the main activity. To remove the note that was selected.
		    				   							((PhotoStart) myNotesStartActivity).onDelete(position);
		    				   						}
		    			   						}
		    					   			   )
		    	           .setNegativeButton ( android.R.string.no, // Set the type of button
		    					   			    new DialogInterface.OnClickListener() // Create the new on click listener for click of ok
		    			   						{
		    				   						// handle the on click of ok
		    				   						public void onClick(DialogInterface dialog, int which) 
		    				   						{ 
		    				   							// Close the dialog button and let the user go back to adding title
		    				   							dialog.cancel();
		    				   						}
		    			   						}
		    					   			   )
		    					   			   .setIcon(android.R.drawable.ic_dialog_alert) // Set the layout of the box, using default.
		    					   			   .show(); // Show the dialog box
		}
	};
}
