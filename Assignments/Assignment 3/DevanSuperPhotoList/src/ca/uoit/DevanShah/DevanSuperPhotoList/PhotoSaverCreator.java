package ca.uoit.DevanShah.DevanSuperPhotoList;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

/**
 * 
 * @author Devan Shah 100428864
 *
 */
@SuppressLint("SimpleDateFormat") public class PhotoSaverCreator 
{
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
    public static File createPhotoFile() throws IOException 
    {
    	// General storage for photos created in DevanSuperPhotoList application
    	String APP_PHOTO_DIR = "/DevanSuperPhotoList/";
    	
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        PhotoStart.myCurrentPhotoFileName = "IMG_" + timeStamp + ".jpg" ;
        
        // Create the File object for the photo storage directory for this application
        File appPhotoStorageDir = new File ( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + APP_PHOTO_DIR );
        
        // Store the global directory of photo
        PhotoStart.myCurrentPhotoDirectory = appPhotoStorageDir.getAbsolutePath();
        
        /**
         * Before making the file for the photo make sure that the photo application directory exists.
         * If it does not exist then create the directory.
         */
        if ( appPhotoStorageDir != null ) 
        {
        	if ( ! appPhotoStorageDir.mkdirs() ) 
        	{
        		if ( ! appPhotoStorageDir.exists() )
        		{
        			Log.d("DevanSuperPhotoList", "Failed to create directory: " + appPhotoStorageDir.getAbsolutePath());
        		}
        	}
        }
        
        // After directory is created create the photo file now
        File photoFile = new File ( appPhotoStorageDir.getAbsolutePath() + "/" + PhotoStart.myCurrentPhotoFileName );
        
        return photoFile;
    }
    
    /**
     * @return 
     * 
     */
    public static Bitmap resizePhoto( String photoPath, int width, int height ) 
    {
		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
		int targetW = width;
		int targetH = height;

		/* Get the size of the image */
		BitmapFactory.Options bmOptions = new BitmapFactory.Options();
		bmOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(photoPath, bmOptions);
		int photoW = bmOptions.outWidth;
		int photoH = bmOptions.outHeight;
		
		/* Figure out which way needs to be reduced less */
		int scaleFactor = 1;
		if ((targetW > 0) || (targetH > 0)) {
			scaleFactor = Math.min(photoW/targetW, photoH/targetH);	
		}

		/* Set bitmap options to scale the image decode target */
		bmOptions.inJustDecodeBounds = false;
		bmOptions.inSampleSize = scaleFactor;
		//bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
		return BitmapFactory.decodeFile(photoPath, bmOptions);
		
	}
}
