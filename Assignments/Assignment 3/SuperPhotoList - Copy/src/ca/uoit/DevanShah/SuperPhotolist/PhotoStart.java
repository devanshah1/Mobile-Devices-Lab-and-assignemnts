package ca.uoit.DevanShah.SuperPhotolist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * This class is used to perform the action for the main activity.
 * 
 * @author Devan Shah 100428864
 * 
 */
@SuppressLint({ "UseSparseArrays", "UseValueOf", "NewApi", "SimpleDateFormat" })
public class PhotoStart extends ActionBarActivity {

	// Variable Deceleration
	public static String action;
	public static int listPosition;
	static final int REQUEST_IMAGE_CAPTURE = 1;
	String mCurrentPhotoPath;
	static final int REQUEST_TAKE_PHOTO = 1;

	// Date Structure Deceleration
	public Vector<Object> photos;

	// Object Deceleration
	public PhotosView myPhotesView;
	public PhotosList myPhotosList;
	public File photosRawDataFile;
	public Bitmap photoCaptureBitmap;
	
	// Variable Tags
	private final String actionTag = "actionTag";
	private final String listPositionTag = "listPositionTag";

	/**
	 * This function is used to create the activity. This also handles activity
	 * changes like rotation and destructions of activity.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Initialize the notes Vector
		photos = new Vector<Object>();

		// Restore the notes from the internal application storage file if
		// available.
		restorePhotosFromInternalStorage();

		// Construct the action to be done when there is no save instance state
		// available
		if (savedInstanceState == null) {
			// Create the new notes list fragment
			myPhotosList = new PhotosList();

			// Start the fragment transaction to place the fragment into the
			// activity
			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();
			// add the notes list fragment to the activity by replacing the
			// FramLayout
			transaction.add(R.id.photos_list, myPhotosList, "PhotosList");
			transaction.commit(); // Commit the changes
			setContentView(R.layout.activity_photo_start); // Set the view for
															// the activity
		} else {

			// Restore the action and listPosition when onCreate function is
			// called with a valid saveInstanceState bundle
			action = savedInstanceState.getString(actionTag);
			listPosition = savedInstanceState.getInt(listPositionTag);

			// Start the fragment transaction to place the fragment into the
			// activity
			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();

			// Check which fragment was placed into the save instance state
			// bundle so that the correct one can be restored.
			// Restore the notes list fragment
			if ((savedInstanceState.getSerializable("PhotosList")) != null) {
				// Get the serialized data of the fragment
				myPhotosList = (PhotosList) savedInstanceState
						.getSerializable("PhotosList");
				transaction
						.replace(R.id.photos_list, myPhotosList, "PhotosList");
			}
			// Restore the description fragment
			else {
				// Create the description fragment
				myPhotesView = new PhotosView();
				// Replace the notes list in the activity
				transaction.replace(R.id.photos_list, myPhotesView,
						"Description");
			}
			transaction.commit(); // Commit the changes
			setContentView(R.layout.activity_photo_start);
		}
	}

	/**
	 * Function is used to create the options menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo_start, menu);
		return true;
	}

	/**
	 * This function is used to handle when buttons in the action bar are
	 * selected. Supports Add note.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		/**
		 * Switch through the possible options on the action bar and perform the
		 * actions accordingly.
		 */
		switch (item.getItemId()) {
		// Handle when the add is clicked
		case R.id.camera:
			dispatchTakePictureIntent();
//			// Set the action and listposition
//			action = "new";
//			listPosition = -1;
//
//			// Create the new notes description with empty title and content
//			myPhotesView = new PhotosView();
//			FragmentTransaction transaction = getFragmentManager()
//					.beginTransaction();
//			transaction.replace(R.id.photos_list, myPhotesView, "Description");
//			transaction.addToBackStack("Description");
//			transaction.commit();

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * This function is used to save the instance of the activity.
	 * 
	 * @param outState
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// Check which fragment is currently visible in the activity and store
		// that one to the bundle
		if (myPhotosList.isVisible()) {
			outState.putSerializable("PhotosList", myPhotosList);
		} else if (myPhotesView.isVisible()) {
			outState.putSerializable("NotesDescription", myPhotesView);
		}

		// Samve the global variable used to construct description fragments
		outState.putString(actionTag, action);
		outState.putFloat(listPositionTag, listPosition);

		super.onSaveInstanceState(outState);
	}

