package ca.uoit.DevanShah.DevanSuperPhotoList;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomImageHandlerAdapter extends ArrayAdapter<PhotoInfo> 
{
	ArrayList<PhotoInfo> photos;
	Context context;
	
	public CustomImageHandlerAdapter(Context context, int resource, ArrayList<PhotoInfo> photos ) 
	{
		super ( context, resource, photos );
		this.photos = photos;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		// Make sure we have a view to work with (may have been given null)
		View itemView = convertView;
		
		if (itemView == null) 
		{
			itemView = ((Activity) context).getLayoutInflater().inflate(R.layout.listviewrow, parent, false);
		}
		
		if ( ! photos.isEmpty() )
		{
			// Find the car to work with.
			PhotoInfo currentPhoto = photos.get(position);
			
			// Fill the view
			ImageView imageView = (ImageView) itemView.findViewById(R.id.photoThumbnail);
			imageView.setImageBitmap((Bitmap) PhotoSaverCreator.resizePhoto((String) currentPhoto.getPhotoDirectory(), imageView.getWidth(), imageView.getHeight()));
			
			// Make:
			TextView makeText = (TextView) itemView.findViewById(R.id.PHOTO_FILE_NAME);
			makeText.setText((CharSequence) currentPhoto.getPhotoFileName());

			// Year:
			TextView yearText = (TextView) itemView.findViewById(R.id.GEO_LOCATION_CELL);
			yearText.setText((CharSequence) currentPhoto.getPhotoGeoLocation());
			
			// Condition:
			TextView condionText = (TextView) itemView.findViewById(R.id.DATE_CELL);
			condionText.setText((CharSequence) currentPhoto.getPhotoCreationDate());	
		}

		return itemView;
	}			
}
