package ca.uoit.DevanShah.DevanSuperPhotoList;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class PhotoLocationManager 
{
	public Activity mainActivity;
	
	public PhotoLocationManager( Activity mainActivity )
	{
		this.mainActivity = mainActivity;
	}
	public void locationManager() 
	{
		final LocationManager manager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);
		
		final LocationListener listener = new LocationListener() 
		{
			@Override
			public void onLocationChanged(Location location) 
			{
				PhotoStart.LATITUDE = location.getLatitude();
				PhotoStart.LONGITUDE = location.getLongitude();
			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
			}
		};
		
		manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
	}
}