	/**
	 * This function is used to handle the action to be performed when item in
	 * the list view is clicked.
	 * 
	 * @param position
	 *            - position value from the list view
	 */
	public void onNoteClicked(int position) {
		// Set the action and listposition
		action = "old";
		listPosition = position;

		// Create the new notes description with empty title and content
		myPhotesView = new PhotosView();
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.photos_list, myPhotesView, "Description");
		transaction.addToBackStack("Description");
		transaction.commit();
	}

	/**
	 * This
	 * 
	 * @param newNote
	 *            - The new note that needs to be saved
	 * @param action
	 *            - the action that was used to construct the fragment for
	 *            description
	 */
	public void onSave(PhotoInfo newNote, String action, int position) {
		// When the fragment was a new note make sure that the new note is added
		// to the Vector
		if (action == "new") {
			photos.addElement(newNote);
		}
		// When the fragment is edit then remove the old note and add the new
		// one to the Vector
		else {
			photos.remove(position);
			photos.addElement(newNote);
		}

		// Create the new notes list with
		myPhotosList = new PhotosList();

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.photos_list, myPhotosList, "PhotosList");
		transaction.commit();

		// Save the notes vector to the intern storage.
		saveNotesInInternalStorage();
	}

	/**
	 * This function is used to cancel the operation that the user has performed
	 * on the, editable note or discard the note if it is new.
	 */
	public void onCancel() {
		// Create the new notes list with
		myPhotosList = new PhotosList();

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.photos_list, myPhotosList, "PhotosList");
		transaction.commit();
	}

	/**
	 * This function is used to delete the note that is selected by the user.
	 * 
	 * @param position
	 */
	public void onDelete(int position) {
		// Only remote elements if a position is positive,
		// to avoid null expections
		if (position >= 0) {
			photos.remove(position);
		}

		// Create the new notes list with
		myPhotosList = new PhotosList();

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.photos_list, myPhotosList, "PhotosList");
		transaction.commit();

		// Save the notes vector to the intern storage.
		saveNotesInInternalStorage();
	}

	/**
	 * Used to store the Vector of notes to a file located on the internal
	 * storage of the device. Saves using serialization to make the data secure.
	 */
	public void saveNotesInInternalStorage() {
		// variable deceleration
		ObjectOutputStream notesRawDataOut;

		try {
			// Construct the stream to write the vector of notes saved already.
			notesRawDataOut = new ObjectOutputStream(new FileOutputStream(
					photosRawDataFile.getAbsoluteFile()));
			notesRawDataOut.writeObject(photos); // Write the object
			notesRawDataOut.flush(); // flush the stream to make sure everything
										// is written.
			notesRawDataOut.close(); // Close the stream
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function is used to restore the notes saved by the user from the
	 * internal application file.
	 */
	@SuppressWarnings("unchecked")
	public void restorePhotosFromInternalStorage() {
		// Create the File handle for the file that is checked if an restore is
		// needed
		photosRawDataFile = new File(getFilesDir(), "NotesRawData.ser");

		// Perform the restore only if the file exists.
		if (photosRawDataFile.exists()) {
			// Variable deceleration
			ObjectInputStream notesRawDataRestore;

			try {
				// Open the stream to retrieve the saved notes from the file.
				notesRawDataRestore = new ObjectInputStream(
						new FileInputStream(photosRawDataFile.getAbsoluteFile()));

				// Read the data in the file and store it in the notes vector.
				photos = (Vector<Object>) notesRawDataRestore.readObject();

				notesRawDataRestore.close(); // Close the stream.
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private void dispatchTakePictureIntent() 
	{
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		if ( takePictureIntent.resolveActivity(getPackageManager()) != null ) 
		{
			startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
		}
		
		// Ensure that there's a camera activity to handle the intent
		if ( takePictureIntent.resolveActivity(getPackageManager()) != null ) 
		{
			// Create the File where the photo should go
			File photoFile = null;
			try {
				photoFile = createImageFile();
			} catch (IOException ex) {
				// Error occurred while creating the File
			}
			
			// Continue only if the File was successfully created
			if ( photoFile != null ) 
			{
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
				startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
			}
		}
	}
	
	@Override
	protected void onActivityResult ( int requestCode, int resultCode, Intent data ) 
	{
	    if ( requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK ) 
	    {
	        Bundle extras = data.getExtras();
	        photoCaptureBitmap = (Bitmap) extras.get("data");
	    }
	}
	
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(imageFileName, /* prefix */
				".jpg", /* suffix */
				storageDir /* directory */
		);

		// Save a file: path for use with ACTION_VIEW intents
		mCurrentPhotoPath = "file:" + image.getAbsolutePath();
		return image;
	}
}
