package ca.uoit.DevanShah.HelloWorldTwo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Course: Mobile Devices
 * Name: Lab Two Layout ( Hello World Two)
 * @author Devan Shah (100428864)
 *
 */
public class MainActivity extends ActionBarActivity {

	// Variable declaration 
	private String name = "Please Enter Your Name:" ;
	private final String nameTag = "nameTag" ;
	
    @SuppressLint("NewApi") @Override
    protected void onCreate( Bundle savedInstanceState ) 
    {
        super.onCreate( savedInstanceState ) ;
        setContentView( R.layout.activity_main ) ;
        
        // Construct the action to be done when there is a saved instance.
        if ( savedInstanceState != null ) 
        {
        	// Gather some generic objects
        	TextView label = (TextView) findViewById( R.id.textView1 ) ;
        	Context context = getApplicationContext() ;
        
        	name = savedInstanceState.getString( nameTag ) ;
        	
        	// Make sure that when name is null or empty that the text view is reset to default.
        	if ( !name.isEmpty() )
        	{
        		label.setText( "Hello " + name ) ;
        	}
        	else {
        		
        		// Restore textView back to default string set, when editView is empty.
        		label.setText( context.getString( R.string.HelloWorld ) ) ;
        	}
        }
    }


    @Override
    public boolean onCreateOptionsMenu( Menu menu ) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState( Bundle outState ) 
    {
    	// Grab the text in the Edit text box before instance is destroyed
    	EditText nameText = ( EditText ) findViewById( R.id.editText1 ) ;
    	name = nameText.getText().toString() ;
    	
    	// Store this in the global variable nameTag to pull down again
    	// for onCreate.
    	outState.putString( nameTag,  name ) ;
    	super.onSaveInstanceState( outState ) ;
    }
    
    @Override
    public boolean onOptionsItemSelected( MenuItem item ) 
    {
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
     * Set the greeting with inputed name when the OK button is clicked. 
     * @param view
     */
    public void setName( View view )  
    {
    	// Gather some generic objects
    	EditText nameText = ( EditText ) findViewById( R.id.editText1 ) ;
    	TextView label = ( TextView ) findViewById( R.id.textView1 ) ;
    	
    	// Set the greeting based on the input string.
    	String name = nameText.getText().toString() ;
    	label.setText( "Hello " + name ) ;
    }
    
    /**
     * Clear the greeting that was presented to the user, when the CLEAR button is clicked.
     * Restores the default message in the label also.
     * @param view
     */
    public void clear( View view ) 
    {
    	// Gather some generic objects
    	Context context = getApplicationContext() ;
    	EditText nameText = ( EditText ) findViewById( R.id.editText1 ) ;
    	TextView label = ( TextView ) findViewById( R.id.textView1 ) ;
    	
    	// Clear the editText and clear and set back to default the textView.
    	nameText.setText( "" ) ;
    	label.setText( context.getString( R.string.HelloWorld ) ) ;
    }
}
