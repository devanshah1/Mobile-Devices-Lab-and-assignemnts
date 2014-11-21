package ca.uoit.DevanShah.DevanSuperPhotoList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


@SuppressLint("SimpleDateFormat") public class PhotoStart extends Activity 
{
	// Photo capture constants 
    static final int REQUEST_PHOTO_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    
	// Date Structure Deceleration
	public Vector<Object> photos;
	
	public static ImageView mImageView;
	public static String myCurrentPhotoPath;
	public static String myCurrentPhotoGeoLocation;
	public static String myCurrentPhotoFileName;
	public static String myCurrentPhotoDirectory;
	public static Bitmap photoCaptureBitmap;
	public static int listPosition;
	public static double LATITUDE;
	public static double LONGITUDE;
	
	// Object Deceleration
	public PhotosView myPhotesView;
	public PhotosList myPhotosList;
	
	// Create the File handle for the file that is checked if an restore is needed
	public File photosRawDataFile;
	
	// Used to retrieve the complete address using latitude and longitude coordinates 
	Geocoder geocoder;
	List<Address> addresses;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_start);
        
        PhotoLocationManager photoLocationManager = new PhotoLocationManager(this);
        photoLocationManager.locationManager();
        
        // Get the default locale for the current configuration
        geocoder = new Geocoder(this, Locale.getDefault());
        
        photosRawDataFile = new File( getFilesDir(), "PhotosRawData.ser");
        
        // Initialize the notes Vector
     	photos = new Vector<Object>();
     	
		// Restore the notes from the internal application storage file if
		// available.
     	restorePhotosHistoryFromInternalStorage();
		
		// Create the new photo list fragment
		myPhotosList = new PhotosList();

		// Start the fragment transaction to place the fragment into the
		// activity
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
		// add the notes list fragment to the activity by replacing the
		// FramLayout
		transaction.replace(R.id.photos_list, myPhotosList, "PhotosList");
		transaction.commit(); // Commit the changes
		setContentView(R.layout.activity_photo_start); // Set the view for the activity
    }

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
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		/**
		 * Switch through the possible options on the action bar and perform the
		 * actions accordingly.
		 */
		switch ( item.getItemId() ) 
		{
			// Handle when the camera icon in the action bar is clicked
			case R.id.camera:
				
				// Dispatch the take picture intent, to take a picture and save it in a file.
				dispatchTakePictureIntent();
				
				return true;
				
			default:
				
				return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * 
	 */
    private void dispatchTakePictureIntent()
    {
    	// Create a new intent to switch to the camera application
        Intent capturePhotoIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
        
        if ( capturePhotoIntent.resolveActivity( getPackageManager() ) != null) 
        {   
            // Variable that stores the File where the photo will be saved.
            File constructedPhotoFile = null;

            // Handle creating the file and storing it into variable 
            try 
            {
                constructedPhotoFile = PhotoSaverCreator.createPhotoFile();
                
                // Set the path to the current photo for Photo Info creation
                myCurrentPhotoPath = constructedPhotoFile.getAbsolutePath();
                
                if ( Geocoder.isPresent()) {
                	System.out.println("GEOCODING IS ON");
                }
                else {
                	System.out.println("GEOCODING IS OFF");
                }
                
                addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
                
                if ( addresses.size() > 0 ) 
                {
                	for (int index = 0; index < addresses.get(0).getMaxAddressLineIndex(); index++) 
                	{
                		myCurrentPhotoGeoLocation += addresses.get(0).getAddressLine(index);
                	}
                }
                else {
                	myCurrentPhotoGeoLocation = Double.toString(LATITUDE) + ", " + Double.toString(LONGITUDE) ;
                }
            }
            // Error occurred while creating the File
            catch ( IOException e ) { e.printStackTrace(); }
            
            // Only put the data into the intent if the photo file was constructed successfully
            if ( constructedPhotoFile != null ) 
            {
            	// Put the URI URI of the image into the photo intent 
                capturePhotoIntent.putExtra ( MediaStore.EXTRA_OUTPUT, Uri.fromFile(constructedPhotoFile) );
                startActivityForResult ( capturePhotoIntent, REQUEST_TAKE_PHOTO );
            }
        }
    }
    
    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) 
    {	
    	if ( resultCode == Activity.RESULT_OK )
    	{
            PhotoInfo photoInformation = new PhotoInfo( myCurrentPhotoGeoLocation, myCurrentPhotoPath, myCurrentPhotoFileName );
            photos.addElement(photoInformation);
    	}
    	
        // Save the photo Vector to a file, for restore later.
        saveNotesInInternalStorage() ;
        
		// Create the new photo list fragment
		myPhotosList = new PhotosList();
		
		// Start the fragment transaction to place the fragment into the
		// activity
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
		// add the notes list fragment to the activity by replacing the
		// FramLayout
		transaction.replace(R.id.photos_list, myPhotosList, "PhotosList");
		transaction.commit(); // Commit the changes	
    }
    
	/**
	 * This function is used to handle the action to be performed when item in
	 * the list view is clicked.
	 * 
	 * @param position
	 *            - position value from the list view
	 */
	public void onPhotoClicked ( int position ) 
	{
		listPosition = position ;
		
		// Create the new notes description with empty title and content
		myPhotesView = new PhotosView();
		
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		
		transaction.replace(R.id.photos_list, myPhotesView, "PhotoView");
		transaction.commit(); // Commit the changes
	}
	
	/**
	 * This function is used to restore the notes saved by the user from the
	 * internal application file.
	 */
	@SuppressWarnings("unchecked")
	public void restorePhotosHistoryFromInternalStorage() 
	{

		// Perform the restore only if the file exists.
		if ( photosRawDataFile.exists() ) 
		{
			// Variable deceleration
			ObjectInputStream photosRawDataRestore;

			try {
				// Open the stream to retrieve the saved notes from the file.
				photosRawDataRestore = new ObjectInputStream( new FileInputStream(photosRawDataFile.getAbsoluteFile()));

				// Read the data in the file and store it in the notes vector.
				photos = (Vector<Object>) photosRawDataRestore.readObject();

				photosRawDataRestore.close(); // Close the stream.
			} 
			catch (IOException e) { e.printStackTrace(); } 
			catch (ClassNotFoundException e) { e.printStackTrace(); }
		}
	}
	
	/**
	 * Used to store the Vector of notes to a file located on the internal
	 * storage of the device. Saves using serialization to make the data secure.
	 */
	public void saveNotesInInternalStorage() 
	{
		// variable deceleration
		ObjectOutputStream photoRawDataOut;

		try {
			
			// Construct the stream to write the vector of notes saved already.
			photoRawDataOut = new ObjectOutputStream(new FileOutputStream(photosRawDataFile.getAbsoluteFile()));
			photoRawDataOut.writeObject(photos); // Write the object
			photoRawDataOut.flush(); // flush the stream to make sure everything is written.
			photoRawDataOut.close(); // Close the stream
		} 
		catch (IOException e) { e.printStackTrace(); }
	}

	public void onDelete(int position) 
	{
		// Only remote elements if a position is positive,
		// to avoid null expections
		if (position >= 0) {
			photos.remove(position);
		}
		
		// Create the new notes list with
		myPhotosList = new PhotosList();

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.photos_list, myPhotosList, "PhotosList");
		transaction.commit(); // Commit the changes

		// Save the notes vector to the intern storage.
		saveNotesInInternalStorage();
	}

	/**
	 * This function is used to cancel the operation that the user has performed
	 * on the, editable note or discard the note if it is new.
	 */
	public void onCancel() 
	{
		// Create the new notes list with
		myPhotosList = new PhotosList();

		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.photos_list, myPhotosList, "PhotosList");
		transaction.commit(); // Commit the changes
	}
}
