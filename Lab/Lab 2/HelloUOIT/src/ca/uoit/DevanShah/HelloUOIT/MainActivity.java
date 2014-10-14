package ca.uoit.DevanShah.HelloUOIT;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

	// Variable declaration 
	public static final String USER_NAME = "user name" ;
	public String name = "" ;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_main) ;
        
        if ( DisplayMessage.reverse ) {
        	
    		// Grab the intent that created the activity
    		Intent intent = getIntent() ;
    		
    		// Grab the user's name from the main activity, which display pushed in
        	name = intent.getStringExtra( MainActivity.USER_NAME ) ;
        	
        	// Define some generic objects
    		TextView greeting = (TextView) findViewById( R.id.textViewTitle ) ;
        	EditText nameText = ( EditText ) findViewById( R.id.editText1 ) ;
        	
        	// Set the greeting and edit text to correct values after returning from 2nd activity.
    		greeting.setText( "Welcome back to UOIT " + name ) ;
        	nameText.setText(name) ;
    		
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * 
     * @param view
     */
    public void sendMessage ( View view )
    {
    	// Create a new intent with the current class as the content 
    	// and the DisplayMessage class as the target of the intent
    	Intent intent = new Intent( this, DisplayMessage.class ) ;
    	
    	// Extract user's name from the edit Text widget 
    	EditText nameText = ( EditText ) findViewById( R.id.editText1 ) ;
    	String name = nameText.getText().toString() ;
    	
    	// Allows to add data to our intent using the same key-value pair technique.
    	intent.putExtra( USER_NAME, name ) ;
    	
    	// Transfers control over to the second activity.
    	startActivity( intent ) ;
    }
}
