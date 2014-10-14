package ca.uoit.DevanShah.HelloUOIT;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DisplayMessage extends ActionBarActivity {

	public static boolean reverse = false ;
	public static String GREETINGTAG = "" ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState) ;
		setContentView(R.layout.activity_display_message) ;
		
		// Show the Up button in the action bar.
		// Below is not needed because this is done by default now.
		//setupActionBar();
		
		// Grab the intent that created the activity
		Intent intent = getIntent() ;
		
		// 
		GREETINGTAG = intent.getStringExtra( MainActivity.USER_NAME ) ;
		TextView message = (TextView) findViewById( R.id.textView2 ) ;
		message.setText( "Welcome " + GREETINGTAG ) ;
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_message, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		
		// Set the reverse boolean to true so we know that there is an activity switch
		reverse = true ;
		Intent reverseIntent = new Intent( this, MainActivity.class ) ;
		    	
    	// Allows to add data to our intent using the same key-value pair technique.
    	reverseIntent.putExtra( MainActivity.USER_NAME, GREETINGTAG ) ;
    	
    	// Transfers control back to the first activity.
    	startActivity( reverseIntent ) ;
    	
    	return true ;
		//return super.onOptionsItemSelected(item);
	}
}
